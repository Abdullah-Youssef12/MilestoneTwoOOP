package managers;

import DAO.UserDAO;
import database.Database;
import entities.users.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public static void save(User user) {
        if (user == null) return;
        if (findById(user.getId()) == null) {
            Database.users.add(user);
        }
    }

    public static User findById(String id) {
        if (id == null) return null;
        return UserDAO.findById(id);
    }

    public static User findByEmail(String email) {
        if (email == null) return null;
        String e = email.trim();
        for (User u : Database.users) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(e)) {
                return u;
            }
        }
        return null;
    }

    public static List<User> findAll() {
        return new ArrayList<>(Database.users);
    }
}

