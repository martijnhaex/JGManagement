package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.Project;
import be.haexnet.jgmanagement.git.model.TestData;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.eclipse.jgit.api.ListBranchCommand.ListMode.REMOTE;

public class GitManager {
    public List<Branch> remoteBranches() {
        final List<Branch> branches = new ArrayList<>();

        Arrays.asList(TestData.values())
                .stream()
                .map(data -> getAllBranchesForProject(data.getProject()))
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
        return remote -> Branch.of(remote.getName().substring(remote.getName().lastIndexOf("/") + 1), null, null, project);
    }

    private Predicate<Branch> isFeatureBranch(final Project project) {
        return branch -> branch.getName().startsWith("feature") || branch.getName().startsWith(project.getName());
    }

    private Repository getRepositoryForProject(final Project project) throws IOException {
        return new FileRepositoryBuilder()
                .setGitDir(new File(project.getDirectoryLocation() + "/.git"))
                .readEnvironment()
                .findGitDir()
                .build();
    }
}
