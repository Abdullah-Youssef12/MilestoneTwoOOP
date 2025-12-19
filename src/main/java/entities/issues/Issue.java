package entities.issues;

import entities.common.Commentable;
import entities.common.Identifiable;
import entities.common.Labelable;
import entities.common.Linkable;
import entities.common.Prioritizable;
import entities.users.User;
import entities.project.Component;

import java.util.ArrayList;
import java.util.List;

public abstract  class Issue implements
        Identifiable, Labelable, Prioritizable, Commentable, Linkable {

    protected String id;
    protected String title;        // name
    protected String summary;      // short summary
    protected String description;  // full description
    protected String status;       // TODO, IN_PROGRESS, DONE, etc.
    protected User assignee;       // can be null
    protected User reporter;  // who created the issue
    protected List<Component> components = new ArrayList<>();



    protected Priority priority = Priority.MEDIUM;

    protected List<String> labels = new ArrayList<>();
    protected List<IssueLink> links = new ArrayList<>();
    protected List<String> comments = new ArrayList<>();

    public Issue(String id, String title, String summary, String description,
                 String status, User assignee, Priority priority, User reporter) {

        this.id = id;
        this.title = title;
        this.summary = (summary == null) ? "" : summary;
        this.description = (description == null) ? "" : description;
        this.status = status;
        this.assignee = assignee;
        if (priority != null) this.priority = priority;
        this.reporter = reporter;
    }


    // ---------------- Identifiable ----------------
    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // ---------------- Basic fields ----------------
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getAssignee() { return assignee; }
    public void setAssignee(User assignee) { this.assignee = assignee; }

    // ---------------- Prioritizable ----------------
    @Override
    public Priority getPriority() { return priority; }

    @Override
    public void setPriority(Priority p) {
        if (p != null) this.priority = p;
    }

    // ---------------- Labelable ----------------
    @Override
    public List<String> getLabels() { return labels; }

    // ---------------- Linkable ----------------
    @Override
    public List<IssueLink> getLinks() { return links; }

    // ---------------- Commentable ----------------
    @Override
    public List<String> getComments() { return comments; }

    public User getReporter() { return reporter; }
    public void setReporter(User reporter) { this.reporter = reporter; }

    public List<Component> getComponents() { return components; }

}
