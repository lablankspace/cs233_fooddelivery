package app.fooddelivery.object;

public abstract class User {
    protected String name;
    protected int id;
    User(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() { return name; }
    public int getId() { return id; }
    abstract String getRole();
}