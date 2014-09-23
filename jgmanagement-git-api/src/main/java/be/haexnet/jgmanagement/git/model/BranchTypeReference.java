package be.haexnet.jgmanagement.git.model;

import org.eclipse.jgit.lib.Ref;

import java.util.Map;

public class BranchTypeReference {
    private final BranchType type;
    private final Map<String, Ref> references;

    private BranchTypeReference(BranchType type, Map<String, Ref> references) {
        this.type = type;
        this.references = references;
    }

    public static BranchTypeReference of(BranchType type, Map<String, Ref> references) {
        return new BranchTypeReference(type, references);
    }

    public BranchType getType() {
        return type;
    }

    public Map<String, Ref> getReferences() {
        return references;
    }
}
