package classes.item.implementations;

import classes.enums.Material;
import classes.enums.Theme;
import classes.item.ItemFactory;

public class ItemFactoryImpl implements ItemFactory {
    @Override
    public Decoration createDecoration(String name, Double price, Material material, Integer quantity) {
        return new Decoration(name, price, material, quantity);
    }

    @Override
    public Clue createClue(String name, Double price, Theme theme) {
        return new Clue(name, price, theme);
    }

    @Override
    public Enigma createEnigma(String name, Double price) {
        return new Enigma(name, price);
    }

    @Override
    public Gift createGift(String name, Double price) {
        return new Gift(name, price);
    }

    @Override
    public Decoration createDecoration() {
        return new Decoration();
    }

    @Override
    public Clue createClue() {
        return new Clue();
    }

    @Override
    public Enigma createEnigma() {
        return new Enigma();
    }

    @Override
    public Gift createGift() {
        return new Gift();
    }
}


