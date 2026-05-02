package app.fooddelivery.object;

import java.util.HashMap;

public class Menu {
    private HashMap<Item, Double> items;
    Menu() {
        items = new HashMap<>();
    }
    public void addItem(Item item) {
        items.put(item, item.getPrice());
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public HashMap<Item, Double> getHash() {
        return new HashMap<>(items);
    }
    public String[] getItems() {
        String[] result = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            result = items.keySet().toArray(new String[0]);
        }
        return result;
    }

    public Double getPrice(Item item) {
        return items.get(item);
    }
    // TODO: Change to return a array of items from the set
    // private String getMenu() {
    //     return menu.toString();
    // }
}