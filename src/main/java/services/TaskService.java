package services;

import DAO.StoryDAO;
import DAO.TaskDAO;
import entities.users.Developer;
import entities.issues.Task;
import entities.issues.Story;
import entities.users.User;
import managers.UserManager;

public class TaskService {

    // Assign a task to a developer
    // in TaskService
    public boolean assignTask(String taskId, String developerId) {
        Task task = TaskDAO.findById(taskId);
        User user = UserManager.findById(developerId);
        if (!(user instanceof Developer) || task == null) {
            return false;
        }
        task.setAssignee(user);
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
