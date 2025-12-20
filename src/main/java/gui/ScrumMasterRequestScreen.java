package gui;

import controllers.ScrumMasterController;
import entities.issues.Bug;
import entities.issues.Epic;
import entities.issues.Issue;
import entities.issues.Story;
import entities.users.Developer;
import entities.users.QAEngineer;
import entities.users.ScrumMaster;
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
 * Screen for scrum masters to see unassigned epics/bugs, create stories, and assign bugs to QA.
 */
public class ScrumMasterRequestScreen {
    private final BorderPane root;
    private final Stage stage;
    private final ScrumMaster scrumMaster;
    private final ScrumMasterController controller;

    public ScrumMasterRequestScreen(Stage stage, ScrumMaster sm) {
        this.stage = stage;
        this.scrumMaster = sm;
        this.controller = new ScrumMasterController();
        this.root = buildUI();
    }

    public ScrumMasterRequestScreen(BorderPane root, Stage stage, ScrumMaster scrumMaster, ScrumMasterController controller) {
        this.root = root;
        this.stage = stage;
        this.scrumMaster = scrumMaster;
        this.controller = controller;
    }

    private BorderPane buildUI() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));

        Label header = new Label("Incoming Requests");
        VBox contentBox = new VBox(10);

        // List of epics to convert to stories
        Label epicsHeader = new Label("Unassigned Epics");
        TableView<Epic> epicsTable = new TableView<>();
        TableColumn<Epic, String> epicIdCol = new TableColumn<>("ID");
        epicIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Epic, String> epicTitleCol = new TableColumn<>("Title");
        epicTitleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        epicsTable.getColumns().addAll(epicIdCol, epicTitleCol);

        TextField storyTitleField = new TextField();
        storyTitleField.setPromptText("Story Title");
        TextField storySummaryField = new TextField();
        storySummaryField.setPromptText("Story Summary");
        TextField devIdField = new TextField();
        devIdField.setPromptText("Developer ID");
        Button createStoryBtn = new Button("Create Story");
        createStoryBtn.setOnAction(e -> {
            Epic selectedEpic = epicsTable.getSelectionModel().getSelectedItem();
            if (selectedEpic != null) {
                Story story = controller.createStoryFromEpic(
                        selectedEpic,
                        new Developer(devIdField.getText(), "", "", "", 0),
                        scrumMaster,
                        storyTitleField.getText(),
                        storySummaryField.getText()
                );
                if (story != null) {
                    showAlert(Alert.AlertType.INFORMATION, "Story Created",
                            "Story ID: " + story.getId());
                    refreshTables(epicsTable, null);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to create story.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Epic Selected", "Select an epic first.");
            }
        });

        HBox epicBtns = new HBox(5, storyTitleField, storySummaryField, devIdField, createStoryBtn);

        // List of unassigned bugs to assign to QA
        Label bugsHeader = new Label("Unassigned Bugs");
        TableView<Bug> bugsTable = new TableView<>();
        TableColumn<Bug, String> bugIdCol = new TableColumn<>("ID");
        bugIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Bug, String> bugTitleCol = new TableColumn<>("Title");
        bugTitleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        bugsTable.getColumns().addAll(bugIdCol, bugTitleCol);

        TextField qaIdField = new TextField();
        qaIdField.setPromptText("QA ID");
        Button assignBugBtn = new Button("Assign to QA");
        assignBugBtn.setOnAction(e -> {
            Bug selectedBug = bugsTable.getSelectionModel().getSelectedItem();
            if (selectedBug != null) {
                boolean ok = controller.assignBugToQa(
                        selectedBug,
                        new QAEngineer(qaIdField.getText(), "", "", "", 0),
                        scrumMaster
                );
                if (ok) {
                    showAlert(Alert.AlertType.INFORMATION, "Bug Assigned", "Bug assigned to QA.");
                    refreshTables(null, bugsTable);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to assign bug.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "No Bug Selected", "Select a bug first.");
            }
        });

        HBox bugBtns = new HBox(5, qaIdField, assignBugBtn);

        contentBox.getChildren().addAll(epicsHeader, epicsTable, epicBtns,
                bugsHeader, bugsTable, bugBtns);

        // Back button
        Button back = new Button("Back");
        back.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, scrumMaster);
            stage.setScene(new Scene(dashboard.getRoot(), 400, 300));
        });

        pane.setTop(header);
        pane.setCenter(contentBox);
        pane.setBottom(back);

        refreshTables(epicsTable, bugsTable);
        return pane;
    }

    private void refreshTables(TableView<Epic> epicsTable, TableView<Bug> bugsTable) {
        if (epicsTable != null) {
            List<Epic> epics = controller.getUnassignedEpics();
            epicsTable.setItems(FXCollections.observableArrayList(epics));
        }
        if (bugsTable != null) {
            List<Bug> bugs = controller.getUnassignedBugs();
            bugsTable.setItems(FXCollections.observableArrayList(bugs));
        }
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
