package entities.sprint;

import entities.issues.Issue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private String id;
    private String name;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String objective;
    private final List<Issue> items = new ArrayList<>();

    public Sprint(String id, String name, LocalDate startDate, LocalDate endDate, String objective) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.objective = objective;
    }
    public Sprint(LocalDate startDate, LocalDate endDate, String objective) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.objective = objective;
    }

    public List<Issue> getItems() { return items; }
    public String getId() { return id; }

    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {return endDate;}

    public String getObjective() {
        return objective;
    }

    public String getName() {
        return name;
    }

    public boolean containsIssue(String issueId) {
        for (Issue i : items) {
            if (i.getId().equals(issueId)) return true;
        }
        return false;
    }

    public boolean addIssue(Issue issue) {
        if (issue == null) return false;
        if (containsIssue(issue.getId())) return false;
        return items.add(issue);
    }

    public boolean removeIssue(String issueId) {
        return items.removeIf(i -> i.getId().equals(issueId));
    }




}

