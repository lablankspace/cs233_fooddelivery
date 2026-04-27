package app.fooddelivery.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.object.Admin;
import app.fooddelivery.service.Restaurant;

public class AdminUI {
    public static void home(Terminal terminal, Admin admin) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "ADMIN", 1);
                  
            int selection = 0;
            String[] options = {"Update Menu", "View Orders", "View Drivers", "Exit"};
            Utils.selection(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                selection = Utils.changeSelection(selection, key, options.length);
                Utils.selection(selection, options, screenSize, text, 3);
                if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            // TODO: IMPLEMENT UPDATE MENU 
                            break;
                        case 1:
                            // TODO: IMPLEMENT VIEW ORDERS
                            break;
                        case 2:
                            // TODO: IMPLEMENT VIEW DRIVERS
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
}
