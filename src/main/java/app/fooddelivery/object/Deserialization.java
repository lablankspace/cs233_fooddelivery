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
                state.users.add(context.deserialize(userElement,role.equals("Admin") ? Admin.class : (role.equals("Driver") ? Driver.class : Customer.class)));
            }
            JsonArray orders = object.getAsJsonArray("orders");
            for (JsonElement orderElement : orders){
                Order order = context.deserialize(orderElement,Order.class);
                for (User user : state.users){
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
    public static class OrderDeserializer implements JsonDeserializer<Order> {

        @Override
        public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            UUID id = UUID.fromString(object.get("id").getAsString());
            UUID customerId = UUID.fromString(object.get("customerId").getAsString());
            return new Order(id,customerId);
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
    public static class UserRatingDeserializer implements JsonDeserializer<UserRating> {

        @Override
        public UserRating deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new UserRating(UUID.fromString(object.get("creatorId").getAsString()),object.get("score").getAsDouble(),object.get("timestamp").getAsString());
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
