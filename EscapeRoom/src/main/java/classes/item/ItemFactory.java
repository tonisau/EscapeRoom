package classes.item;

import classes.item.implementations.Clue;
import classes.item.implementations.Decoration;
import classes.item.implementations.Enigma;

public interface ItemFactory {
    Decoration createDecoration(String name, double price, Material material, int quantity);
    Clue createClue(String name, double price, Theme theme);
    Enigma createEnigma(String name, double price);
    Gift createGift(String name, double price);
    Enigma createEnigma();
}
