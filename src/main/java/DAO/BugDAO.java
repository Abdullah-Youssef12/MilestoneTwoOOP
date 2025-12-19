package DAO;

import database.Database;
import entities.issues.Bug;

import java.util.ArrayList;
import java.util.List;

public class BugDAO {

    public static void save(Bug bug) {
        Database.bugs.add(bug);
    }

    public static Bug findById(String id) {
        for (Bug b : Database.bugs) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public static List<Bug> findAll() {
        return new ArrayList<>(Database.bugs);
    }
}
