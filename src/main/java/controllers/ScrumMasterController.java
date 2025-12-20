package controllers;

import entities.issues.Bug;
import entities.issues.Epic;
import entities.issues.Issue;
import entities.issues.IssueType;
import entities.issues.Priority;
import entities.issues.Story;
import entities.project.Component;
import entities.users.Developer;
import entities.users.QAEngineer;
import entities.users.ScrumMaster;
import entities.users.User;
import managers.IssueManager;
import services.IssueCreationService;
import services.IssueSearchService;

import java.util.List;

/**
 * Handles Scrum Master actions:
 * - viewing unassigned epics/bugs
 * - creating stories from epics and assigning to developers
 * - assigning bugs to QA engineers
 */
public class ScrumMasterController {
    private final IssueCreationService creationService = new IssueCreationService();
    private final IssueSearchService searchService = new IssueSearchService();
    private final IssueManager issueManager = new IssueManager();

    /** Get all epics that are not in a sprint (i.e., unassigned) */
    public List<Epic> getUnassignedEpics() {
        return searchService.getUnassignedEpics();
    }

    /** Get all unassigned bugs (to be assigned to QA) */
    public List<Bug> getUnassignedBugs() {
        return searchService.getUnassignedBugs();
    }

    /** Create a story from an epic and assign it to a developer */
    public Story createStoryFromEpic(Epic epic, Developer developer, ScrumMaster sm,
                                     String storyTitle, String storySummary) {
        Story story = (Story) creationService.createIssue(
                IssueType.STORY,
                epic.getId(),
                storyTitle,
                storySummary,
                epic.getDescription(),
                "TO DO",
                Priority.MEDIUM,
                (User) null,
                sm,
                (List<String>) epic,
                (List<Component>) null,
                null,
                5,
                null
        );
        story.setAssignee(developer);
        story.setAssignedBy(sm);
        // persist
        IssueManager.save(story);
        return story;
    }

    /** Assign a bug to a QA engineer */
    public boolean assignBugToQa(Bug bug, QAEngineer qa, ScrumMaster sm) {
        bug.setAssignee(qa);
        bug.setAssignedBy(sm);
        IssueManager.save(bug);
        return true;
    }
}
