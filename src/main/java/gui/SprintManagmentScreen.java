package gui;

import entities.sprint.Sprint;
import entities.users.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.SprintService;

import java.time.LocalDate;

/**
 * Minimal UI for creating new sprints.
 * Only collects start date, end date and objective.
 */
public class SprintManagmentScreen {

    private  GridPane root;
    private Stage stage;
    private SprintService sprintService;

    public SprintManagmentScreen(Stage stage) {
        this.stage = stage;
        this.sprintService = new SprintService();
        this.root = buildUI();
    }

    public SprintManagmentScreen(Stage stage, User currentUser) {
    }

    private GridPane buildUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(8);

        javafx.scene.control.DatePicker startPicker = new DatePicker(LocalDate.now());
        DatePicker endPicker = new DatePicker(LocalDate.now().plusWeeks(2));
        TextField objectiveField = new TextField();

        Button createBtn = new Button("Create Sprint");
        Button backBtn = new Button("Back");

        int row = 0;
        grid.add(new Label("Start Date:"), 0, row);
        grid.add(startPicker, 1, row++);
        grid.add(new Label("End Date:"), 0, row);
        grid.add(endPicker, 1, row++);
        grid.add(new Label("Objective:"), 0, row);
        grid.add(objectiveField, 1, row++);
        grid.add(createBtn, 0, row);
        grid.add(backBtn, 1, row);

        createBtn.setOnAction(e -> {
            LocalDate start = startPicker.getValue();
            LocalDate end = endPicker.getValue();
            String objective = objectiveField.getText();
            if (start != null && end != null && !objective.isBlank() && !end.isBefore(start)) {
                // Note: adjust this constructor to match your Sprint class
                Sprint sprint = new Sprint(start,end,objective);
                // you can add sprint to SprintDAO or SprintManager here
                showAlert(Alert.AlertType.INFORMATION, "Success", "Sprint created successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid input", "Please check the dates and objective.");
            }
        });

        backBtn.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, null);
            stage.setScene(new Scene(dashboard.getRoot(), 600, 400));
        });

        return grid;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public GridPane getRoot() {
        return root;
    }
}
