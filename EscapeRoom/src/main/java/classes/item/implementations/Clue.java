package classes.item.implementations;

import classes.item.Item;
import classes.enums.Theme;

public class Clue extends Item {

    Theme theme;

    public Clue(String name, double price, Theme theme) {
        super(name, price);
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
