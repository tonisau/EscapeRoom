package classes.item.implementations;

import classes.item.Item;

public class Enigma extends Item {
    public Enigma(String name, double price) {
        super(name, price);
    }

    public Enigma() {
        super();
    }

    @Override
    public String toString() {
        return "Enigma{" +
                "itemId=" + this.getItemId() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                '}';
    }
}
