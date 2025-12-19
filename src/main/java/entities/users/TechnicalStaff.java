package entities.users;

public abstract class TechnicalStaff extends User {
    protected int capacity; // how many hours / points they can handle

    public TechnicalStaff(String id, String name, String email, String password, int capacity) {
        super(id, name, email, password);
        this.capacity = capacity;
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}