package gui;

import entities.users.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.AccessService;


/**
 * Simple login screen.
 * On successful login, opens the appropriate dashboard.
 */
public class LoginScreen {

    private final GridPane root;
    private final Stage stage;
    private final AccessService accessService;

    public LoginScreen(Stage stage) {
        this.stage = stage;
        this.accessService = new AccessService();
        this.root = buildUI();
    }

    private GridPane buildUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(registerButton, 1, 2);

        // Correct setOnAction handlers
        loginButton.setOnAction (e-> {
            String email = emailField.getText();
            String password = passwordField.getText();
            User user = accessService.login(email, password);
            if (user != null) {
                DashboardScreen dash = new DashboardScreen(stage, user);
                stage.setScene(new Scene(dash.getRoot(), 600, 400));
            } else {
                showAlert(Alert.AlertType.ERROR,
                        "Login failed",
                        "Invalid email or password");
            }
        });

        registerButton.setOnAction(e-> {
            RegisterScreen register = new RegisterScreen(stage);
            stage.setScene(new Scene(register.getRoot(), 400, 400));
        });

        return grid;
    }

    private void showAlert(Alert.AlertType type,
                           String title,
                           String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public GridPane getRoot() {
        return root;
    }
}