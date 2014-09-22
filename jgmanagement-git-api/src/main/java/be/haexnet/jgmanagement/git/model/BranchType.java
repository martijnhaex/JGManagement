package be.haexnet.jgmanagement.git.model;

import org.eclipse.jgit.lib.Constants;

import java.util.Arrays;
import java.util.List;

public enum BranchType {
    ALL(Arrays.asList(Constants.R_HEADS, Constants.R_REMOTES)),
    REMOTE(Arrays.asList(Constants.R_REMOTES)),
    LOCAL(Arrays.asList(Constants.R_HEADS));

    private final List<String> refs;

    BranchType(List<String> refs) {
        this.refs = refs;
    }

    public List<String> getRefs() {
        return refs;
    }
}
