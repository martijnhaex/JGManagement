package be.haexnet.jgmanagement.git.model;

public enum BaseBranch {
    MASTER("origin/master"),
    DEVELOP("origin/develop");

    private final String fullyQualifiedName;

    BaseBranch(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }
}
