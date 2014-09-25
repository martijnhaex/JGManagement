package be.haexnet.jgmanagement.jira.model;

import org.joda.time.DateTime;

public class Issue {
    private final String key;
    private final String title;
    private final String status;
    private final DateTime lastUpdate;
    private final String url;

    private Issue(String url, DateTime lastUpdate, String status, String title, String key) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.status = status;
        this.title = title;
        this.key = key;
    }

    public static Issue of(String url, DateTime lastUpdate, String status, String title, String key) {
        return new Issue(url, lastUpdate, status, title, key);
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }

    public String getUrl() {
        return url;
    }
}
