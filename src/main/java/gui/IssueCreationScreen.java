package gui;

import entities.issues.Issue;
import entities.issues.IssueType;
import entities.issues.Priority;
import entities.users.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.IssueCreationService;

/**
 * Screen for creating work items (Epic, Story, Task, Bug).
 */
public class IssueCreationScreen {

    private final GridPane root;
    private final Stage stage;
    private final User currentUser;
    private final IssueCreationService service;

    public IssueCreationScreen(Stage stage, User user) {
        this.stage = stage;
        this.currentUser = user;
        this.service = new IssueCreationService();
        this.root = buildUI();
    }

    private GridPane buildUI() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(8);

        ComboBox<IssueType> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(IssueType.EPIC, IssueType.STORY, IssueType.TASK, IssueType.BUG);
        typeBox.setValue(IssueType.EPIC);

        TextField titleField = new TextField();
        TextArea summaryArea = new TextArea();
        summaryArea.setPromptText("Summary");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");

        ComboBox<Priority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(Priority.values());
        priorityBox.setValue(Priority.MEDIUM);

        TextField estimateField = new TextField();
        estimateField.setPromptText("Estimate for Task");
        TextField severityField = new TextField();
        severityField.setPromptText("Severity for Bug");

        Button createBtn = new Button("Create");
        Button cancelBtn = new Button("Cancel");

        int row = 0;
        grid.add(new Label("Type:"), 0, row);
        grid.add(typeBox, 1, row++);
        grid.add(new Label("Title:"), 0, row);
        grid.add(titleField, 1, row++);
        grid.add(new Label("Summary:"), 0, row);
        grid.add(summaryArea, 1, row++);
        grid.add(new Label("Description:"), 0, row);
        grid.add(descriptionArea, 1, row++);
        grid.add(new Label("Priority:"), 0, row);
        grid.add(priorityBox, 1, row++);
        grid.add(new Label("Estimate:"), 0, row);
        grid.add(estimateField, 1, row++);
        grid.add(new Label("Severity:"), 0, row);
        grid.add(severityField, 1, row++);
        grid.add(createBtn, 0, row);
        grid.add(cancelBtn, 1, row);

        createBtn.setOnAction(e -> {
            IssueType type = typeBox.getValue();
            String title = titleField.getText();
            String summary = summaryArea.getText();
            String description = descriptionArea.getText();
            Priority priority = priorityBox.getValue();
            Integer estimate = parseInteger(estimateField.getText());
            String severity = severityField.getText();
            Issue issue = service.createIssue(type, null, title, summary, description,
                    null, priority, null, currentUser,
                    null, null, null, estimate, severity);
            if (issue != null) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Issue created with ID: " + issue.getId());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Failed to create issue. Check your inputs.");
            }
        });

        cancelBtn.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, currentUser);
            stage.setScene(new Scene(dashboard.getRoot(), 600, 400));
        });

        return grid;
    }

    private Integer parseInteger(String text) {
        try {
            return (text == null || text.isBlank()) ? null : Integer.parseInt(text.trim());
        } catch (Exception ex) {
            return null;
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
