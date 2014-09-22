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

        assertThat(branches).hasSize(1);
        assertThat(extractProperty("name").from(branches)).containsOnly("feature-other-project");
        assertThat(extractProperty("lastCommit").from(branches)).containsNull();
        assertThat(extractProperty("merged").from(branches)).containsNull();
        assertThat(extractProperty("project").from(branches)).containsOnly(TestData.SSH.getProject());
    }
}