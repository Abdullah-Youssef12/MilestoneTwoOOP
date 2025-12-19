package entities.common;

import entities.issues.Priority;

public interface Prioritizable {
    Priority getPriority();
    void setPriority(Priority p);

    default boolean isHighPriority() {
        return getPriority() == Priority.HIGH || getPriority() == Priority.CRITICAL;
    }
}

