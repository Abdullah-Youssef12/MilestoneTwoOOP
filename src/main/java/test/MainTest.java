package test;

import database.Database;
import entities.issues.*;
import entities.users.*;
import managers.UserManager;
import DAO.TaskDAO;
import services.AccessService;
import services.IssueCreationService;
import services.TaskService;

public class MainTest {

    public static void main(String[] args) {

        System.out.println("===== TC04: Scrum Master assigns Task to Developer =====");

        // Reset in-memory DB
        Database.users.clear();
        Database.issues.clear();

        // Create users
        ScrumMaster scrumMaster = new ScrumMaster("SM1", "Ahmed", "ahmed@company.com", "1234");
        Developer developer = new Developer("DEV1", "Sara", "sara@company.com", "1234", 5);
        Stakeholder stakeholder = new Stakeholder("ST1", "Omar", "omar@company.com", "1234");

        // Register users (keeps your normal flow)
        AccessService accessService = new AccessService();
        accessService.register(scrumMaster);
        accessService.register(developer);
        accessService.register(stakeholder);

        // IMPORTANT: Some projects store users inside UserManager (not only Database.users)
        // If UserManager.findById() uses its own list, make sure these users are visible there too.
        // If you don't have addUser(...) in UserManager, just comment the next 3 lines.
        try {
            UserManager.addUser(scrumMaster);
            UserManager.addUser(developer);
            UserManager.addUser(stakeholder);
        } catch (Exception ignored) {
            // If addUser doesn't exist, ignore. Your UserManager may already read Database.users.
        }

        // Create a TASK reported by stakeholder
        IssueCreationService issueService = new IssueCreationService();
        Issue created = issueService.createIssue(
                IssueType.TASK,
                null,
                "Implement Login",
                "Login Feature",
                "Implement login using JavaFX",
                null,
                Priority.HIGH,
                null,
                stakeholder,
                null,
                null,
                null,
                3,
                null
        );

        System.out.println("Task created with ID: " + created.getId());

        // ✅ FIX: Ensure Task is saved inside TaskDAO so TaskService can find it by TaskDAO.findById(...)
        if (created instanceof Task) {
            TaskDAO.save((Task) created);
        } else {
            System.out.println("❌ TC04 FAILED: Created issue is not a Task!");
            return;
        }

        // Debug checks (to know EXACTLY why it fails if it still fails)
        System.out.println("DEBUG TaskDAO.findById: " + (TaskDAO.findById(created.getId()) != null));
        System.out.println("DEBUG Dev exists: " + (UserManager.findById(developer.getId()) != null));
        System.out.println("DEBUG Assigner exists: " + (UserManager.findById(scrumMaster.getId()) != null));

        // Assign task
        TaskService taskService = new TaskService();
        boolean assigned = taskService.assignTask(created.getId(), developer.getId(), scrumMaster.getId());

        System.out.println("Assignment result: " + assigned);

        // Validate
        Issue updated = Database.issues.stream()
                .filter(i -> i.getId().equals(created.getId()))
                .findFirst()
                .orElse(null);

        if (updated == null) {
            System.out.println("❌ TC04 FAILED: Task not found in Database.issues after assignment");
            return;
        }

        System.out.println("Assigned To: " + (updated.getAssignee() == null ? "null" : updated.getAssignee().getName()));
        System.out.println("Assigned By: " + (updated.getAssignedBy() == null ? "null" : updated.getAssignedBy().getName()));

        if (assigned
                && updated.getAssignee() != null
                && updated.getAssignee().getId().equals(developer.getId())
                && updated.getAssignedBy() != null
                && updated.getAssignedBy().getId().equals(scrumMaster.getId())) {
            System.out.println("✅ TC04 PASSED");
        } else {
            System.out.println("❌ TC04 FAILED");
        }
    }
}
