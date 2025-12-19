package services;

import DAO.SprintDAO;
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
}

