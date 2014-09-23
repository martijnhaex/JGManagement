package be.haexnet.jgmanagement.git.model;

import org.joda.time.DateTime;

public class Branch {
    private final String name;
    private final DateTime lastCommit;
    private final Boolean merged;

    private final Project project;

    private final BranchType type;

    private Branch(String name, DateTime lastCommit, Boolean merged, Project project, BranchType type) {
        this.name = name;
        this.lastCommit = lastCommit;
        this.merged = merged;
        this.project = project;
        this.type = type;
    }

    public static Branch of (String name, DateTime lastCommit, Boolean merged, Project project, BranchType type) {
        return new Branch(name, lastCommit, merged, project, type);
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

    public BranchType getType() {
        return type;
    }
}
