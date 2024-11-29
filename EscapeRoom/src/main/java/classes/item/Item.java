package classes.item;

public abstract class Item {
    private int itemId;
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(int itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public Item() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
