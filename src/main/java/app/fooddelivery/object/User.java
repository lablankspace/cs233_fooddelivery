package app.fooddelivery.object;

import java.util.UUID;

public abstract class User {
    protected String name;
    protected UUID id;
    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }
    protected User(String name, UUID id) {
        this.name = name;
        this.id = id;
    }
    public String getName() { return name; }
    public UUID getId() { return id; }
    public abstract String getRole();
}