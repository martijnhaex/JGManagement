package be.haexnet.jgmanagement.git.model;

public class Project {
    private final String name;
    private final String directoryLocation;

    private Project(String name, String directoryLocation) {
        this.name = name;
        this.directoryLocation = directoryLocation;
    }

    public static Project of(String name, String directoryLocation) {
        return new Project(name, directoryLocation);
    }

    public String getName() {
        return name;
    }

    public String getDirectoryLocation() {
        return directoryLocation;
    }
}
