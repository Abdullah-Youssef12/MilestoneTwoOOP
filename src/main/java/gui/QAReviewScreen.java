package gui;

import controllers.QAController;
import entities.issues.Issue;
import entities.users.QAEngineer;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Screen for QA engineers to review assigned bugs/tasks.
 */
public class QAReviewScreen {
    private final BorderPane root;
    private final Stage stage;
    private final QAEngineer qa;
    private final QAController controller;

    public QAReviewScreen(Stage stage, QAEngineer qaEngineer) {
        this.stage = stage;
        this.qa = qaEngineer;
        this.controller = new QAController();
        this.root = buildUI();
    }

    private BorderPane buildUI() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));
        Label header = new Label("QA Review");

        // Table of assigned issues
        TableView<Issue> table = new TableView<>();
        TableColumn<Issue, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getId()));
        TableColumn<Issue, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getTitle()));
        TableColumn<Issue, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getStatus()));
        TableColumn<Issue, String> reporterCol = new TableColumn<>("Reporter");
        reporterCol.setCellValueFactory(d -> {
            if (d.getValue().getReporter() == null) {
                return new javafx.beans.property.SimpleStringProperty("");
            }
            return new javafx.beans.property.SimpleStringProperty(d.getValue().getReporter().getName());
        });
        table.getColumns().addAll(idCol, titleCol, statusCol, reporterCol);

        // Controls for reviewing issues
        TextField newStatusField = new TextField();
        newStatusField.setPromptText("New Status");
        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> {
            Issue selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean ok = controller.reviewIssue(selected, newStatusField.getText().trim());
                if (ok) {
                    showAlert(Alert.AlertType.INFORMATION, "Updated", "Status updated.");
                    refreshTable(table);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Could not update status.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Issue Selected", "Select an issue first.");
            }
        });

        Button back = new Button("Back");
        back.setOnAction(e -> {
            DashboardScreen dash = new DashboardScreen(stage, qa);
            stage.setScene(new Scene(dash.getRoot(), 400, 300));
        });

        pane.setTop(header);
        pane.setCenter(table);
        pane.setBottom(new HBox(5, newStatusField, updateBtn, back));
        refreshTable(table);
        return pane;
    }

    private void refreshTable(TableView<Issue> table) {
        List<Issue> issues = controller.getAssignedIssues(qa.getId());
        table.setItems(FXCollections.observableArrayList(issues));
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
