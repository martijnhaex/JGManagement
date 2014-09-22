package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.Project;

import java.util.ArrayList;
import java.util.List;

import static be.haexnet.jgmanagement.git.util.GitManagerUtil.getBranches;
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
                .map(getBranches(baseBranch))
                .forEach(branches::addAll);

        return branches;
    }
}
