package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.fixture.ProjectFixture;
import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.Project;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class GitManagerIntegrationTest {
    static final List<Project> PROJECTS = ProjectFixture.LIST();

    GitManager manager = new GitManager();

    @Test(expected = AssertionError.class)
    public void remoteBranchesNoProjectsOrBaseBranch() throws Exception {
        manager.remoteBranches();
    }

    @Test
    public void remoteBranchesWithProjectsNoBaseBranch() throws Exception {
        final List<Branch> branches = manager.withProjects(PROJECTS).remoteBranches();

        assertThat(branches).hasSize(1);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57));
        assertThat(extractProperty("merged").from(branches)).containsOnly(false);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH());
    }

    @Test
    public void remoteBranchesWithProjectsAndBaseBranch() throws Exception {
        final List<Branch> branches = manager.withProjects(PROJECTS).withBaseBranch("origin/master").remoteBranches();

        assertThat(branches).hasSize(1);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57));
        assertThat(extractProperty("merged").from(branches)).containsOnly(false);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH());
    }
}