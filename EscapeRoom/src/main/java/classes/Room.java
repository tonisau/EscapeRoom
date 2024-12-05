package classes;

import classes.enums.Level;

public class Room {
    private Integer idRoom;
    private String name;
    private Double price;
    private Level level;

    public Room(String name, Double price, Level level){
        this.name=name;
        this.price=price;
        this.level=level;
    }

    public Room() {}

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
