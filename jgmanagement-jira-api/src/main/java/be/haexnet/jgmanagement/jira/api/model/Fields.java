package be.haexnet.jgmanagement.jira.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fields {
    @JsonProperty("summary")
    private String title;
    @JsonProperty("issuetype")
    private IssueType issueType;
    @JsonProperty
    private String description;
    @JsonProperty
    private Status status;
    @JsonProperty("updated")
    private DateTime lastUpdate;

    public String getTitle() {
        return title;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public DateTime getLastUpdate() {
        return lastUpdate;
    }
}
