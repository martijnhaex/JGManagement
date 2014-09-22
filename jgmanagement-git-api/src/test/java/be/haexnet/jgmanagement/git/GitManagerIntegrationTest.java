package be.haexnet.jgmanagement.git;

import be.haexnet.jgmanagement.git.model.Branch;
import be.haexnet.jgmanagement.git.model.TestData;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

public class GitManagerIntegrationTest {
    GitManager manager = new GitManager();

    @Test
    public void remoteBranches() throws Exception {
        final List<Branch> branches = manager.remoteBranches();

        assertThat(branches).hasSize(5);
        assertThat(extractProperty("name").from(branches)).containsOnly("refs/remotes/origin/develop", "refs/remotes/origin/feature-other-project", "refs/remotes/origin/master", "refs/remotes/origin/develop", "refs/remotes/origin/master");
        assertThat(extractProperty("lastCommit").from(branches)).containsNull();
        assertThat(extractProperty("merged").from(branches)).containsNull();
        assertThat(extractProperty("project").from(branches)).containsOnly(TestData.SSH.getProject(), TestData.GIT_JIRA.getProject());
    }
}