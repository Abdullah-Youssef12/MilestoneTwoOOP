package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the Agile Task Coordinator application.
 * This version simply shows the LoginScreen first, and
 * then navigates to the appropriate DashboardScreen based on
 * the user's role (Stakeholder, Developer, Scrum Master, or QA).
 *
 * All new controllers (StakeholderController, ScrumMasterController,
 * DeveloperController, QAController) are invoked by the screens
 * (DashboardScreen, EpicRequestScreen, BugRequestScreen, etc.).
 */
public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agile Task Coordinator");

        // Show the login screen
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene loginScene = new Scene(loginScreen.getRoot(), 400, 300);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
