package entities.common;

import java.util.List;

public interface Commentable {
    List<String> getComments();

    default void addComment(String comment) {
        if (comment == null) return;
        String c = comment.trim();
        if (c.isEmpty()) return;
        getComments().add(c);
    }

    default int commentCount() {
        return getComments().size();
    }
}
