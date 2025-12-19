package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Entry point for the JavaFX application.
 * Displays the login screen as the first view.
 */
public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene scene = new Scene(loginScreen.getRoot(), 400, 300);
        primaryStage.setTitle("Agile Task Coordinator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
