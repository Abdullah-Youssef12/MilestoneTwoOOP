package services;

import DAO.SprintDAO;
import database.Database;
import entities.sprint.Sprint;
import entities.issues.Issue;

import java.time.LocalDate;

public class SprintService {


    // Check if sprint is active today
    public boolean isSprintActive(Sprint sprint) {
        LocalDate today = LocalDate.now();
        return (today.isEqual(sprint.getStartDate()) || today.isAfter(sprint.getStartDate()))
                && (today.isEqual(sprint.getEndDate()) || today.isBefore(sprint.getEndDate()));
    }

    // Return number of items in sprint
    public int getSprintItemCount(Sprint sprint) {
        return sprint.getItems().size();
    }

    public Sprint createSprint(String id,
                               String name,
                               String startDate,
                               String endDate,
                               String goal) {

        // -------- Validation --------
        if (id == null || id.isBlank()) return null;
        if (name == null || name.isBlank()) return null;
        if (startDate == null || endDate == null) return null;

        // Prevent duplicate sprint IDs
        for (Sprint s : Database.sprints) {
            if (s.getId().equals(id)) {
                return null;
            }
        }


        return null;
    }
}