package be.haexnet.jgmanagement.jira.model;

public class Credential {
    private final String username;
    private final String password;
    private final String url;
    private final boolean authorize;

    private Credential(String username, String password, String url, boolean authorize) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.authorize = authorize;
    }

    public static Credential of(String username, String password, String url, boolean authorize) {
        return new Credential(username, password, url, authorize);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public boolean isAuthorize() {
        return authorize;
    }
}
