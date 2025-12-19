package managers;

import database.Database;
import entities.project.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectManager {

    public static void save(Project project) {
        if (findById(project.getId()) == null) {
            Database.projects.add(project);
        }
    }

    public static Project findById(String id) {
        for (Project p : Database.projects) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public static Project findByName(String name) {
        for (Project p : Database.projects) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public static List<Project> findAll() {
        return new ArrayList<>(Database.projects);
    }
}

