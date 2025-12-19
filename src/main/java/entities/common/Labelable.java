package entities.common;

import java.util.List;

public interface Labelable {
    List<String> getLabels();

    default void addLabel(String label) {
        if (label == null) return;
        String l = label.trim();
        if (l.isEmpty()) return;

        // avoid duplicates (case-insensitive)
        for (String existing : getLabels()) {
            if (existing.equalsIgnoreCase(l)) return;
        }
        getLabels().add(l);
    }

    default void removeLabel(String label) {
        if (label == null) return;
        getLabels().removeIf(x -> x.equalsIgnoreCase(label.trim()));
    }

    default boolean hasLabel(String label) {
        if (label == null) return false;
        for (String x : getLabels()) {
            if (x.equalsIgnoreCase(label.trim())) return true;
        }
        return false;
    }
}

