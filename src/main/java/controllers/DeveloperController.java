package controllers;

import entities.issues.Issue;
import entities.users.Developer;
import managers.IssueManager;
import services.IssueSearchService;

import java.util.List;

/**
 * Handles Developer actions: view assigned issues and update their status.
 */
public class DeveloperController {
    private final IssueSearchService searchService = new IssueSearchService();
    private final IssueManager issueManager = new IssueManager();

    /** Get issues assigned to a developer */
    public List<Issue> getAssignedIssues(String developerId) {
        return searchService.byAssigneeId(developerId);
    }

    /** Update the status of an issue */
    public boolean updateIssueStatus(Issue issue, String newStatus) {
        issue.setStatus(newStatus);
        issueManager.save(issue);
        return true;
    }
}
