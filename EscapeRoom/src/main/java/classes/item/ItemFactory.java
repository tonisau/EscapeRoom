package classes.item;

import classes.enums.Material;
import classes.enums.Theme;
import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;
import classes.item.implementations.Gift;

public interface ItemFactory {
    Decoration createDecoration(String name, Double price, Material material, Integer quantity);
    Clue createClue(String name, Double price, Theme theme);
    Enigma createEnigma(String name, Double price);
    Gift createGift(String name, Double price);

    Decoration createDecoration();
    Clue createClue();
    Enigma createEnigma();
    Gift createGift();
}
