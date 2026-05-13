package app.fooddelivery.object;

import java.util.HashSet;

public class Menu {
    private final HashSet<Item> items;
    Menu() {
        items = new HashSet<>();
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public HashSet<Item> getItems() {
        return items;
    }
}