package gui;

import entities.issues.Issue;
import entities.users.ScrumMaster;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.BacklogService;
import services.TaskService;
import managers.IssueManager;

public class BacklogScreen {

    private final BorderPane root;
    private final Stage stage;
    private final ScrumMaster sm;
    private final TaskService taskService;

    public BacklogScreen(Stage stage, ScrumMaster sm) {
        this.stage = stage;
        this.sm = sm;
        this.taskService = new TaskService();
        this.root = buildUI();
    }

    private BorderPane buildUI() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        Label header = new Label("Backlog (Issues not in any sprint)");
        TableView<Issue> table = new TableView<>();
        TableColumn<Issue, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Issue, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        TableColumn<Issue, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        table.getColumns().addAll(idCol, titleCol, statusCol);

        // use IssueManager so any newly created issues are visible
        ObservableList<Issue> data = FXCollections.observableArrayList(IssueManager.findAll());
        table.setItems(data);

        Button assignBtn = new Button("Assign to Developer");
        TextField devIdField = new TextField();
        devIdField.setPromptText("Developer ID");
        assignBtn.setOnAction(e -> {
            Issue selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && !devIdField.getText().isBlank()) {
                String devId = devIdField.getText().trim();
                // record the Scrum Master as the assigner
                taskService.assignTask(selected.getId(), devId, sm.getId());
                data.remove(selected);
                showAlert(Alert.AlertType.INFORMATION, "Assigned", "Issue assigned to developer");
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            DashboardScreen dash = new DashboardScreen(stage, sm);
            stage.setScene(new Scene(dash.getRoot(), 600, 400));
        });

        HBox assignBox = new HBox(5, new Label("Dev ID:"), devIdField, assignBtn, backBtn);
        assignBox.setPadding(new Insets(10));

        pane.setTop(header);
        pane.setCenter(table);
        pane.setBottom(assignBox);
        return pane;
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
