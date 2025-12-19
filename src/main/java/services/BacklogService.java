package services;

import entities.issues.Issue;
import entities.sprint.Sprint;
import managers.IssueManager;

import java.util.ArrayList;
import java.util.List;

public class BacklogService {

    // Backlog = all issues that are NOT inside any sprint
    public List<Issue> getBacklog(List<Sprint> allSprints) {
        List<Issue> result = new ArrayList<>();

        for (Issue issue : IssueManager.findAll()) {
            if (!isInAnySprint(issue, allSprints)) {
                result.add(issue);
            }
        }
        return result;
    }

    public boolean addIssueToSprint(Issue issue, Sprint sprint) {
        if (issue == null || sprint == null) return false;

        // prevent duplicates
        for (Issue i : sprint.getItems()) {
            if (i.getId().equals(issue.getId())) return false;
        }
        sprint.getItems().add(issue);
        return true;
    }

    public boolean removeIssueFromSprint(String issueId, Sprint sprint) {
        if (issueId == null || sprint == null) return false;
        return sprint.getItems().removeIf(i -> i.getId().equals(issueId));
    }

    public String issueDetails(Issue i) {
        String assignee = (i.getAssignee() == null) ? "Unassigned" : i.getAssignee().getName();
        String reporter = (i.getReporter() == null) ? "Unknown" : i.getReporter().getName();

        return ""
                + "ID: " + i.getId() + "\n"
                + "Title: " + i.getTitle() + "\n"
                + "Summary: " + i.getSummary() + "\n"
                + "Status: " + i.getStatus() + "\n"
                + "Priority: " + i.getPriority() + "\n"
                + "Assignee: " + assignee + "\n"
                + "Reporter: " + reporter + "\n"
                + "Labels: " + i.getLabels() + "\n"
                + "Components: " + i.getComponents() + "\n"
                + "Links: " + i.getLinks().size();
    }

    private boolean isInAnySprint(Issue issue, List<Sprint> allSprints) {
        if (allSprints == null) return false;

        for (Sprint s : allSprints) {
            for (Issue i : s.getItems()) {
                if (i.getId().equals(issue.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}

