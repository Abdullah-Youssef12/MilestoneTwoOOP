package entities.issues;

import entities.users.User;

public class Task extends Issue {

    private int estimate;     // story points or hours
    private int loggedWork;   // work done so far

    public Task(String id,
                String title,
                String summary,
                String description,
                String status,
                User assignee,
                Priority priority,
                int estimate,
                User reporter) {

        super(id, title, summary, description, status, assignee, priority, reporter);
        this.estimate = Math.max(estimate, 0);
        this.loggedWork = 0;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = Math.max(estimate, 0);
    }

    public int getLoggedWork() {
        return loggedWork;
    }

    public void logWork(int amount) {
        if (amount <= 0) return;
        loggedWork += amount;
        if (loggedWork > estimate) {
            loggedWork = estimate;
        }
    }

    public int getRemainingWork() {
        return estimate - loggedWork;
    }
}
