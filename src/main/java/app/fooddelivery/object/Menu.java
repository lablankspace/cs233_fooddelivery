package app.fooddelivery.object;

import java.util.HashSet;

public class Menu { // check discord
    private HashSet<Item> items;
    Menu() {
        items = new HashSet<>();
    }
    private void addItem(Item item, double price) {
        items.add(item);
    }
    private void removeItem(Item item) {
        items.remove(item);
    }
    // TODO: Change to return a array of items from the set
    // private String getMenu() {
    //     return menu.toString();
    // }
}