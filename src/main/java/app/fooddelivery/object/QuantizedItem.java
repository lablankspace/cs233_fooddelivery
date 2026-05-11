package app.fooddelivery.object;

import java.util.UUID;

public class QuantizedItem {
    private Item item;
    private final UUID itemId;
    private final int quantity;

    public QuantizedItem(Item item, int quantity) {
        this.item = item;
        this.itemId = item.getId();
        this.quantity = quantity;
    }

    QuantizedItem(UUID itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public UUID getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return item.getPrice() * quantity;
    }

    protected void setItemRef(Item item) {
        if (item.getId().equals(itemId)) {
            this.item = item;
        }
    }
}
