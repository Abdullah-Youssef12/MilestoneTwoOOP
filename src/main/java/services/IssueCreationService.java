package services;

import entities.issues.*;
import entities.project.Component;
import entities.users.User;
import managers.IssueManager;

import java.util.List;

public class IssueCreationService {

    public Issue createIssue(
            IssueType type,
            String id,
            String title,
            String summary,
            String description,
            String status,
            Priority priority,
            User assignee,
            User reporter,
            List<String> labels,
            List<Component> components,
            List<IssueLink> links,
            Integer estimateForTask,
            String severityForBug
    ) {
        if (type == null) return null;
        if (title == null || title.isBlank()) return null;

        if (id == null || id.isBlank()) {
            id = type.name() + "-" + (System.currentTimeMillis() % 1_000_0000L);
        }

        if (summary == null) summary = "";
        if (description == null) description = "";
        if (status == null || status.isBlank()) status = IssueStatus.TODO;
        if (priority == null) priority = Priority.MEDIUM;

        Issue created;

        switch (type) {
            case EPIC:
                created = new Epic(id, title, summary, description, status, assignee, priority, reporter);
                break;

            case STORY:
                created = new Story(id, title, summary, description, status, assignee, priority, reporter);
                break;

            case TASK:
                int est = (estimateForTask == null) ? 0 : Math.max(estimateForTask, 0);
                created = new Task(id, title, summary, description, status, assignee, priority, est, reporter);
                break;

            case BUG:
                String sev = (severityForBug == null || severityForBug.isBlank()) ? "Minor" : severityForBug.trim();
                created = new Bug(id, title, summary, description, status, assignee, priority, sev, reporter);
                break;

            default:
                return null;
        }

        // labels
        if (labels != null) {
            for (String l : labels) {
                created.addLabel(l);
            }
        }

        // components
        if (components != null) {
            created.getComponents().addAll(components);
        }

        // links
        if (links != null) {
            created.getLinks().addAll(links);
        }

        IssueManager.save(created);
        return created;
    }
}
