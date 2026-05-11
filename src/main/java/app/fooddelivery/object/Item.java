package app.fooddelivery.object;

import java.util.Objects;
import java.util.UUID;

public class Item {
    private String name;
    private String description;
    private double price;
    private UUID id;
    protected Item(String name, String description, double price, UUID id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }
    public Item(String name, String description, double price) {
        this(name,description,price,UUID.randomUUID());
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public UUID getId() {
        return id;
    }
    public void editName(String name) {
        this.name = name;
    }
    public void editDescription(String description) {
        this.description = description;
    }
    public void editPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}