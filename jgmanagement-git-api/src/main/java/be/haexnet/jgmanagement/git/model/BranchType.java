package be.haexnet.jgmanagement.git.model;

import org.eclipse.jgit.lib.Constants;

public enum BranchType {
    REMOTE(Constants.R_REMOTES),
    LOCAL(Constants.R_HEADS);

    private final String ref;

    BranchType(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}
