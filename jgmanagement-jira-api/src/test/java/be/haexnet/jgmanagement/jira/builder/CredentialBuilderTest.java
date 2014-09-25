package be.haexnet.jgmanagement.jira.builder;

import be.haexnet.jgmanagement.jira.model.Credential;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static be.haexnet.jgmanagement.jira.builder.CredentialBuilder.*;
import static org.fest.assertions.api.Assertions.assertThat;


public class CredentialBuilderTest {
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
        credential().create();
    }

    @Test
    public void usernameEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your username first.");
        credential().withUsername("").create();
    }

    @Test
    public void passwordNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your password first.");
        credential().withUsername(USERNAME).create();
    }

    @Test
    public void passwordEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set your password first.");
        credential().withUsername(USERNAME).withPassword("").create();
    }

    @Test
    public void urlNull() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the URL of JIRA first.");
        credential().withUsername(USERNAME).withPassword(PASSWORD).create();
    }

    @Test
    public void urlEmpty() throws Exception {
        withConfigurationAssertionError("Cannot call JIRA, please set the URL of JIRA first.");
        credential().withUsername(USERNAME).withPassword(PASSWORD).withUrl("").create();
    }

    @Test
    public void create() throws Exception {
        final Credential credential = credential().withUsername(USERNAME).withPassword(PASSWORD).withUrl(URL).create();

        assertThat(credential.getUsername()).isEqualTo(USERNAME);
        assertThat(credential.getPassword()).isEqualTo(PASSWORD);
        assertThat(credential.getUrl()).isEqualTo(URL);
    }
}