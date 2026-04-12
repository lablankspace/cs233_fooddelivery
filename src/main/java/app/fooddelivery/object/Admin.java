package app.fooddelivery.object;

public class Admin extends User{
    Admin(String name, int id) { super(name, id); }
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