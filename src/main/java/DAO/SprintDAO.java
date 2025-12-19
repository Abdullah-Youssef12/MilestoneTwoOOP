package DAO;

import database.Database;
import entities.sprint.Sprint;

import java.util.ArrayList;
import java.util.List;

public class SprintDAO {

    public static void save(Sprint sprint) {
        Database.sprints.add(sprint);
    }

    public static Sprint findById(String id) {
        for (Sprint s : Database.sprints) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static List<Sprint> findAll() {
        return new ArrayList<>(Database.sprints);
    }
}
