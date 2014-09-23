package be.haexnet.jgmanagement.git.fixture;

import be.haexnet.jgmanagement.git.model.Project;

import java.util.ArrayList;
import java.util.List;

public final class ProjectFixture {
    private ProjectFixture() {
    }

    public static List<Project> LIST() {
        final List<Project> projects = new ArrayList<>();
        projects.add(SSH());
        projects.add(JIRA_GIT());
        return projects;
    }

    public static Project SSH() {
        return Project.of("SSHCredentialManagement", "/Users/haexmar/Documents/workspace/SSHCredentialManagement");
    }

    public static Project JIRA_GIT() {
        return Project.of("JGManagement", "/Users/haexmar/Documents/workspace/JGManagement");
    }
}
