package be.haexnet.jgmanagement.git;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE;
import static org.fest.assertions.api.Assertions.assertThat;

public class GitManager {
    private List<Project> projects;
    private String baseBranch = "origin/develop";

    public GitManager withProjects(final List<Project> projects) {
        this.projects = projects;
        return this;
    }

    public GitManager withBaseBranch(final String baseBranch) {
        this.baseBranch = baseBranch;
        return this;
    }

    public List<Branch> show() {
        assertThat(projects).isNotEmpty();

        final List<Branch> branches = new ArrayList<>();
        projects.stream()
                .map(project -> getAllBranchesForProject(project))
                .forEach(branches::addAll);

        return branches;
    }

    private List<Branch> getAllBranchesForProject(final Project project) {
        try {
            return getGit(project)
                    .branchList()
                    .setListMode(REMOTE)
                    .call()
                    .stream()
                    .map(createBranch(project))
                    .filter(isFeatureBranch(project))
                    .collect(Collectors.toList());
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

        //TODO throw a custom exception!
        return Collections.emptyList();
    }

    private Git getGit(final Project project) throws IOException {
        return new Git(
                getRepositoryForProject(project)
        );
    }

    private Function<Ref, Branch> createBranch(final Project project) {
        return remote -> {
            final ObjectId remoteBranchId = remote.getObjectId();
            final String remoteBranchName = remote.getName();

            return Branch.of(
                    remoteBranchName.substring(remoteBranchName.lastIndexOf("/") + 1),
                    getLastCommitOfBranch(project, remoteBranchId).orElse(null),
                    isBranchMerged(project, remoteBranchId),
                    project);
        };
    }

    private Optional<DateTime> getLastCommitOfBranch(final Project project, final ObjectId branchId) {
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

    private Boolean isBranchMerged(final Project project, final ObjectId branchId) {
        //TODO make generic to say in which branch is merged!
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

    private Predicate<Branch> isFeatureBranch(final Project project) {
        return branch ->
                branch.getName().startsWith("feature") || branch.getName().startsWith(project.getName());
    }

    private Repository getRepositoryForProject(final Project project) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(project.getDirectoryLocation() + "/.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }
}
