package app.fooddelivery.object;

import java.util.HashSet;
import java.util.HashMap;

public class Menu { // check discord
    private HashMap<Item, Double> items;
    Menu() {
        items = new HashMap<>();
    }
    private void addItem(Item item, double price) {
        items.put(item, price);
    }
    private void removeItem(Item item) {
        items.remove(item);
    }
    private HashMap getMenu() {
        return items;
    }
    public String[] getItems() {
        String[] result = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            result = items.keySet().toArray(new String[0]);
        }
        return result;
    }
    // TODO: Change to return a array of items from the set
    // private String getMenu() {
    //     return menu.toString();
    // }
}