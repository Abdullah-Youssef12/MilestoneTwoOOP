package entities.project;

import entities.issues.Issue;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private final String id;
    private String name;

    private final List<Component> components = new ArrayList<>();
    private final List<Issue> issues = new ArrayList<>();

    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Component> getComponents() { return components; }
    public List<Issue> getIssues() { return issues; }
}

