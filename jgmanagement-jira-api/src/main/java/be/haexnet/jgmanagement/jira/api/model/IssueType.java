package be.haexnet.jgmanagement.jira.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType {
    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }
}
