package be.haexnet.jgmanagement.jira.model.mapper;

import be.haexnet.jgmanagement.jira.api.model.Fields;
import be.haexnet.jgmanagement.jira.api.model.Issue;
import be.haexnet.jgmanagement.jira.model.Ticket;

public class TicketMapper {
    public Ticket map(final Issue issue) {
        final Fields issueFields = issue.getFields();
        return Ticket.of(
                issue.getKey(),
                issueFields.getTitle(),
                issueFields.getIssueType().getName(),
                issueFields.getDescription(),
                issueFields.getStatus().getName(),
                issueFields.getLastUpdate()
        );
    }
}
