package app.fooddelivery.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.service.Restaurant;
import app.fooddelivery.object.Driver;

public class DriverUI {
        public static void home(Terminal terminal, Restaurant resturant, Driver driver) {
        try {
            TerminalSize screenSize = terminal.getTerminalSize();
            terminal.clearScreen();
            final TextGraphics text = terminal.newTextGraphics();
            text.setForegroundColor(TextColor.ANSI.GREEN);
            Utils.Title(screenSize, text);
            Utils.subTitle(screenSize, text, "DRIVER", 1);
                  
            int selection = 0;
            String[] options = {"View Order", "Update Order", "View Rates", "Exit"};
            Utils.selection(selection, options, screenSize, text, 3);
            OUTER:
                while (true) {
                KeyStroke key = terminal.readInput();
                selection = Utils.changeSelection(selection, key, options.length);
                Utils.selection(selection, options, screenSize, text, 3);
                if (key.getKeyType() == KeyType.Enter) {
                    switch (selection) {
                        case 0:
                            // TODO: IMPLEMENT VIEW ORDER 
                            break;
                        case 1:
                            // TODO: IMPLEMENT UPDATE ORDER
                            break;
                        case 2:
                            // TODO: IMPLEMENT VIEW RATE
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
