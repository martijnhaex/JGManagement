package be.haexnet.jgmanagement.jira;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static be.haexnet.jgmanagement.jira.JiraManager.manageJira;

public class JiraManagerTest {
    static final String USERNAME = "admin";
    static final String PASSWORD = "admin-123";
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
        manageJira().withUsername(USERNAME).withPassword(PASSWORD).withUrl(URL).showIssue(null);
    }

    @Test
    public void issueKeyEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the issue key first.");
        manageJira().withUsername(USERNAME).withPassword(PASSWORD).withUrl(URL).showIssue("");
    }
}