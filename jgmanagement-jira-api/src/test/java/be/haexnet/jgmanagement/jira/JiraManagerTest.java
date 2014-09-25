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
    public void usernameNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your username first.");
        manageJira().showIssue("FEATURE-10");
    }

    @Test
    public void usernameEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your username first.");
        manageJira().withUsername("").showIssue("FEATURE-1");
    }

    @Test
    public void passwordNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your password first.");
        manageJira().withUsername(USERNAME).showIssue("FEATURE-101");
    }

    @Test
    public void passwordEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your password first.");
        manageJira().withUsername(USERNAME).withPassword("").showIssue("FEATURE-75");
    }

    @Test
    public void urlNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the URL of JIRA first.");
        manageJira().withUsername(USERNAME).withPassword(PASSWORD).showIssue("FEATURE-75");
    }

    @Test
    public void urlEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the URL of JIRA first.");
        manageJira().withUsername(USERNAME).withPassword(PASSWORD).withUrl("").showIssue("FEATURE-36");
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