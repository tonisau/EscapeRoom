package classes;

import classes.enums.Level;

public class Room {
    private int idRoom;
    private String name;
    private double price;
    private Level level;

    public Room(int idRoom, String name, double price, Level level){
        this.idRoom=idRoom;
        this.name=name;
        this.price=price;
        this.level=level;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Room{" +
                "idRoom=" + idRoom +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", level=" + level +
                '}';
    }
}
