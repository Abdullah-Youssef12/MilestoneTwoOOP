package services;

import entities.users.User;
import managers.UserManager;

public class AccessService {

    // Register new user
    public boolean register(User user) {
        if (user == null) return false;

        // basic checks
        if (isBlank(user.getId())) return false;
        if (isBlank(user.getName())) return false;
        if (isBlank(user.getEmail())) return false;
        if (isBlank(user.getPassword())) return false;

        // email must be unique
        if (UserManager.findByEmail(user.getEmail()) != null) {
            return false;
        }

        // id should be unique too (optional but good)
        if (UserManager.findById(user.getId()) != null) {
            return false;
        }

        UserManager.save(user);
        return true;
    }

    // Login (returns user if correct, null otherwise)
    public User login(String email, String password) {
        if (isBlank(email) || isBlank(password)) return null;

        User user = UserManager.findByEmail(email);
        if (user == null) return null;

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
