package services;

import entities.issues.*;
import entities.project.Component;
import managers.IssueManager;

import java.util.ArrayList;
import java.util.List;

public class IssueSearchService {

    // -------- Basic filters (Jira-style) --------

    public List<Issue> byStatus(String status) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(status)) return result;

        for (Issue i : IssueManager.findAll()) {
            if (i.getStatus() != null && i.getStatus().equalsIgnoreCase(status.trim())) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Issue> byAssigneeId(String assigneeId) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(assigneeId)) return result;

        for (Issue i : IssueManager.findAll()) {
            if (i.getAssignee() != null && assigneeId.trim().equals(i.getAssignee().getId())) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Issue> byReporterId(String reporterId) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(reporterId)) return result;

        for (Issue i : IssueManager.findAll()) {
            if (i.getReporter() != null && reporterId.trim().equals(i.getReporter().getId())) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Issue> byLabel(String label) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(label)) return result;

        for (Issue i : IssueManager.findAll()) {
            if (i.hasLabel(label.trim())) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Issue> byPriority(Priority priority) {
        List<Issue> result = new ArrayList<>();
        if (priority == null) return result;

        for (Issue i : IssueManager.findAll()) {
            if (i.getPriority() == priority) {
                result.add(i);
            }
        }
        return result;
    }

    // -------- Useful extras (optional but Jira-like) --------

    public List<Issue> textSearch(String keyword) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(keyword)) return result;

        String k = keyword.trim().toLowerCase();

        for (Issue i : IssueManager.findAll()) {
            if (containsIgnoreCase(i.getTitle(), k)
                    || containsIgnoreCase(i.getSummary(), k)
                    || containsIgnoreCase(i.getDescription(), k)) {
                result.add(i);
            }
        }
        return result;
    }

    public List<Issue> byComponentName(String componentName) {
        List<Issue> result = new ArrayList<>();
        if (isBlank(componentName)) return result;

        String target = componentName.trim();

        for (Issue i : IssueManager.findAll()) {
            for (Component c : i.getComponents()) {
                if (c != null && c.getName() != null && c.getName().equalsIgnoreCase(target)) {
                    result.add(i);
                    break;
                }
            }
        }
        return result;
    }

    // detect type by runtime class (simple and works well)
    public List<Issue> byType(IssueType type) {
        List<Issue> result = new ArrayList<>();
        if (type == null) return result;

        for (Issue i : IssueManager.findAll()) {
            if (matchesTypeStrict(i, type)) {
                result.add(i);
            }
        }
        return result;
    }

    // -------- Combined filter (like Jira query) --------

    public List<Issue> filter(String status,
                              String assigneeId,
                              String reporterId,
                              String label,
                              Priority priority,
                              IssueType type,
                              String keyword,
                              String componentName) {

        List<Issue> result = new ArrayList<>();

        for (Issue i : IssueManager.findAll()) {
            if (!matchesStatus(i, status)) continue;
            if (!matchesAssignee(i, assigneeId)) continue;
            if (!matchesReporter(i, reporterId)) continue;
            if (!matchesLabel(i, label)) continue;
            if (!matchesPriority(i, priority)) continue;
            if (!matchesTypeCriteria(i, type)) continue;
            if (!matchesKeyword(i, keyword)) continue;
            if (!matchesComponent(i, componentName)) continue;

            result.add(i);
        }

        return result;
    }

    // -------- Helpers --------

    private boolean matchesStatus(Issue i, String status) {
        if (isBlank(status)) return true;
        return i.getStatus() != null && i.getStatus().equalsIgnoreCase(status.trim());
    }

    private boolean matchesAssignee(Issue i, String assigneeId) {
        if (isBlank(assigneeId)) return true;
        return i.getAssignee() != null && assigneeId.trim().equals(i.getAssignee().getId());
    }

    private boolean matchesReporter(Issue i, String reporterId) {
        if (isBlank(reporterId)) return true;
        return i.getReporter() != null && reporterId.trim().equals(i.getReporter().getId());
    }

    private boolean matchesLabel(Issue i, String label) {
        if (isBlank(label)) return true;
        return i.hasLabel(label.trim());
    }

    private boolean matchesPriority(Issue i, Priority p) {
        if (p == null) return true;
        return i.getPriority() == p;
    }

    // criteria check (null means ignore)
    private boolean matchesTypeCriteria(Issue i, IssueType type) {
        if (type == null) return true;
        return matchesTypeStrict(i, type);
    }

    private boolean matchesKeyword(Issue i, String keyword) {
        if (isBlank(keyword)) return true;
        String k = keyword.trim().toLowerCase();
        return containsIgnoreCase(i.getTitle(), k)
                || containsIgnoreCase(i.getSummary(), k)
                || containsIgnoreCase(i.getDescription(), k);
    }

    private boolean matchesComponent(Issue i, String componentName) {
        if (isBlank(componentName)) return true;
        String target = componentName.trim();

        for (Component c : i.getComponents()) {
            if (c != null && c.getName() != null && c.getName().equalsIgnoreCase(target)) {
                return true;
            }
        }
        return false;
    }

    // the only "type match" method (no duplicates)
    private boolean matchesTypeStrict(Issue i, IssueType type) {
        switch (type) {
            case BUG:   return i instanceof Bug;
            case TASK:  return i instanceof Task;
            case STORY: return i instanceof Story;
            case EPIC:  return i instanceof Epic;
            default:    return false;
        }
    }

    private boolean containsIgnoreCase(String text, String keywordLower) {
        if (text == null) return false;
        return text.toLowerCase().contains(keywordLower);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
