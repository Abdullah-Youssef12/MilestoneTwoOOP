package database;

import entities.project.Project;
import entities.users.User;
import entities.issues.*;
import entities.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static List<User> users = new ArrayList<>();
    public static List<Epic> epics = new ArrayList<>();
    public static List<Story> stories = new ArrayList<>();
    public static List<Task> tasks = new ArrayList<>();
    public static List<Bug> bugs = new ArrayList<>();
    public static List<Sprint> sprints = new ArrayList<>();
    public static List<Project> projects = new ArrayList<>();
    public static List<Issue> issues = new ArrayList<>();

}

