package classes.item.implementations;

import classes.item.Item;
import classes.enums.Material;

public class Decoration extends Item {

    Material material;
    Integer quantity;

    Decoration(String name, Double price, Material material, Integer quantity) {
        super(name, price);
        this.setMaterial(material);
        this.setQuantity(quantity);
    }

    Decoration() { super(); }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Decoration{" +
                "itemId=" + this.getItemId() +
                ", name='" + this.getName() +
                ", price= " + String.format("%.2f", this.getPrice()) + "€" +
                ", material=" + this.getMaterial().name() +
                ", quantity=" + this.getQuantity() +
                '}';
    }
}
