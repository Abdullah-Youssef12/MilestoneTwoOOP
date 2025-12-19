package DAO;

import database.Database;
import entities.issues.Epic;

import java.util.ArrayList;
import java.util.List;

public class EpicDAO {

    public static void save(Epic epic) {
        Database.epics.add(epic);
    }

    public static Epic findById(String id) {
        for (Epic e : Database.epics) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public static List<Epic> findAll() {
        return new ArrayList<>(Database.epics);
    }
}
