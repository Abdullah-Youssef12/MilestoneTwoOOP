package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Agile Task Coordinator application.
 *
 * <p>The application starts by showing the login screen.  Once a user logs in,
 * the LoginScreen itself will navigate to the DashboardScreen appropriate for
 * the user’s role.  From there stakeholders can create new issues; Scrum Masters
 * can view the backlog and assign issues to developers; developers and QA engineers
 * can view their assigned issues along with who assigned them; and Scrum Masters
 * can also manage sprints.  All of these flows are handled by the individual
 * screens (DashboardScreen, BacklogScreen, AssignedIssueScreen, etc.).</p>
 */
public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the window title
        primaryStage.setTitle("Agile Task Coordinator");

        // Initialize and load the login screen as the first view
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene loginScene = new Scene(loginScreen.getRoot(), 400, 300);

        // Display the login screen
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
