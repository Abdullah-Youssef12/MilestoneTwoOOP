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
 * Now includes separate buttons for epics/bugs for stakeholders,
 * requests and sprint management for scrum masters,
 * tasks for developers, and reviews for QA.
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

        // Stakeholder: request epic or bug
        if (currentUser instanceof Stakeholder) {
            Button requestEpicBtn = new Button("Request Epic");
            requestEpicBtn.setOnAction(e -> {
                EpicRequestScreen epicScreen = new EpicRequestScreen(stage, (Stakeholder) currentUser);
                stage.setScene(new Scene(epicScreen.getRoot(), 600, 400));
            });
            Button requestBugBtn = new Button("Request Bug");
            requestBugBtn.setOnAction(e -> {
                BugRequestScreen bugScreen = new BugRequestScreen(stage, (Stakeholder) currentUser);
                stage.setScene(new Scene(bugScreen.getRoot(), 600, 400));
            });
            box.getChildren().addAll(requestEpicBtn, requestBugBtn);
        }

        // Developer: view assigned tasks
        if (currentUser instanceof Developer) {
            Button myTasksBtn = new Button("My Tasks");
            myTasksBtn.setOnAction(e -> {
                DeveloperIssueScreen devScreen = new DeveloperIssueScreen(stage, (Developer) currentUser);
                stage.setScene(new Scene(devScreen.getRoot(), 600, 400));
            });
            box.getChildren().add(myTasksBtn);
        }

        // QA Engineer: review assigned issues
        if (currentUser instanceof QAEngineer) {
            Button reviewBtn = new Button("Review Issues");
            reviewBtn.setOnAction(e -> {
                QAReviewScreen qaScreen = new QAReviewScreen(stage, (QAEngineer) currentUser);
                stage.setScene(new Scene(qaScreen.getRoot(), 600, 400));
            });
            box.getChildren().add(reviewBtn);
        }

        // Scrum Master: view requests and manage sprints
        if (currentUser instanceof ScrumMaster) {
            Button requestsBtn = new Button("Requests");
            requestsBtn.setOnAction(e -> {
                ScrumMasterRequestScreen reqScreen = new ScrumMasterRequestScreen(stage, (ScrumMaster) currentUser);
                stage.setScene(new Scene(reqScreen.getRoot(), 600, 400));
            });
            Button manageSprintsBtn = new Button("Manage Sprints");
            manageSprintsBtn.setOnAction(e -> {
                SprintManagmentScreen sprintMgmt = new SprintManagmentScreen(stage);
                stage.setScene(new Scene(sprintMgmt.getRoot(), 600, 400));
            });
            box.getChildren().addAll(requestsBtn, manageSprintsBtn);
        }

        // Logout button for everyone
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
