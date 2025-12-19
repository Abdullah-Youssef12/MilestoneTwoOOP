package gui;

import entities.issues.Issue;
import entities.users.TechnicalStaff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.IssueSearchService;

/**
 * Lists issues assigned to a developer or QA engineer.
 */
public class AssignedIssueScreen {

    private final BorderPane root;
    private final Stage stage;
    private final TechnicalStaff currentUser;
    private final IssueSearchService searchService;

    public AssignedIssueScreen(Stage stage, TechnicalStaff user) {
        this.stage = stage;
        this.currentUser = user;
        this.searchService = new IssueSearchService();
        this.root = buildUI();
    }

    private BorderPane buildUI() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10));
        Label header = new Label("Assigned Issues");

        TableView<Issue> table = new TableView<>();
        TableColumn<Issue, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        TableColumn<Issue, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitle()));
        TableColumn<Issue, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        table.getColumns().addAll(idCol, titleCol, statusCol);

        ObservableList<Issue> issues =
                FXCollections.observableArrayList(searchService.byAssigneeId(currentUser.getId()));
        table.setItems(issues);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            DashboardScreen dashboard = new DashboardScreen(stage, currentUser);
            stage.setScene(new Scene(dashboard.getRoot(), 600, 400));
        });

        pane.setTop(header);
        pane.setCenter(table);
        pane.setBottom(backBtn);
        return pane;
    }

    public BorderPane getRoot() {
        return root;
    }
}
