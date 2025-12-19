package entities.issues;

import entities.issues.Issue;

public class IssueLink {
    private final IssueLinkType type;
    private final Issue target;

    public IssueLink(IssueLinkType type, Issue target) {
        this.type = type;
        this.target = target;
    }

    public IssueLinkType getType() { return type; }
    public Issue getTarget() { return target; }
}

