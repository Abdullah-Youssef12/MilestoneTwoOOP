package services;

import DAO.StoryDAO;
import DAO.TaskDAO;
import entities.users.Developer;
import entities.users.User;
import entities.issues.Task;
import entities.issues.Story;

public class TaskService {

    // Assign a task to a developer
    public boolean assignTask(Task task, User user) {

        // Only developers can receive tasks
        if (!(user instanceof Developer)) {
            return false;
        }

        // Assign the task
        task.setAssignee(user);

        // Save/update task in storage
        TaskDAO.save(task);
        return true;
    }

    // Change task status
    public boolean changeStatus(Task task, String newStatus) {
        // You can limit allowed statuses if you want
        task.setStatus(newStatus);
        return true;
    }

    // Add a task to a story
    public boolean addTaskToStory(Task task, Story story) {
        story.getTasks().add(task);
        StoryDAO.save(story);   // update story
        TaskDAO.save(task);     // update task
        return true;
    }
}
