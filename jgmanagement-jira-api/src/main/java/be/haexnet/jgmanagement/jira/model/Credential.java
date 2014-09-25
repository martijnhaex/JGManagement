package be.haexnet.jgmanagement.jira.model;

public class Credential {
    private final String username;
    private final String password;
    private final String url;

    private Credential(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public static Credential of(String username, String password, String url) {
        return new Credential(username, password, url);
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
}
