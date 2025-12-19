package entities.common;

import entities.issues.IssueLink;
import entities.issues.IssueLinkType;
import entities.issues.Issue;

import java.util.ArrayList;
import java.util.List;

public interface Linkable {
    List<IssueLink> getLinks();

    default void link(IssueLinkType type, Issue target) {
        if (type == null || target == null) return;
        getLinks().add(new IssueLink(type, target));
    }

    default List<Issue> getLinkedIssues(IssueLinkType type) {
        List<Issue> result = new ArrayList<>();
        if (type == null) return result;

        for (IssueLink link : getLinks()) {
            if (link.getType() == type) {
                result.add(link.getTarget());
            }
        }
        return result;
    }

    default boolean hasLinkTo(IssueLinkType type, Issue target) {
        if (type == null || target == null) return false;
        for (IssueLink link : getLinks()) {
            if (link.getType() == type && link.getTarget().getId().equals(target.getId())) {
                return true;
            }
        }
        return false;
    }
}

