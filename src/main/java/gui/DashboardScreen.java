package gui;

import entities.users.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Dashboard screen.
 * Shows different options based on the user's role.
 */
public class DashboardScreen {

    private final VBox root;
    private final Stage stage;
    private final User currentUser;

    public DashboardScreen(Stage stage, User user) {
        this.stage = stage;
        this.currentUser = user;
        this.root = buildUI();
    }

    private VBox buildUI() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        Label welcome = new Label("Welcome, " + currentUser.getName());
        box.getChildren().add(welcome);

        // Stakeholder: create new work items
        if (currentUser instanceof Stakeholder) {
            Button createIssueBtn = new Button("Create Issue");
            createIssueBtn.setOnAction(e -> {
                IssueCreationScreen creation = new IssueCreationScreen(stage, currentUser);
                stage.setScene(new Scene(creation.getRoot(), 500, 500));
            });
            box.getChildren().add(createIssueBtn);
        }

        // Technical staff: view assigned issues
        if (currentUser instanceof TechnicalStaff) {
            Button viewAssignedBtn = new Button("View Assigned Issues");
            viewAssignedBtn.setOnAction(e -> {
                AssignedIssueScreen assigned = new AssignedIssueScreen(stage, (TechnicalStaff) currentUser);
                stage.setScene(new Scene(assigned.getRoot(), 600, 400));
            });
            box.getChildren().add(viewAssignedBtn);
        }

        // Scrum master: manage sprints
        if (currentUser instanceof ScrumMaster) {
            Button manageSprintsBtn = new Button("Manage Sprints");
            manageSprintsBtn.setOnAction(e -> {
                SprintManagmentScreen sprintMgmt = new SprintManagmentScreen(stage);
                stage.setScene(new Scene(sprintMgmt.getRoot(), 600, 400));
            });
            box.getChildren().add(manageSprintsBtn);
        }

        Button logout = new Button("Logout");
        logout.setOnAction(e -> {
            LoginScreen login = new LoginScreen(stage);
            stage.setScene(new Scene(login.getRoot(), 400, 300));
        });
        box.getChildren().add(logout);

        return box;
    }

    public VBox getRoot() {
        return root;
    }
}
