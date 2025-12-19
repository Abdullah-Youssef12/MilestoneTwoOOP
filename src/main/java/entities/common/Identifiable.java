package  entities.common;

public interface Identifiable {
    String getId();

    default boolean hasId(String id) {
        return id != null && id.equals(getId());
    }
}
