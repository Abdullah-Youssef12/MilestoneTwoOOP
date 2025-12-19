package entities.project;

public class Component {
    private final String id;
    private String name;

    public Component(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

