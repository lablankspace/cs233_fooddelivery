package app.fooddelivery.object;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class Deserialization {
    public static class StateDeserializer implements JsonDeserializer<State> {

        @Override
        public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            State state = new State();
            JsonObject object = json.getAsJsonObject();
            JsonArray users = object.getAsJsonArray("users");
            for (JsonElement userElement : users){
                String role = userElement.getAsJsonObject().get("role").getAsString();
                state.users.add(context.deserialize(userElement,role.equals("Customer") ? Customer.class : (role.equals("Driver") ? Driver.class : Admin.class)));
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
    public static class CustomerDeserializer implements JsonDeserializer<Customer> {

        @Override
        public Customer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Customer(object.get("name").getAsString(),UUID.fromString(object.get("id").getAsString()));
        }
    }
    public static class DriverDeserializer implements JsonDeserializer<Driver> {

        @Override
        public Driver deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new Driver(object.get("name").getAsString(),UUID.fromString(object.get("id").getAsString()));
        }
    }
    public static class UserRatingDeserializer implements JsonDeserializer<UserRating> {

        @Override
        public UserRating deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            return new UserRating(UUID.fromString(object.get("creatorId").getAsString()),object.get("score").getAsDouble(),object.get("timestamp").getAsString());
        }
    }
}
