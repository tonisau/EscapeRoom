package classes.item;

import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;
import classes.item.implementations.Gift;

public class ItemFactoryImpl implements ItemFactory {
    @Override
    public Decoration createDecoration(String name, double price, Material material, int quantity) {
        return new Decoration(name, price, material, quantity);
    }

    @Override
    public Clue createClue(String name, double price, Theme theme) {
        return new Clue(name, price, theme);
    }

    @Override
    public Enigma createEnigma(String name, double price) {
        return new Enigma(name, price);
    }

    @Override
    public Gift createGift(String name, double price) {
        return new Gift(name, price);
    }
}
