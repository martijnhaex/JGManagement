package be.haexnet.jgmanagement.jira;

import be.haexnet.jgmanagement.jira.model.Ticket;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static be.haexnet.jgmanagement.jira.JiraManager.manageJira;
import static org.fest.assertions.api.Assertions.assertThat;

public class JiraManagerTest {
    static final String URL = "http://localhost:8080/JIRA";

    @Rule
    public ExpectedException expectedException = ExpectedException.none().handleAssertionErrors();

    private void withConfigurationAssertionError(final String expectedErrorMessage) {
        expectedException.expect(AssertionError.class);
        expectedException.expectMessage(expectedErrorMessage);
    }

    @Test
    public void issueKeyNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the issue key first.");
        manageJira().withUrl(URL).showIssue(null);
    }

    @Test
    public void issueKeyEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the issue key first.");
        manageJira().withUrl(URL).showIssue("");
    }

    @Test
    public void showIssueWithoutAuthorization() throws Exception {
        final Ticket ticket = manageJira().withUrl("https://jira.spring.io").showIssue("XD-2176");

        assertThat(ticket.getIssueKey()).isEqualTo("XD-2176");
        assertThat(ticket.getTitle()).isNotEmpty();
        assertThat(ticket.getDescription()).isNotEmpty();
        assertThat(ticket.getType()).isNotEmpty();
        assertThat(ticket.getStatus()).isNotEmpty();
        assertThat(ticket.getLastUpdate()).isNotNull();
    }
}