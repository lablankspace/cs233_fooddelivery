package app.fooddelivery.object;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Deserialization {
    public static class StateDeserializer implements JsonDeserializer<State> {

        @Override
        public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            State state = new State();
            JsonObject object = json.getAsJsonObject();
            JsonArray users = object.getAsJsonArray("users");
            for (JsonElement userElement : users){
                String role = userElement.getAsJsonObject().get("role").getAsString();
                Class<? extends User> type;
                switch (role) {
                    case "Admin":
                        type = Admin.class;
                        break;
                    case "Driver":
                        type = Driver.class;
                        break;
                    default:
                        type = Customer.class;
                        break;
                }

                User user = context.deserialize(userElement, type);

                state.users.put(user.getId(), user);
            }
            JsonArray orders = object.getAsJsonArray("orders");
            for (JsonElement orderElement : orders){
                Order order = context.deserialize(orderElement,Order.class);
                for (User user : state.users.values()){
                    if (!(user instanceof Customer))continue;
                    if (order.getCustomerId().equals(user.getId())){
                        order.setCustomer((Customer) user);
                        break;
                    }
                }
                state.orders.add(order);
            }
            return state;
        }
    }
    public static class MenuDeserializer implements JsonDeserializer<Menu> {
        @Override
        public Menu deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Menu menu = new Menu();
            JsonArray items = object.getAsJsonArray("items");
            for (JsonElement element : items) {
                Item item = context.deserialize(element, Item.class);
                menu.addItem(item);
            }
            return menu;
        }
    }
    public static class OrderDeserializer implements JsonDeserializer<Order> {

        @Override
        public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            UUID id = UUID.fromString(object.get("id").getAsString());
            UUID customerId = UUID.fromString(object.get("customerId").getAsString());
            Order o = new Order(id, customerId);
            JsonArray items = object.getAsJsonArray("items");
            for (JsonElement element : items) {
                QuantizedItem item = context.deserialize(element, QuantizedItem.class);
                o.addQuantizedItem(item);
            }
            return o;
        }
    }
    public static class QuantizedItemDeserializer implements JsonDeserializer<QuantizedItem> {

        @Override
        public QuantizedItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new QuantizedItem(UUID.fromString(object.get("itemId").getAsString()),object.get("quantity").getAsInt());
        }
    }
    public static class ItemDeserializer implements JsonDeserializer<Item> {
        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            String name = object.get("name").getAsString();
            String description = object.get("description").getAsString();
            Double price = object.get("price").getAsDouble();
            UUID id = UUID.fromString(object.get("id").getAsString());
            return new Item(name, description, price, id);
        }
    }
    public static class AdminDeserializer implements JsonDeserializer<Admin> {

        @Override
        public Admin deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Admin(object.get("name").getAsString(),UUID.fromString(object.get("id").getAsString()),hexToBytes(object.get("phash").getAsString()));
        }
    }
    public static class CustomerDeserializer implements JsonDeserializer<Customer> {

        @Override
        public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Customer(object.get("name").getAsString(),UUID.fromString(object.get("id").getAsString()),hexToBytes(object.get("phash").getAsString()));
        }
    }
    public static class DriverDeserializer implements JsonDeserializer<Driver> {

        @Override
        public Driver deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Driver(object.get("name").getAsString(),UUID.fromString(object.get("id").getAsString()),hexToBytes(object.get("phash").getAsString()));
        }
    }
    public static class UserRatingDeserializer implements JsonDeserializer<Rating> {

        @Override
        public Rating deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Rating(UUID.fromString(object.get("creatorId").getAsString()),object.get("score").getAsInt(),object.get("timestamp").getAsString());
        }
    }
    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have even length");
        }

        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int high = Character.digit(hex.charAt(i), 16);
            int low  = Character.digit(hex.charAt(i + 1), 16);

            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("Invalid hex character");
            }

            result[i / 2] = (byte) ((high << 4) + low);
        }
        return result;
    }
}
