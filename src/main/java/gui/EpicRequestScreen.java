package gui;

import entities.issues.Issue;
import entities.issues.IssueType;
import entities.issues.Priority;
import entities.users.Stakeholder;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.IssueCreationService;
import services.IssueSearchService;

import java.util.List;

public class EpicRequestScreen {

    private final VBox root;
    private final Stage stage;
    private final Stakeholder stakeholder;
    private final IssueCreationService creationService;
    private final IssueSearchService searchService;

    public EpicRequestScreen(Stage stage, Stakeholder user) {
        this.stage = stage;
        this.stakeholder = user;
        this.creationService = new IssueCreationService();
        this.searchService = new IssueSearchService();
        this.root = buildUI();
    }

    private VBox buildUI() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label header = new Label("Request New Epic");
        TextField titleField = new TextField();
        titleField.setPromptText("Epic Title");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Description");

        Button createBtn = new Button("Create Epic");
        createBtn.setOnAction(e -> {
            String title = titleField.getText().trim();
            String desc = descArea.getText().trim();
            if (!title.isBlank() && !desc.isBlank()) {
                Issue epic = creationService.createIssue(
                        IssueType.EPIC,
                        null,
                        title,
                        title,
                        desc,
                        null,
                        Priority.MEDIUM,
                        null,
                        stakeholder,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                showAlert(Alert.AlertType.INFORMATION,
                        "Success", "Epic created: ID " + epic.getId());
                titleField.clear();
                descArea.clear();

            } else {
                showAlert(Alert.AlertType.ERROR,
                        "Input error", "Title and description cannot be empty");
            }
        });

        TableView<Issue> table = new TableView<>();
        TableColumn<Issue, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Issue, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        table.getColumns().addAll(idCol, statusCol);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            DashboardScreen dash = new DashboardScreen(stage, stakeholder);
            stage.setScene(new Scene(dash.getRoot(), 600, 400));
        });

        vbox.getChildren().addAll(header, titleField, descArea, createBtn, table, backBtn);
        refreshTable(table);
        return vbox;
    }

    /** Refreshes the table with the stakeholder’s epics */
    private void refreshTable(TableView<Issue> table) {
        List<Issue> epics = searchService.byReporterId(stakeholder.getId());
        table.setItems(FXCollections.observableArrayList(epics));
    }

    private void showAlert(Alert.AlertType type,
                           String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getRoot() {
        return root;
    }
}
