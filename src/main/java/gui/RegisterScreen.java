package gui;

import entities.users.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.AccessService;

/**
 * Screen for registering new users.
 * Creates the appropriate subclass of User based on the selected role.
 */
public class RegisterScreen {

    private final GridPane root;
    private final Stage stage;
    private final AccessService accessService;

    public RegisterScreen(Stage stage) {
        this.stage = stage;
        this.accessService = new AccessService();
        this.root = buildUI();
    }

    private GridPane buildUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Stakeholder", "Developer", "QAEngineer", "ScrumMaster");
        roleCombo.setValue("Stakeholder");
        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity (for Dev/QA)");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Role:"), 0, 4);
        grid.add(roleCombo, 1, 4);
        grid.add(new Label("Capacity:"), 0, 5);
        grid.add(capacityField, 1, 5);
        grid.add(registerButton, 0, 6);
        grid.add(backButton, 1, 6);

        registerButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = roleCombo.getValue();
            User newUser = null;
            switch (role) {
                case "Stakeholder":
                    newUser = new Stakeholder(id, name, email, password);
                    break;
                case "Developer":
                    int devCap = parseIntSafe(capacityField.getText());
                    newUser = new Developer(id, name, email, password, devCap);
                    break;
                case "QAEngineer":
                    int qaCap = parseIntSafe(capacityField.getText());
                    newUser = new QAEngineer(id, name, email, password, qaCap);
                    break;
                case "ScrumMaster":
                    newUser = new ScrumMaster(id, name, email, password);
                    break;
            }
            boolean ok = accessService.register(newUser);
            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Registration successful", "You can now log in.");
                LoginScreen login = new LoginScreen(stage);
                stage.setScene(new Scene(login.getRoot(), 400, 300));
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration failed", "Please check your inputs or duplicates.");
            }
        });

        backButton.setOnAction(e -> {
            LoginScreen login = new LoginScreen(stage);
            stage.setScene(new Scene(login.getRoot(), 400, 300));
        });

        return grid;
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception ex) {
            return 0;
        }
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
