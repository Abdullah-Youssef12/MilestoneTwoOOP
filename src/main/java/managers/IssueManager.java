package managers;

import database.Database;
import entities.issues.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueManager {

    // Save issue (only if not already saved)
    public static void save(Issue issue) {
        if (issue == null) return;

        if (findById(issue.getId()) == null) {
            Database.issues.add(issue);
        }
    }

    // Find issue by ID
    public static Issue findById(String id) {
        if (id == null) return null;

        for (Issue issue : Database.issues) {
            if (issue.getId().equals(id)) {
                return issue;
            }
        }
        return null;
    }

    // Find issue by title (case-insensitive)
    public static Issue findByTitle(String title) {
        if (title == null) return null;

        for (Issue issue : Database.issues) {
            if (issue.getTitle().equalsIgnoreCase(title.trim())) {
                return issue;
            }
        }
        return null;
    }

    // Return all issues
    public static List<Issue> findAll() {
        return new ArrayList<>(Database.issues);
    }

    // Find issues assigned to a specific user
    public static List<Issue> findByAssignee(String userId) {
        List<Issue> result = new ArrayList<>();
        if (userId == null) return result;

        for (Issue issue : Database.issues) {
            if (issue.getAssignee() != null &&
                    issue.getAssignee().getId().equals(userId)) {
                result.add(issue);
            }
        }
        return result;
    }

    // Find issues by status
    public static List<Issue> findByStatus(String status) {
        List<Issue> result = new ArrayList<>();
        if (status == null) return result;

        for (Issue issue : Database.issues) {
            if (status.equalsIgnoreCase(issue.getStatus())) {
                result.add(issue);
            }
        }
        return result;
    }

    // Remove issue
    public static boolean delete(String id) {
        Issue issue = findById(id);
        if (issue == null) return false;

        return Database.issues.remove(issue);
    }
}

