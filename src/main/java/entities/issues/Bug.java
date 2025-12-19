package entities.issues;

import entities.users.User;

public class Bug extends Issue {

    private String severity; // Minor, Major, Critical

    public Bug(String id,
               String title,
               String summary,
               String description,
               String status,
               User assignee,
               Priority priority,
               String severity,
               User reporter) {

        super(id, title, summary, description, status, assignee, priority, reporter);
        this.severity = (severity == null || severity.isBlank())
                ? "Minor"
                : severity.trim();
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        if (severity != null && !severity.isBlank()) {
            this.severity = severity.trim();
        }
    }
}
