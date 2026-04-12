package app.fooddelivery.object;

public class Item {
    private final String name;
    private final String description;
    private final double price;
    Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}