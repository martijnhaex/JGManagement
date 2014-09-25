package be.haexnet.jgmanagement.jira;

import be.haexnet.jgmanagement.jira.model.Credential;
import be.haexnet.jgmanagement.jira.model.Issue;

import static be.haexnet.jgmanagement.jira.builder.CredentialBuilder.credential;
import static org.fest.assertions.api.Assertions.assertThat;

public class JiraManager {
    private String username;
    private String password;
    private String url;

    private JiraManager() {
    }

    public static JiraManager manageJira() {
        return new JiraManager();
    }

    public JiraManager withUsername(final String username) {
        this.username = username;
        return this;
    }

    public JiraManager withPassword(final String password) {
        this.password = password;
        return this;
    }

    public JiraManager withUrl(final String url) {
        this.url = url;
        return this;
    }

    public Issue showIssue(final String issueKey) {
        assertThat(issueKey).overridingErrorMessage("Cannot call JIRA, please set the issue key first.").isNotEmpty();

        final Credential credential = credential().withUsername(username).withPassword(password).withUrl(url).create();

        //TODO do call!
        return null;
    }
}
