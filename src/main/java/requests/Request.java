package requests;

import java.time.LocalDateTime;
import java.util.UUID;

public class Request {
    private final String requestId;
    private final RequestType type;
    private final String issueId;     // your created issue ID (EPIC-..., STORY-..., TASK-...)
    private final String issueTitle;
    private final String stakeholderUsername;
    private final LocalDateTime createdAt;

    // assignment info
    private String assignedDeveloperUsername; // nullable
    private String assignedByScrumMaster;     // nullable
    private boolean resolved;                 // set true when assigned

    public Request(RequestType type, String issueId, String issueTitle, String stakeholderUsername) {
        this.requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.type = type;
        this.issueId = issueId;
        this.issueTitle = issueTitle;
        this.stakeholderUsername = stakeholderUsername;
        this.createdAt = LocalDateTime.now();
        this.resolved = false;
    }

    public String getRequestId() { return requestId; }
    public RequestType getType() { return type; }
    public String getIssueId() { return issueId; }
    public String getIssueTitle() { return issueTitle; }
    public String getStakeholderUsername() { return stakeholderUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isResolved() { return resolved; }

    public String getAssignedDeveloperUsername() { return assignedDeveloperUsername; }
    public String getAssignedByScrumMaster() { return assignedByScrumMaster; }

    public void markAssigned(String developerUsername, String scrumMasterUsername) {
        this.assignedDeveloperUsername = developerUsername;
        this.assignedByScrumMaster = scrumMasterUsername;
        this.resolved = true;
    }
}
