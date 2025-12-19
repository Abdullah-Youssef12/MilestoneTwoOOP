package services;

import entities.users.*;
import managers.UserManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserPersistence {

    private static final String FILE_NAME = "users.csv";

    public static void loadUsers() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String password = parts[3];
                String role = parts[4];
                int capacity = parts.length > 5 ? Integer.parseInt(parts[5]) : 0;
                User user;
                switch (role) {
                    case "Developer":
                        user = new Developer(id, name, email, password, capacity);
                        break;
                    case "QAEngineer":
                        user = new QAEngineer(id, name, email, password, capacity);
                        break;
                    case "ScrumMaster":
                        user = new ScrumMaster(id, name, email, password);
                        break;
                    default:
                        user = new Stakeholder(id, name, email, password);
                        break;
                }
                UserManager.save(user);
            }
        } catch (IOException ignored) {
            // No file yet – ignore
        }
    }

    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getId()).append(",")
                    .append(user.getName()).append(",")
                    .append(user.getEmail()).append(",")
                    .append(user.getPassword()).append(",")
                    .append(user.getClass().getSimpleName());
            if (user instanceof Developer) {
                sb.append(",").append(((Developer) user).getCapacity());
            } else if (user instanceof QAEngineer) {
                sb.append(",").append(((QAEngineer) user).getCapacity());
            }
            bw.write(sb.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
