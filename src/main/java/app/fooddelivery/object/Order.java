package app.fooddelivery.object;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class Order{

    private Customer customer;
    private UUID customerId; // Used for deserialization post-processing
    private ArrayList<QuantizedItem> items;
    private Status status;
    private UUID id;
    public Order(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
        items = new ArrayList<>();
        this.id = UUID.randomUUID();
        this.status = Status.PLACED;
    }
    protected Order(UUID id, UUID customerId) {
        this.customerId = customerId;
        items = new ArrayList<>();
        this.id = id;
        this.status = Status.PLACED;
    }
    public void addItem(Item item, int quantity) {
        items.add(new QuantizedItem(item,quantity));
    }
    public void removeItem(Item item){
        for (QuantizedItem quantizedItem : items){
            if (quantizedItem.getItemId().equals(item)){
                items.remove(quantizedItem);
                return;
            }
        }
    }
    public Customer getCustomer() {
        return customer;
    }
    public ArrayList<QuantizedItem> getItems() {
        return items;
    }
    public Status getStatus() {
        return status;
    }
    public void updateStatus(Status status) {
        this.status = status;
    }
    public UUID getId() {
        return this.id;
    }
    public static enum Status {
        PLACED,
        ACCEPTED,
        INPROGRESS,
        DELIVERED
    }
    protected void setCustomer(Customer customer){
        this.customer = customer;
        this.customerId = customer.getId();
    }
    protected UUID getCustomerId(){
        return customerId;
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}