package controllers;

import entities.users.Stakeholder;
import entities.issues.Issue;
import entities.issues.IssueType;
import entities.issues.Priority;
import services.IssueCreationService;
import services.IssueSearchService;

import java.util.List;

/**
 * Handles actions performed by a Stakeholder.
 * Stakeholders can request epics and bugs, and view their own issues.
 */
public class StakeholderController {
    private final IssueCreationService creationService = new IssueCreationService();
    private final IssueSearchService searchService = new IssueSearchService();

    /** Request a new epic */
    public Issue requestEpic(String title, String summary, String description,
                             Priority priority, Stakeholder stakeholder) {
        return creationService.createIssue(IssueType.EPIC, null, title, summary, description,
                "TO DO", priority, null, stakeholder, null,
                null, null, null, null);
    }

    /** Request a new bug */
    public Issue requestBug(String title, String summary, String description, String severity,
                            Priority priority, Stakeholder stakeholder) {
        Issue bug = creationService.createIssue(IssueType.BUG, null, title, summary, description,
                "TO DO", priority, null, stakeholder, null,
                null, null, null, null);
        // You can set severity via labels or a custom field if needed
        bug.getLabels().add(severity);
        return bug;
    }

    /** Get all issues reported by this stakeholder */
    public List<Issue> getMyIssues(String stakeholderId) {
        return searchService.byReporterId(stakeholderId);
    }
}
