package app.fooddelivery.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.object.Customer;
import app.fooddelivery.service.Restaurant;
import app.fooddelivery.ui.Select2D;


public class CustomerUI {
    public static void home(Terminal terminal, Customer customer) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "CUSTOMER", 1);
                  
            int selection = 0;
            String[] options = {"Place Order", "View Order History", "Rate Order", "Exit"};
            Utils.selection(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                selection = Utils.changeSelection(selection, key, options.length);
                Utils.selection(selection, options, screenSize, text, 3);
                if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            terminal.clearScreen();
                            placeOrder(terminal, customer); 
                            break;
                        case 1:
                            // TODO: IMPLEMENT VIEW ORDER HISTORY
                            break;
                        case 2:
                            // TODO: IMPLEMENT RATE ORDER
                            break;
                        case 3:
                            terminal.clearScreen();
                            break OUTER;
                        default: 
                            break;
                    }
                }
                terminal.flush();
            }
        } catch(Exception e) {
                        e.printStackTrace();
                }
    }
    public static void placeOrder(Terminal terminal, Customer customer) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "PLACE ORDER", 1);
                  
            Select2D selection = new Select2D(0,0);
            String[][] options = {{"Place Order", "View Order History"},{"Rate Order", "Exit"}};
            Utils.selection2D(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                selection = Utils.changeSelection2D(selection, key, options.length, options[0].length);
                Utils.selection2D(selection, options, screenSize, text, 3);
               /* if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            // TODO: IMPLEMENT PLACE ORDER 
                            break;
                        case 1:
                            // TODO: IMPLEMENT VIEW ORDER HISTORY
                            break;
                        case 2:
                            // TODO: IMPLEMENT RATE ORDER
                            break;
                        case 3:
                            terminal.clearScreen();
                            break OUTER;
                        default: 
                            break;
                    } 
                } */
                terminal.flush();
            }
        } catch(Exception e) {
                        e.printStackTrace();
                }
    }
}
