package app.fooddelivery.object;

import java.util.UUID;

public class Admin extends User{
    protected Admin(String name, UUID id, String password) { super(name, id, password); }
    protected Admin(String name, UUID id, byte[] passwordHash) { super(name, id, passwordHash); }
    protected Admin(User u) { super(u.getName(), u.getId(), u.getPasswordHash()); }
    public Admin(String name, String password) {
        this(name, UUID.randomUUID(), password);
    }
    @Override
    public String getRole() {
        return "Admin";
    }
}