package gui.stakeholder;

import requests.Request;
import requests.RequestService;
import requests.RequestType;
import gui.NavigationManager;

import javax.swing.*;
import java.awt.*;

/**
 * Hook createTask() to your real Task creation logic.
 */
public class CreateTaskPanel extends JPanel {
    private final NavigationManager nav;

    private final JTextField titleField = new JTextField(20);
    private final JTextArea descArea = new JTextArea(5, 20);

    // Replace with your logged-in stakeholder username from your session
    private String stakeholderUsername = "stakeholder";

    public interface TaskCreationHandler {
        // return created task ID (e.g., TASK-1234) or null if fail
        String createTask(String title, String description, String stakeholderUsername);
    }

    private final TaskCreationHandler creationHandler;

    public CreateTaskPanel(NavigationManager nav, TaskCreationHandler creationHandler) {
        this.nav = nav;
        this.creationHandler = creationHandler;

        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel("Create Task");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(0,1,6,6));
        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Description:"));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        form.add(new JScrollPane(descArea));
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backBtn = new JButton("Back");
        JButton createBtn = new JButton("Create");
        buttons.add(backBtn);
        buttons.add(createBtn);
        add(buttons, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> nav.goBack());
        createBtn.addActionListener(e -> onCreate());
    }

    public void setStakeholderUsername(String username) {
        if (username != null && !username.isBlank()) this.stakeholderUsername = username;
    }

    private void onCreate() {
        String t = titleField.getText().trim();
        String d = descArea.getText().trim();

        if (t.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.");
            return;
        }

        String createdId = creationHandler.createTask(t, d, stakeholderUsername);
        if (createdId == null) {
            JOptionPane.showMessageDialog(this, "Failed to create task.");
            return;
        }

        // ✅ push request so Scrum Master can see it
        Request req = new Request(RequestType.TASK, createdId, t, stakeholderUsername);
        RequestService.getInstance().addRequest(req);

        JOptionPane.showMessageDialog(this, "Created: " + createdId + "\nRequest sent to Scrum Master.");

        // optionally clear fields
        titleField.setText("");
        descArea.setText("");
    }
}
