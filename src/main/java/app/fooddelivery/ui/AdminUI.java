package app.fooddelivery.ui;

import java.util.ArrayList;
import java.util.List;

import app.fooddelivery.object.Admin;
import app.fooddelivery.object.Driver;
import app.fooddelivery.object.Item;
import app.fooddelivery.object.Menu;
import app.fooddelivery.object.Order;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Utils.Screen;
import app.fooddelivery.ui.Utils.Term;

public class AdminUI {
    public static class AdminHome implements Screen {
        private final Admin admin;

        public AdminHome(Admin admin) {
            this.admin = admin;
        }

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("WELCOME " + admin.getName())));

            List<Utils.Line> lines = new ArrayList<>(List.of(
                    new Utils.Button("View Menu", () -> new ViewMenu(admin)),
                    new Utils.Button("View Orders", () -> new ViewOrders(admin)),
                    new Utils.Button("Exit", () -> new LoginUI.Login())));
            return Utils.loop(title, lines, term);
        }
    }

    public static class ViewMenu implements Screen {
        private final Admin admin;

        public ViewMenu(Admin admin) {
            this.admin = admin;
        }

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Menu")));

            List<Utils.Line> lines = new ArrayList<>();
            Menu menu = restaurant.getMenu();

            for (Item item : menu.getItems()) {
                Utils.Button line = new Utils.Button(
                        item.getName() + " --- " + item.getDescription() + " --- $" + item.getPrice(),
                        () -> new ViewItem(admin, item));
                lines.add(line);
            }
            lines.add(new Utils.Button("New Item", () -> new AddItem(admin)));
            lines.add(new Utils.Button("Exit", () -> new AdminHome(admin)));
            return Utils.loop(title, lines, term);
        }
    }

    public static class AddItem implements Screen {
        private final Admin admin;

        public AddItem(Admin admin) {
            this.admin = admin;
        }

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Create new Menu Item")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input name = new Utils.Input("Item Name: ", false);
            Utils.Input desc = new Utils.Input("Item Description: ", false);
            Utils.Input price = new Utils.Input("Price: ", false);
            lines.add(name);
            lines.add(desc);
            lines.add(price);
            lines.add(new Utils.Button("Add Item", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    Item item = new Item(name.getInput(), desc.getInput(), priceD);
                    restaurant.getMenu().addItem(item);
                    return new ViewMenu(admin);
                } catch (NumberFormatException e) {
                    return new AddItemError(admin);
                }
            }));
            lines.add(new Utils.Button("Exit", () -> {
                return new ViewOrders(admin);
            }));
            return Utils.loop(title, lines, term);
        }
    }

    public static class AddItemError implements Screen {
        private final Admin admin;

        public AddItemError(Admin admin) {
            this.admin = admin;
        }

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Create new Menu Item"),
                    new Utils.Text("Error: Price must be a double(ex: 5.20)")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input name = new Utils.Input("Item Name: ", false);
            Utils.Input desc = new Utils.Input("Item Description: ", false);
            Utils.Input price = new Utils.Input("Price: ", false);
            lines.add(name);
            lines.add(desc);
            lines.add(price);
            lines.add(new Utils.Button("Add Item", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    Item item = new Item(name.getInput(), desc.getInput(), priceD);
                    restaurant.getMenu().addItem(item);
                    return new ViewMenu(admin);
                } catch (NumberFormatException e) {
                    return new AddItemError(admin);
                }
            }));
            lines.add(new Utils.Button("Exit", () -> {
                return new ViewOrders(admin);
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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text(item.getName())));

            List<Utils.Line> lines = new ArrayList<>(List.of(
                    new Utils.Button("Edit Name", () -> new EditName(admin, item)),
                    new Utils.Button("Edit Description", () -> new EditDescription(admin, item)),
                    new Utils.Button("Edit Price", () -> new EditPrice(admin, item)),
                    new Utils.Button("Delete Item", () -> new RemoveItem(admin, item)),
                    new Utils.Button("Exit", () -> new ViewMenu(admin))));
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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Editing " + item.getName() + "'s Name")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input name = new Utils.Input("New Name: ", false);
            lines.add(name);
            lines.add(new Utils.Button("Save Name", () -> {
                item.editName(name.getInput());
                return new ViewItem(admin, item);
            }));
            lines.add(new Utils.Button("Exit", () -> {
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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Editing " + item.getName() + "'s Description")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input description = new Utils.Input("New Description: ", false);
            lines.add(description);
            lines.add(new Utils.Button("Save Description", () -> {
                item.editDescription(description.getInput());
                return new ViewItem(admin, item);
            }));
            lines.add(new Utils.Button("Exit", () -> {
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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Editing " + item.getName() + "'s Price")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input price = new Utils.Input("New Price: ", false);
            lines.add(price);
            lines.add(new Utils.Button("Save Price", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    item.editPrice(priceD);
                    return new ViewItem(admin, item);
                } catch (NumberFormatException e) {
                    return new EditPriceError(admin, item);
                }
            }));
            lines.add(new Utils.Button("Exit", () -> new ViewItem(admin, item)));

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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Editing " + item.getName() + "'s Price"),
                    new Utils.Text("Price must be a double(5.20)")));

            List<Utils.Line> lines = new ArrayList<>();
            Utils.Input price = new Utils.Input("New Price: ", false);
            lines.add(price);
            lines.add(new Utils.Button("Save Price", () -> {
                try {
                    double priceD = Double.parseDouble(price.getInput());
                    item.editPrice(priceD);
                    return new ViewItem(admin, item);
                } catch (NumberFormatException e) {
                    return new EditPriceError(admin, item);
                }
            }));
            lines.add(new Utils.Button("Exit", () -> new ViewItem(admin, item)));

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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Are you sure you wish to remove " + item.getName())));

            List<Utils.Line> lines = new ArrayList<>(List.of(
                    new Utils.Button("Yes", () -> {
                        restaurant.getMenu().removeItem(item);
                        return new ViewItem(admin, item);
                    }),
                    new Utils.Button("No", () -> {
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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("All active and inactive orders")));

            List<Utils.Line> lines = new ArrayList<>();
            lines.add(new Utils.Text("Order Queue: "));
            for (Order order : restaurant.getOrderQueue()) {
                lines.add(new Utils.Button(order.getId().toString(), () -> new ViewOrder(admin, order)));
            }
            lines.add(new Utils.Text("Active Orders: "));
            for (Driver driver : restaurant.getDrivers()) {
                for (Order order : driver.getOrders()) {
                    lines.add(new Utils.Button(order.getId().toString(), () -> new ViewOrder(admin, order)));
                }
            }
            lines.add(new Utils.Button("Exit", () -> new AdminHome(admin)));

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

        public Screen run(Term term, Restaurant restaurant) {
            term.clear();

            List<Utils.Line> title = new ArrayList<>(List.of(
                    new Utils.Text("ISLAND GIRLS DELIVERY SERVICE"),
                    new Utils.Text("Order: " + order.getId().toString()),
                    new Utils.Text("Customer: " + order.getCustomer().getName()),
                    new Utils.Text("ID: " + order.getId().toString()),
                    new Utils.Text("Status: " + order.getStatus().toString())

            ));
            List<Utils.Line> lines = new ArrayList<>();
            lines.add(new Utils.Button("Exit", () -> new ViewOrders(admin)));
            return Utils.loop(title, lines, term, 6);
        }
    }
}