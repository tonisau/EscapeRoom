package classes.item;

import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;

public class ItemFactory {
    public static Item createItem(ItemType type, String name, double price) {
        return switch (type) {
            case CLUE -> new Clue(name, price);
            case ENIGMA -> new Enigma(name, price);
            case DECORATION -> new Decoration(name, price);
            default -> throw new IllegalArgumentException("Unknown item type: " + type);
        };
    }
}
