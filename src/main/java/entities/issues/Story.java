package entities.issues;

import entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class Story extends Issue {

    private final List<Task> tasks = new ArrayList<>();

    public Story(String id,
                 String title,
                 String summary,
                 String description,
                 String status,
                 User assignee,
                 Priority priority,
                 User reporter) {

        super(id, title, summary, description, status, assignee, priority, reporter);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean addTask(Task task) {
        if (task == null) return false;
        if (tasks.contains(task)) return false;
        tasks.add(task);
        return true;
    }
}
