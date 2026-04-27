package app.fooddelivery.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;


public class Utils {
        public static void Title(TerminalSize screenSize, TextGraphics text){
                String output = "ISLAND GIRLS DELIVERY SERVICE";
                text.putString(screenSize.getColumns() / 2 - output.length() / 2, 0, output, SGR.BOLD);
        } 
        public static void subTitle(TerminalSize screenSize, TextGraphics text, String output, int y){
                text.putString(screenSize.getColumns() / 2 - output.length() / 2, y, output, SGR.BOLD);
        }
        public static void selection(int selection, String[] options, TerminalSize screenSize, TextGraphics text, int y) {
                for (int i = 0; i < options.length; i++) {
                        String prefix = (i == selection) ? "> " : "  ";
                        String line = prefix + options[i];
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
        }
        public static int changeSelection(int selection, KeyStroke key, int optionsLength) {
                if ( key.getKeyType() == KeyType.ArrowDown ) {
                        selection++;
                }
                if ( key.getKeyType() == KeyType.ArrowUp ) { 
                        selection--;
                }
                if (selection < 0) {
                        selection = optionsLength - 1;
                }
                if (selection >= optionsLength) {
                        selection = 0;
                }
        return selection;
        }
        public static void input(int selection, String[] options, String[] inputs, TerminalSize screenSize, TextGraphics text, int y, boolean[] hidden) {
                for (int i = 0; i < options.length; i++) {
                        String prefix = (i == selection) ? "> " : "  ";
                        String line = prefix + options[i];
                        if ( !hidden[i] ) line += " " + inputs[i];
                        else {
                        String mask = "*".repeat(inputs[i].length());
                        line += " " + mask ;
                        }
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
       }
        public static void amount(int selection, String[] options, int[] quantity, TerminalSize screenSize, TextGraphics text, int y) {
                for (int i = 0; i < options.length; i++) {
                        String prefix = (i == selection) ? "> " : "  ";
                        String line = prefix + options[i];
                        line += " - " + quantity[i] + " +";
                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
        }
        public static int[] changeAmount(int selection, KeyStroke key, int[] quantity) {
                if ( key.getKeyType() == KeyType.ArrowRight ) {
                        quantity[selection]++;
                }
                if ( key.getKeyType() == KeyType.ArrowUp && quantity[selection] != 0) { 
                        quantity[selection]--;
                }
        return quantity;
        }
}