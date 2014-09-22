package be.haexnet.jgmanagement.git.model;

import org.joda.time.DateTime;

public class Branch {
    private final String name;
    private final DateTime lastCommit;
    private final Boolean merged;

    private final Project project;

    private Branch(String name, DateTime lastCommit, Boolean merged, Project project) {
        this.name = name;
        this.lastCommit = lastCommit;
        this.merged = merged;
        this.project = project;
    }

    public static Branch of (String name, DateTime lastCommit, Boolean merged, Project project) {
        return new Branch(name, lastCommit, merged, project);
    }

    public String getName() {
        return name;
    }

    public DateTime getLastCommit() {
        return lastCommit;
    }

    public Boolean getMerged() {
        return merged;
    }

    public Project getProject() {
        return project;
    }
}
