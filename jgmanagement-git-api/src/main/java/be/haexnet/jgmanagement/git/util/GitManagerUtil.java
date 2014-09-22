package be.haexnet.jgmanagement.git.util;

import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE;

public class GitManagerUtil {
    public static Function<Project, List<Branch>> getBranches(final String baseBranch) {
        return project -> getBranches(project, baseBranch);
    }

    private static List<Branch> getBranches(final Project project, final String baseBranch) {
        try {
            return getGit(project)
                    .branchList()
                    .setListMode(REMOTE)
                    .call()
                    .stream()
                    .map(createBranch(project, baseBranch))
                    .filter(isFeatureBranch(project))
                    .collect(Collectors.toList());
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

        //TODO throw a custom exception!
        return Collections.emptyList();
    }

    private static Git getGit(final Project project) throws IOException {
        return new Git(
                getRepositoryForProject(project)
        );
    }

    private static Repository getRepositoryForProject(final Project project) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(project.getDirectoryLocation() + "/.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }

    private static Function<Ref, Branch> createBranch(final Project project, final String baseBranch) {
        return remote -> {
            final ObjectId remoteBranchId = remote.getObjectId();
            final String remoteBranchName = remote.getName();

            return Branch.of(
                    remoteBranchName.substring(remoteBranchName.lastIndexOf("/") + 1),
                    getLastCommitOfBranch(project, remoteBranchId).orElse(null),
                    isBranchMerged(project, remoteBranchId, baseBranch),
                    project);
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
