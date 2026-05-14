package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Button;
import app.fooddelivery.ui.Utils.Input;
import app.fooddelivery.ui.Utils.Line;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;
import app.fooddelivery.ui.Utils.Text;


public class AdminUI {
    public static class AdminHome implements Screen {
        private final Admin admin;

        public AdminHome(Admin admin) {
            this.admin = admin;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("WELCOME " + admin.getName())));

            List<Line> lines = new ArrayList<>(List.of(
                    new Button("View Menu", () -> new ViewMenu(admin)),
                    new Button("View Orders", () -> new ViewOrders(admin)),
                    new Button("Exit", () -> new LoginUI.Login())));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewMenu implements Screen {
        private final Admin admin;

        public ViewMenu(Admin admin) {
            this.admin = admin;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Menu")));

            List<Line> lines = new ArrayList<>();
            Menu menu = restaurant.getMenu();

            for (Item item : menu.getItems()) {
                Button line = new Button(
                        item.getName() + " --- " + item.getDescription() + " --- $" + item.getPrice(),
                        () -> new ViewItem(admin, item));
                lines.add(line);
            }
            lines.add(new Button("New Item", () -> new AddItem(admin)));
            lines.add(new Button("Exit", () -> new AdminHome(admin)));
            return Utils.loop(title, lines, term);
        }
    }

    public static class AddItem implements Screen {
        private final Admin admin;

