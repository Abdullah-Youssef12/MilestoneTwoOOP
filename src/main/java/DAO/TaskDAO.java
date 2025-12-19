package DAO;

import database.Database;
import entities.issues.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public static void save(Task task) {
        Database.tasks.add(task);
    }

    public static Task findById(String id) {
        for (Task t : Database.tasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static List<Task> findAll() {
        return new ArrayList<>(Database.tasks);
    }
}
