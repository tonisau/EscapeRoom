package classes.item.implementations;

import classes.item.Item;
import classes.item.Theme;

public class Clue extends Item {

    Theme theme;

    public Clue(String name, double price) {
        super(name, price);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
