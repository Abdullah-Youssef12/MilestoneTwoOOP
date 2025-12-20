package gui;

import controllers.StakeholderController;
import entities.issues.Issue;
import entities.issues.Priority;
import entities.users.Stakeholder;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Screen for stakeholders to request a new bug and see their bugs.
 */
public class BugRequestScreen {
    private final BorderPane root;
    private final Stage stage;
    private final Stakeholder stakeholder;
    private final StakeholderController controller;

    public BugRequestScreen(Stage stage, Stakeholder stakeholder) {
        this.stage = stage;
        this.stakeholder = stakeholder;
        this.controller = new StakeholderController();
        this.root = buildUI();
    }

    private BorderPane buildUI() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        Label header = new Label("Request Bug");
        VBox formBox = new VBox(5);
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField summaryField = new TextField();
        summaryField.setPromptText("Summary");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        TextField severityField = new TextField();
        severityField.setPromptText("Severity (Low/Medium/High)");
        ComboBox<Priority> priorityBox = new ComboBox<>(
                FXCollections.observableArrayList(Priority.values())
        );
        priorityBox.setValue(Priority.MEDIUM);
        Button createBtn = new Button("Create");
        createBtn.setOnAction(e -> {
            Issue bug = controller.requestBug(
                    titleField.getText().trim(),
                    summaryField.getText().trim(),
                    descriptionArea.getText().trim(),
                    severityField.getText().trim(),
                    priorityBox.getValue(),
                    stakeholder
            );
            if (bug != null) {
                showAlert(Alert.AlertType.INFORMATION, "Bug Created", "Bug ID: " + bug.getId());
                refreshTable();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create bug.");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, stakeholder);
            stage.setScene(new Scene(dashboard.getRoot(), 400, 300));
        });

        HBox buttonBox = new HBox(5, createBtn, backBtn);
        formBox.getChildren().addAll(titleField, summaryField, descriptionArea,
                severityField, priorityBox, buttonBox);

        // Table of my bugs
        TableView<Issue> table = new TableView<>();
        TableColumn<Issue, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Issue, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        TableColumn<Issue, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        table.getColumns().addAll(idCol, titleCol, statusCol);

        pane.setTop(header);
        pane.setCenter(table);
        pane.setBottom(formBox);

        refreshTable(table);
        return pane;
    }

    /** Reload the bug list table */
    private void refreshTable() {
        // dummy call for doc; real call is in the buildUI with table reference
    }
    private void refreshTable(TableView<Issue> table) {
        List<Issue> myIssues = controller.getMyIssues(stakeholder.getId());
        table.setItems(FXCollections.observableArrayList(myIssues));
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}
