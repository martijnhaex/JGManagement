package be.haexnet.jgmanagement.jira.model;

import org.joda.time.DateTime;

public class Ticket {
    private final String issueKey;
    private final String title;
    private final String type;
    private final String description;
    private final String status;
    private final DateTime lastUpdate;

    private Ticket(String issueKey, String title, String type, String description, String status, DateTime lastUpdate) {
        this.issueKey = issueKey;
        this.title = title;
        this.type = type;
        this.description = description;
        this.status = status;
        this.lastUpdate = lastUpdate;
    }

    public static Ticket of(String issueKey, String title, String type, String description, String status, DateTime lastUpdate) {
        return new Ticket(issueKey, title, type, description, status, lastUpdate);
    }

    public String getIssueKey() {
        return issueKey;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }
}
