package controllers;

import entities.issues.Issue;
import entities.users.QAEngineer;
import managers.IssueManager;
import services.IssueSearchService;

import java.util.List;

/**
 * Handles QA engineer actions: view assigned bugs and update review status.
 */
public class QAController {
    private final IssueSearchService searchService = new IssueSearchService();
    private final IssueManager issueManager = new IssueManager();

    /** Get bugs assigned to this QA engineer */
    public List<Issue> getAssignedIssues(String qaId) {
        return searchService.byAssigneeId(qaId);
    }

    /** Approve or reject the developer’s work */
    public boolean reviewIssue(Issue issue, String newStatus) {
        issue.setStatus(newStatus);
        issueManager.save(issue);
        return true;
    }
}
