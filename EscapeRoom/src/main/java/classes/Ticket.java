package classes;

import java.util.Date;
import java.util.List;

public class Ticket {
    private int roomId;
    private double price ;
    private Date saleDate;
    private List<User> users;

    public Ticket(int roomId, Date saleDate, double price, List<User> users){
        this.roomId = roomId;
        this.saleDate = saleDate;
        this.users = users;
    }

    public static void setPrice(double price) {
        Ticket.price = price;
    }

    public static double getPrice() {
        return price;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "Sala " + roomId +
                ", fecha: " + saleDate +
                ", precio: " + price +
                "€, judadores: " + users +
                '}';
    }
}
