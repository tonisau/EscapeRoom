package classes.item;

public abstract class Item {
    private int itemId;
    private String name;
    private Double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(int itemId, String name, Double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public Item() { }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
