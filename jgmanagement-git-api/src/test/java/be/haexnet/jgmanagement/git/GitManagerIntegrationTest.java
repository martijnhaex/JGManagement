package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.fixture.ProjectFixture;
import be.haexnet.jgmanagement.git.model.BaseBranch;
import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.BranchType;
import be.haexnet.jgmanagement.git.model.Project;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static be.haexnet.jgmanagement.git.GitManager.manageGit;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class GitManagerIntegrationTest {
    static final List<Project> PROJECTS = ProjectFixture.LIST();

    @Rule
    public ExpectedException expectedException = ExpectedException.none().handleAssertionErrors();

    @Test
    public void showNoProjectsOrBaseBranch() throws Exception {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Cannot call GIT, please set the project (GIT repositories) to monitor first.");

        manageGit().showBranches();
    }

    @Test
    public void showNoBranchType() {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Cannot call GIT, please set the branch types (LOCAL and/or REMOTE) first.");

        manageGit().withProjects(PROJECTS).showBranches();
    }

    @Test
    public void showBranchesWithProjectsNoBaseBranch() throws Exception {
        final List<Branch> branches = manageGit().withProjects(PROJECTS).withRemoteBranches().showBranches();

        assertThat(branches).hasSize(1);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57));
        assertThat(extractProperty("merged").from(branches)).containsOnly(false);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH());
        assertThat(extractProperty("type").from(branches)).containsOnly(BranchType.REMOTE);
    }

    @Test
    public void showWithProjectsAndBaseBranch() throws Exception {
        final List<Branch> branches = manageGit().withProjects(PROJECTS).withBaseBranch(BaseBranch.MASTER).withRemoteBranches().showBranches();

        assertThat(branches).hasSize(1);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57));
        assertThat(extractProperty("merged").from(branches)).containsOnly(false);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH());
        assertThat(extractProperty("type").from(branches)).containsOnly(BranchType.REMOTE);
    }

    @Test
    public void showLocalBranches() throws Exception {
        final List<Branch> branches = manageGit().withProjects(PROJECTS).withLocalBranches().showBranches();

        assertThat(branches).hasSize(2);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project", "feature-only-local");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57), new DateTime(2014, 9, 22, 17, 8, 26));
        assertThat(extractProperty("merged").from(branches)).containsOnly(true, false);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH(), ProjectFixture.JIRA_GIT());
        assertThat(extractProperty("type").from(branches)).containsOnly(BranchType.LOCAL);
    }

    @Test
    public void showAllBranches() throws Exception {
        final List<Branch> branches = manageGit().withProjects(PROJECTS).withLocalBranches().withRemoteBranches().showBranches();

        assertThat(branches).hasSize(3);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project", "feature-only-local");
        assertThat(extractProperty("lastCommit").from(branches)).containsOnly(new DateTime(2014, 9, 22, 9, 44, 57), new DateTime(2014, 9, 22, 17, 8, 26));
        assertThat(extractProperty("merged").from(branches)).containsOnly(false, true);
        assertThat(extractProperty("project").from(branches)).containsOnly(ProjectFixture.SSH(), ProjectFixture.JIRA_GIT());
        assertThat(extractProperty("type").from(branches)).containsOnly(BranchType.REMOTE, BranchType.LOCAL);
    }
}