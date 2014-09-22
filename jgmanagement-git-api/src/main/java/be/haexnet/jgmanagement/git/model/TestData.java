package be.haexnet.jgmanagement.git.model;

//TODO remove this class, once configuration (of database) is in place! And needs to be feed to this API!
public enum TestData {
    SSH(Project.of("SSHCredentialManagement", "~/Documents/workspace/SSHCredentialManagement")),
    GIT_JIRA(Project.of("JGManagement", "~/Documents/worspace/JGManagement"));

    private Project project;

    TestData(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
