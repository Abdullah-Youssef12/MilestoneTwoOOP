package gui.scrummaster;

import requests.Request;
import requests.RequestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * You must connect:
 * - getDevelopers(): returns list of developer usernames (or names)
 * - assignTask(issueId, devUsername, scrumUsername): assigns a TASK in your system
 * - optional: assignStory/assignEpic if your system supports it; otherwise only allow TASK assignment
 */
public class RequestPanel extends JPanel {

    public interface DeveloperProvider {
        List<String> getDevelopers();
    }

    public interface AssignmentHandler {
        boolean assign(Request req, String developerUsername, String scrumMasterUsername);
    }

    private final DefaultTableModel model;
    private final JTable table;
    private final JComboBox<String> devCombo;
    private final JButton assignBtn;
    private final JButton refreshBtn;

    private final DeveloperProvider developerProvider;
    private final AssignmentHandler assignmentHandler;

    private String scrumMasterUsername = "scrumMaster"; // set from your session/login

    public RequestPanel(DeveloperProvider developerProvider, AssignmentHandler assignmentHandler) {
        this.developerProvider = developerProvider;
        this.assignmentHandler = assignmentHandler;

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Requests");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"Request ID", "Type", "Issue ID", "Issue Title", "Stakeholder", "Status"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        refreshBtn = new JButton("Refresh");
        south.add(refreshBtn);

        south.add(new JLabel("Assign to:"));
        devCombo = new JComboBox<>();
        south.add(devCombo);

        assignBtn = new JButton("Assign Selected");
        south.add(assignBtn);

        add(south, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> reload());
        assignBtn.addActionListener(e -> assignSelected());

        reload();
    }

    public void setScrumMasterUsername(String scrumMasterUsername) {
        if (scrumMasterUsername != null && !scrumMasterUsername.isBlank()) {
            this.scrumMasterUsername = scrumMasterUsername;
        }
    }

    public final void reload() {
        // refresh developers list
        devCombo.removeAllItems();
        for (String dev : developerProvider.getDevelopers()) devCombo.addItem(dev);

        // refresh requests table
        model.setRowCount(0);
        List<Request> open = RequestService.getInstance().getOpenRequests();
        for (Request r : open) {
            model.addRow(new Object[]{
                    r.getRequestId(),
                    r.getType(),
                    r.getIssueId(),
                    r.getIssueTitle(),
                    r.getStakeholderUsername(),
                    r.isResolved() ? "RESOLVED" : "OPEN"
            });
        }
    }

    private void assignSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a request first.");
            return;
        }
        if (devCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No developer available.");
            return;
        }

        String requestId = String.valueOf(model.getValueAt(row, 0));
        String devUsername = String.valueOf(devCombo.getSelectedItem());

        Request req = RequestService.getInstance().findById(requestId).orElse(null);
        if (req == null) {
            JOptionPane.showMessageDialog(this, "Request not found. Refresh and try again.");
            return;
        }

        boolean ok = assignmentHandler.assign(req, devUsername, scrumMasterUsername);
        if (ok) {
            req.markAssigned(devUsername, scrumMasterUsername);
            JOptionPane.showMessageDialog(this, "Assigned successfully.");
            reload();
        } else {
            JOptionPane.showMessageDialog(this, "Assignment failed. Check your Task/Issue assignment logic.");
        }
    }
}
