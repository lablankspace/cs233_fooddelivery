package object;

public class Item {
    private String name;
    private String description;
    private List<String> ingredients;
    Item(String name, String description) {
        this.name = name;
        this.description = description;
        List<String> ingredients = new List<>();
    }
    private void addIngredient(String ingredient) {

    }
    private String getName() {
        return name;
    }
    private String getDescription() {
        return description;
    }
    private String getIngredients() {
        return ingredients.toString();
    }
}