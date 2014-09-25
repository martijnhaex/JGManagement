package be.haexnet.jgmanagement.jira.builder;

import be.haexnet.jgmanagement.jira.model.Credential;

import static org.fest.assertions.api.Assertions.assertThat;

public class CredentialBuilder {
    private boolean authorize;
    private String username;
    private String password;
    private String url;

    private CredentialBuilder() { }

    public static CredentialBuilder credential() {
        return new CredentialBuilder();
    }

    public CredentialBuilder withUsername(final String username) {
        this.username = username;
        return this;
    }

    public CredentialBuilder withPassword(final String password) {
        this.password = password;
        return this;
    }

    public CredentialBuilder withUrl(final String url) {
        this.url = url;
        return this;
    }

    public CredentialBuilder withAuthorization(final boolean authorize) {
        this.authorize = authorize;
        return this;
    }

    public Credential create() {
        if (authorize) {
            assertThat(username).overridingErrorMessage("Cannot call JIRA, please set your username first.").isNotEmpty();
            assertThat(password).overridingErrorMessage("Cannot call JIRA, please set your password first.").isNotEmpty();
        }
        assertThat(url).overridingErrorMessage("Cannot call JIRA, please set the URL of JIRA first.").isNotEmpty();

        return Credential.of(
                username,
                password,
                url,
                authorize
        );
    }
}
