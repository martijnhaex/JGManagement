package be.haexnet.jgmanagement.jira.api;

import be.haexnet.jgmanagement.jira.api.model.Issue;
import be.haexnet.jgmanagement.jira.model.Credential;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class JiraManagerUtil {
    private static final String REST_API_ISSUE_URL = "/rest/api/latest/issue/";

    public static Issue getIssue(final Credential credential, final String issueKey) {
        final HttpHeaders header = createHeader(credential);

        return new RestTemplate()
                .exchange(
                        credential.getUrl() + REST_API_ISSUE_URL + issueKey,
                        HttpMethod.GET,
                        new HttpEntity<String>(header),
                        Issue.class
                )
                .getBody();
    }

    private static HttpHeaders createHeader(final Credential credential) {
        final HttpHeaders header = new HttpHeaders();
        if (credential.isAuthorize()) {
            header.add("Authorization", "Basic " + createAuthorization(credential));
        }
        return header;
    }

    private static String createAuthorization(final Credential credential) {
        return new String(
                Base64.encode(
                        new String(credential.getUsername() + ":" + credential.getPassword()).getBytes()
                )
        );
    }
}
