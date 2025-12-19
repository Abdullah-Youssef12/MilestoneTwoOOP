package gui.developer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Hook this into your real TaskService.
 * Your Task should expose: id, title, assignedBy (scrum master), etc.
 */
public class DeveloperTasksPanel extends JPanel {

    public static class DevTaskRow {
        public final String taskId;
        public final String title;
        public final String assignedBy;

        public DevTaskRow(String taskId, String title, String assignedBy) {
            this.taskId = taskId;
            this.title = title;
            this.assignedBy = assignedBy;
        }
    }

    public interface DeveloperTaskProvider {
        List<DevTaskRow> getTasksAssignedTo(String developerUsername);
    }

    private final DefaultTableModel model;
    private final JTable table;
    private final JButton refreshBtn;

    private final DeveloperTaskProvider taskProvider;
    private String developerUsername = "developer"; // set from session/login

    public DeveloperTasksPanel(DeveloperTaskProvider taskProvider) {
        this.taskProvider = taskProvider;

        setLayout(new BorderLayout(10, 10));
        JLabel title = new JLabel("My Assigned Tasks");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Task ID", "Title", "Assigned By"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshBtn = new JButton("Refresh");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(refreshBtn);
        add(south, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> reload());

        reload();
    }

    public void setDeveloperUsername(String developerUsername) {
        if (developerUsername != null && !developerUsername.isBlank()) {
            this.developerUsername = developerUsername;
            reload();
        }
    }

    public final void reload() {
        model.setRowCount(0);
        List<DevTaskRow> rows = taskProvider.getTasksAssignedTo(developerUsername);
        for (DevTaskRow r : rows) {
            model.addRow(new Object[]{r.taskId, r.title, r.assignedBy});
        }
    }
}
