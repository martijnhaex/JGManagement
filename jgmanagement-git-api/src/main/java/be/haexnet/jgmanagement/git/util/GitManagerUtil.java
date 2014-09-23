package be.haexnet.jgmanagement.git.util;

import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.BranchType;
import be.haexnet.jgmanagement.git.model.BranchTypeReference;
import be.haexnet.jgmanagement.git.model.Project;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GitManagerUtil {
    public static Function<Project, List<Branch>> getBranches(final List<BranchType> types, final String baseBranch) {
        return project -> getBranches(project, types, baseBranch);
    }

    private static List<Branch> getBranches(final Project project, final List<BranchType> types, final String baseBranch) {
        final List<Branch> branches = new ArrayList<>();
        types.stream()
                .map(getReferences(project))
                .map(createBranches(project, baseBranch))
                .forEach(branches::addAll);
        return branches;
    }

    private static Function<BranchType, BranchTypeReference> getReferences(final Project project) {
        return type -> {
            Map<String, Ref> references = Collections.emptyMap();
            try {
                references = getRepositoryForProject(project).getRefDatabase().getRefs(type.getRef());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BranchTypeReference.of(type, references);
        };
    }

    private static Function<BranchTypeReference, List<Branch>> createBranches(final Project project, final String baseBranch) {
        return typeReference -> {
            final Map<String, Ref> references = typeReference.getReferences();

            return references.keySet()
                    .stream()
                    .map(references::get)
                    .map(createBranch(project, typeReference.getType(), baseBranch))
                    .filter(isFeatureBranch(project))
                    .collect(Collectors.toList());
        };
    }

    private static Repository getRepositoryForProject(final Project project) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(project.getDirectoryLocation() + "/.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }

    private static Function<Ref, Branch> createBranch(final Project project, final BranchType type, final String baseBranch) {
        return remote -> {
            final ObjectId remoteBranchId = remote.getObjectId();
            final String remoteBranchName = remote.getName();

            return Branch.of(
                    remoteBranchName.substring(remoteBranchName.lastIndexOf("/") + 1),
                    getLastCommitOfBranch(project, remoteBranchId).orElse(null),
                    isBranchMerged(project, remoteBranchId, baseBranch),
                    project,
                    type
            );
        };
    }

    private static Optional<DateTime> getLastCommitOfBranch(final Project project, final ObjectId branchId) {
        try {
            final PlotWalk walk = new PlotWalk(getRepositoryForProject(project));
            return Optional.of(
                    new DateTime(walk.parseCommit(branchId).getCommitTime() * 1000L)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static Boolean isBranchMerged(final Project project, final ObjectId branchId, final String baseBranch) {
        try {
            final Repository repository = getRepositoryForProject(project);
            final PlotWalk walk = new PlotWalk(repository);

            final ObjectId developBranchId = repository.resolve(baseBranch);

            return walk.isMergedInto(
                    walk.lookupCommit(branchId),
                    walk.lookupCommit(developBranchId)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Predicate<Branch> isFeatureBranch(final Project project) {
        return branch ->
                branch.getName().startsWith("feature") || branch.getName().startsWith(project.getName());
    }
}
