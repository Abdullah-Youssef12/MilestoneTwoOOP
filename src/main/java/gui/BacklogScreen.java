package gui;

import entities.issues.Issue;
import entities.users.ScrumMaster;
import entities.users.TechnicalStaff;
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

import java.util.List;

public class BacklogScreen {

    private final BorderPane root;
    private final Stage stage;
    private final ScrumMaster sm;
    private  BacklogService backlogService;
    private final TaskService taskService;

    public BacklogScreen(Stage stage, ScrumMaster sm) {
        this.stage = stage;
        this.sm = sm;
        this.backlogService = new BacklogService();
        this.taskService = new TaskService();
        this.root = buildUI();
    }



    public TaskService getTaskService() {
        return taskService;
    }

    public ScrumMaster getSm() {
        return sm;
    }

    public Stage getStage() {
        return stage;
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

        List<Issue> backlog = (List<Issue>) backlogService.getBacklogService();
        ObservableList<Issue> data = FXCollections.observableArrayList(backlog);
        table.setItems(data);

        // Assign selected issue to developer
        Button assignBtn = new Button("Assign to Developer");
        TextField devIdField = new TextField();
        devIdField.setPromptText("Developer ID");
        assignBtn.setOnAction(e -> {
            Issue selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && !devIdField.getText().isBlank()) {
                String devId = devIdField.getText().trim();
                // assign via TaskService
                taskService.assignTask(selected.getId(), devId);
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
