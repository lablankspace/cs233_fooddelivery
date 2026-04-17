package app.fooddelivery.object;

public class QuantizedItem {
    public final Item item;
    public final int quantity;
    QuantizedItem(Item item,int quantity){
        this.item = item;
        this.quantity = quantity;
    }
}
