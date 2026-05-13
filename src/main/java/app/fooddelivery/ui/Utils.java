package app.fooddelivery.ui;

import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import app.fooddelivery.service.Restaurant;

public class Utils {
        public interface Screen {
                Screen run(Term term, Restaurant restaurant);
        }

        interface Line {
                String render(boolean selected);

                void onKey(KeyStroke key);
        }

        interface Action {
                Screen run();
        }

        public static class Term {
                private Terminal terminal;
                private TextGraphics text;
                private TerminalSize size;

                public Term() {
                        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
                        try {
                                this.terminal = defaultTerminalFactory.createTerminal();
                                this.size = terminal.getTerminalSize();
                                text = terminal.newTextGraphics().setForegroundColor(TextColor.ANSI.GREEN);
                        } catch (IOException e) {
                                System.err.println("Failed to create terminal");
                        }
                }

                public Terminal terminal() {
                        return terminal;
                }

                public TextGraphics text() {
                        return text;
                }

                public TerminalSize size() {
                        return size;
                }

                public KeyStroke readKey() {
                        try {
                                return terminal.readInput();
                        } catch (IOException e) {
                                System.err.println("Failed to read terminal input");
                                return null;
                        }
                }

                public void flush() {
                        try {
                                terminal.flush();
                        } catch (IOException e) {
                                System.err.println("Failed to flush terminal");
                        }
                }

                public void clear() {
                        try {
                                terminal.clearScreen();
                        } catch (IOException e) {
                                System.err.println("Failed to clear terminal");

                        }
                }

        }

        public static class Text implements Line {
                private String label;

                Text(String label) {
                        this.label = label;
                }
                @Override
                public String render(boolean selected) {
                        return label;
                }
                @Override
                public void onKey(KeyStroke key) {
                }
        }

        public static class Button implements Line {
                private String label;
                private Action action;
                private Screen result = null;

                Button(String label, Action action) {
                        this.label = label;
                        this.action = action;
                }
                @Override
                public String render(boolean selected) {
                        return (selected ? "> " : " ") + label;
                }
                @Override
                public void onKey(KeyStroke key) {
                        if (key.getKeyType() == KeyType.Enter) {
                                result = action.run();
                        }
                }

                public Screen result() {
                        Screen temp = result;
                        result = null;
                        return temp;
                }
        }

        public static class Amount implements Line {
                private final String label;
                private int quantity;
                final int min;
                final int max;

                Amount(String label) {
                        this.label = label;
                        this.min = 0;
                        this.max = 100;
                        this.quantity = this.min;
                }

                Amount(String label, int min, int max) {
                        this.label = label;
                        this.quantity = min;
                        this.min = min;
                        this.max = max;
                }
                @Override
                public String render(boolean selected) {
                        return (selected ? "> " : " ") + label + " - " + quantity + " + ";
                }
                @Override
                public void onKey(KeyStroke key) {
                        if (key.getKeyType() == KeyType.ArrowRight && quantity < max) {
                                quantity++;
                        }
                        if (key.getKeyType() == KeyType.ArrowLeft && quantity > min) {
                                quantity--;
                        }
                }

                public int quantity() {
                        return quantity;
                }
        }

        public static class Input implements Line {
                private final String label;
                private String value = "";
                private final boolean hidden;

                Input(String label, boolean hidden) {
                        this.label = label;
                        this.hidden = hidden;
                }
                @Override
                public String render(boolean selected) {
                        String display = hidden ? "*".repeat(value.length()) : value;
                        return (selected ? "> " : " ") + label + " " + display;
                }
                @Override
                public void onKey(KeyStroke key) {
                        if (key.getKeyType() == KeyType.Character) {
                                value += key.getCharacter();
                        }
                        if (key.getKeyType() == KeyType.Backspace && value.length() > 0) {
                                value = value.substring(0, value.length() - 1);
                        }
                }

                public String getInput() {
                        return value;
                }
        }
        
        public static void render(List<Line> lines, int selection, TerminalSize screenSize, TextGraphics text, int y) {
                for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i).render(i == selection);

                        text.putString(0, y + i, " ".repeat(screenSize.getColumns()));
                        int x = screenSize.getColumns() / 2 - line.length() / 2;
                        text.putString(x, y + i, line);
                }
        }

        public static int keyPress(int selection, KeyStroke key, List<Line> lines) {
                if (key.getKeyType() == KeyType.ArrowDown)
                        selection++;
                else if (key.getKeyType() == KeyType.ArrowUp)
                        selection--;
                else if (key.getKeyType() == KeyType.Enter && (lines.get(selection) instanceof Utils.Input
                                || lines.get(selection) instanceof Utils.Amount))
                        selection++;

                if (selection < 0)
                        selection = lines.size() - 1;
                if (selection >= lines.size())
                        selection = 0;

                return selection;
        }

        public static Screen loop(List<Line> title, List<Line> lines, Term term) {
                int selection = 0;

                render(title, -1, term.size(), term.text(), 0);
                render(lines, selection, term.size(), term.text(), 3);

                term.flush();

                while (true) {
                        KeyStroke key = term.readKey();

                        Line current = lines.get(selection);
                        current.onKey(key);

                        if (current instanceof Button) {
                                Button b = (Button) current;
                                Screen next = b.result();
                                if (next != null) {
                                        return next;
                                }
                        }

                        selection = keyPress(selection, key, lines);

                        render(title, -1, term.size(), term.text(), 0);
                        render(lines, selection, term.size(), term.text(), 3);

                        term.flush();
                }
        }

        public static Screen loop(List<Line> title, List<Line> lines, Term term, int y) {
                int selection = 0;

                render(title, -1, term.size(), term.text(), 0);
                render(lines, selection, term.size(), term.text(), y);

                term.flush();

                while (true) {
                        KeyStroke key = term.readKey();

                        Line current = lines.get(selection);
                        current.onKey(key);

                        if (current instanceof Button) {
                                Button b = (Button) current;
                                Screen next = b.result();
                                if (next != null) {
                                        return next;
                                }
                        }

                        selection = keyPress(selection, key, lines);

                        render(title, -1, term.size(), term.text(), 0);
                        render(lines, selection, term.size(), term.text(), y);

                        term.flush();
                }
        }
}