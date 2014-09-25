package be.haexnet.jgmanagement.jira;

import be.haexnet.jgmanagement.jira.api.JiraManagerUtil;
import be.haexnet.jgmanagement.jira.api.model.Issue;
import be.haexnet.jgmanagement.jira.model.Credential;
import be.haexnet.jgmanagement.jira.model.Ticket;
import be.haexnet.jgmanagement.jira.model.mapper.TicketMapper;

import static be.haexnet.jgmanagement.jira.builder.CredentialBuilder.credential;
import static org.fest.assertions.api.Assertions.assertThat;

public class JiraManager {
    private boolean authorize;
    private String username;
    private String password;
    private String url;

    private JiraManager() {
    }

    public static JiraManager manageJira() {
        return new JiraManager();
    }

    public JiraManager withAuthorization(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.authorize = true;
        return this;
    }

    public JiraManager withUrl(final String url) {
        this.url = url;
        return this;
    }

    public Ticket showIssue(final String issueKey) {
        assertThat(issueKey).overridingErrorMessage("Cannot call JIRA, please set the issue key first.").isNotEmpty();

        return new TicketMapper().map(
                getIssue(issueKey)
        );
    }

    private Issue getIssue(String issueKey) {
        return JiraManagerUtil.getIssue(
                createCredential(),
                issueKey
        );
    }

    private Credential createCredential() {
        return credential().withUsername(username).withPassword(password).withUrl(url).withAuthorization(authorize).create();
    }
}