        public AddItem(Admin admin) {
            this.admin = admin;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Create new Menu Item")));

            List<Line> lines = new ArrayList<>();
            Input name = new Input("Item Name: ", false);
            Input desc = new Input("Item Description: ", false);
            Input price = new Input("Price: ", false);
            lines.add(name);
            lines.add(desc);
            lines.add(price);
            lines.add(new Button("Add Item", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    Item item = new Item(name.getInput(), desc.getInput(), priceD);
                    restaurant.getMenu().addItem(item);
                    return new ViewMenu(admin);
                } catch (NumberFormatException e) {
                    return new AddItemError(admin);
                }
            }));
            lines.add(new Button("Exit", () -> {
                return new ViewMenu(admin);
            }));
            return Utils.loop(title, lines, term);
        }
    }

    public static class AddItemError implements Screen {
        private final Admin admin;

        public AddItemError(Admin admin) {
            this.admin = admin;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Create new Menu Item"),
                    new Text("Error: Price must be a double(ex: 5.20)")));

            List<Line> lines = new ArrayList<>();
            Input name = new Input("Item Name: ", false);
            Input desc = new Input("Item Description: ", false);
            Input price = new Input("Price: ", false);
            lines.add(name);
            lines.add(desc);
            lines.add(price);
            lines.add(new Button("Add Item", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    Item item = new Item(name.getInput(), desc.getInput(), priceD);
                    restaurant.getMenu().addItem(item);
                    return new ViewMenu(admin);
                } catch (NumberFormatException e) {
                    return new AddItemError(admin);
                }
            }));
            lines.add(new Button("Exit", () -> {
                return new ViewMenu(admin);
            }));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewItem implements Screen {
        private final Admin admin;
        private final Item item;

        public ViewItem(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text(item.getName())));

            List<Line> lines = new ArrayList<>(List.of(
                    new Button("Edit Name", () -> new EditName(admin, item)),
                    new Button("Edit Description", () -> new EditDescription(admin, item)),
                    new Button("Edit Price", () -> new EditPrice(admin, item)),
                    new Button("Delete Item", () -> new RemoveItem(admin, item)),
                    new Button("Exit", () -> new ViewMenu(admin))));
            return Utils.loop(title, lines, term);
        }
    }

    public static class EditName implements Screen {
        private final Admin admin;
        private final Item item;

        public EditName(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Editing " + item.getName() + "'s Name")));

            List<Line> lines = new ArrayList<>();
            Input name = new Input("New Name: ", false);
            lines.add(name);
            lines.add(new Button("Save Name", () -> {
                item.editName(name.getInput());
                return new ViewItem(admin, item);
            }));
            lines.add(new Button("Exit", () -> {
                return new ViewItem(admin, item);
            }));

            return Utils.loop(title, lines, term);
        }
    }

    public static class EditDescription implements Screen {
        private final Admin admin;
        private final Item item;

        public EditDescription(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Editing " + item.getName() + "'s Description")));

            List<Line> lines = new ArrayList<>();
            Input description = new Input("New Description: ", false);
            lines.add(description);
            lines.add(new Button("Save Description", () -> {
                item.editDescription(description.getInput());
                return new ViewItem(admin, item);
            }));
            lines.add(new Button("Exit", () -> {
                return new ViewItem(admin, item);
            }));

            return Utils.loop(title, lines, term);
        }
    }

    public static class EditPrice implements Screen {
        private final Admin admin;
        private final Item item;

        public EditPrice(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Editing " + item.getName() + "'s Price")));

            List<Line> lines = new ArrayList<>();
            Input price = new Input("New Price: ", false);
            lines.add(price);
            lines.add(new Button("Save Price", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    item.editPrice(priceD);
                    return new ViewItem(admin, item);
                } catch (NumberFormatException e) {
                    return new EditPriceError(admin, item);
                }
            }));
            lines.add(new Button("Exit", () -> new ViewItem(admin, item)));

            return Utils.loop(title, lines, term);
        }
    }

    public static class EditPriceError implements Screen {
        private final Admin admin;
        private final Item item;

        public EditPriceError(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Editing " + item.getName() + "'s Price"),
                    new Text("Error: Price must be a double(ex: 5.20)")));

            List<Line> lines = new ArrayList<>();
            Input price = new Input("New Price: ", false);
            lines.add(price);
            lines.add(new Button("Save Price", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    item.editPrice(priceD);
                    return new ViewItem(admin, item);
                } catch (NumberFormatException e) {
                    return new EditPriceError(admin, item);
                }
            }));
            lines.add(new Button("Exit", () -> new ViewItem(admin, item)));

            return Utils.loop(title, lines, term);
        }
    }

    public static class RemoveItem implements Screen {
        private final Admin admin;
        private final Item item;

        public RemoveItem(Admin admin, Item item) {
            this.admin = admin;
            this.item = item;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Are you sure you wish to remove " + item.getName())));

            List<Line> lines = new ArrayList<>(List.of(
                    new Button("Yes", () -> {
                        restaurant.getMenu().removeItem(item);
                        return new ViewMenu(admin);
                    }),
                    new Button("No", () -> {
                        return new ViewItem(admin, item);
                    })));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrders implements Screen {
        private final Admin admin;

        public ViewOrders(Admin admin) {
            this.admin = admin;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("All active and inactive orders")));

            List<Line> lines = new ArrayList<>();
            for (Driver driver : restaurant.getDrivers()) {
                for (Order order : driver.getOrders()) {
                    lines.add(new Button(order.getId().toString(), () -> new ViewOrder(admin, order)));
                }
            }
            lines.add(new Button("Exit", () -> new AdminHome(admin)));

            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewOrder implements Screen {
        private final Admin admin;
        private final Order order;

        public ViewOrder(Admin admin, Order order) {
            this.admin = admin;
            this.order = order;
        }
        @Override
        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Line> title = new ArrayList<>(List.of(
                    new Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Text("Order: " + order.getId().toString()),
                    new Text("Customer: " + order.getCustomer().getName()),
                    new Text("ID: " + order.getId().toString()),
                    new Text("Status: " + order.getStatus().toString())

            ));
            List<Line> lines = new ArrayList<>();
            lines.add(new Button("Exit", () -> new ViewOrders(admin)));
            return Utils.loop(title, lines, term, 6);
        }
    }
}