package classes.item.implementations;

import classes.item.Item;
import classes.item.Material;

public class Decoration extends Item {

    Material material;
    int quantity;

    public Decoration(String name, double price) {
        super(name, price);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}