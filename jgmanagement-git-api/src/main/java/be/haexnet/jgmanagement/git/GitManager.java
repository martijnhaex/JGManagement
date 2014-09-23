package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.model.BaseBranch;
import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.BranchType;
import be.haexnet.jgmanagement.git.model.Project;

import java.util.ArrayList;
import java.util.List;

import static be.haexnet.jgmanagement.git.util.GitManagerUtil.getBranches;
import static org.fest.assertions.api.Assertions.assertThat;

public class GitManager {
    private List<Project> projects;
    private BaseBranch baseBranch = BaseBranch.DEVELOP;
    private List<BranchType> types = new ArrayList<>();

    private GitManager() { }

    public static GitManager manageGit() {
        return new GitManager();
    }

    public GitManager withProjects(final List<Project> projects) {
        this.projects = projects;
        return this;
    }

    public GitManager withBaseBranch(final BaseBranch baseBranch) {
        this.baseBranch = baseBranch;
        return this;
    }

    public GitManager withLocalBranches() {
        addBranchTypeWhenNotExists(BranchType.LOCAL);
        return this;
    }

    public GitManager withRemoteBranches() {
        addBranchTypeWhenNotExists(BranchType.REMOTE);
        return this;
    }

    public List<Branch> show() {
        assertThat(projects).overridingErrorMessage("Cannot call GIT, please set the project (GIT repositories) to monitor first.").isNotEmpty();
        assertThat(types).overridingErrorMessage("Cannot call GIT, please set the branch types (LOCAL and/or REMOTE) first.").isNotEmpty();

        final List<Branch> branches = new ArrayList<>();
        projects.stream()
                .map(getBranches(types, baseBranch))
                .forEach(branches::addAll);
        return branches;
    }

    private void addBranchTypeWhenNotExists(final BranchType type) {
        if (!types.contains(type)) {
            types.add(type);
        }
    }
}
