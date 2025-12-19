package entities.issues;

import entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Issue {

    private final List<Story> stories = new ArrayList<>();

    public Epic(String id,
                String title,
                String summary,
                String description,
                String status,
                User assignee,
                Priority priority,
                User reporter) {

        super(id, title, summary, description, status, assignee, priority, reporter);
    }

    public List<Story> getStories() {
        return stories;
    }

    public boolean addStory(Story story) {
        if (story == null) return false;
        if (stories.contains(story)) return false;
        stories.add(story);
        return true;
    }
}
