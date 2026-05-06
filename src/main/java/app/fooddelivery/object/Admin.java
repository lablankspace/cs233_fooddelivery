package app.fooddelivery.object;

import java.util.UUID;

public class Admin extends User{
    public Admin(String name, UUID id, String password) { super(name, id, password); }
    protected Admin(String name, UUID id, byte[] passwordHash) { super(name, id, passwordHash); }
    public Admin(User u) { super(u.getName(), u.getId(), u.getPasswordHash()); }
    public Admin(String name, String password) {
        this(name, UUID.randomUUID(), password);
    }
    private void addItem(Item item) {

    } 
    private void removeItem(Item item) {
    
    }
    private void updateItem(Item item, Double price) {

    }
    private void viewAllOrders() {
     
    }
    private void viewDriverRate(Driver driver) {

    }
    @Override
    public String getRole() {
        return "Admin";
    }
}