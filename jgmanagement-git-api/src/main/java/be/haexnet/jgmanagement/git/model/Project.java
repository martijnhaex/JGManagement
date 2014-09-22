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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (directoryLocation != null ? !directoryLocation.equals(project.directoryLocation) : project.directoryLocation != null)
            return false;
        if (name != null ? !name.equals(project.name) : project.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (directoryLocation != null ? directoryLocation.hashCode() : 0);
        return result;
    }
}
