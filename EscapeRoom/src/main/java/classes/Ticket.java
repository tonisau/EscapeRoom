package classes;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ticket {
    private int roomId;
    private double price ;
    private LocalDateTime saleDate;
    private List<User> users;

    public Ticket(){}

    public Ticket(int roomId, LocalDateTime saleDate, double price, List<User> users){
        this.roomId = roomId;
        this.saleDate = saleDate;
        this.price = price;
        this.users = users;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "Room " + roomId +
                ", date: " + saleDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                ", total price for all players: " + String.format("%.2f", price) +
                "€, players: \n" + users +
                '}';
    }
}
