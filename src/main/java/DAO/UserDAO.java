package DAO; // or "managers"

import database.Database;
import entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static void save(User user) {
        Database.users.add(user);
    }

    public static User findByEmail(String email) {
        for (User u : Database.users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public static User findById(String id) {
        for (User u : Database.users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public static List<User> findAll() {
        return new ArrayList<>(Database.users);
    }
}
