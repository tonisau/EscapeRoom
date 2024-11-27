package classes;

import subscription.Subscriber;

import java.util.List;

public class User implements Subscriber {
    private int id;
    private String name;
    private String email;
    private boolean isSuscriber;
    private List<String> certificates;
    private List<String> gifts;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, boolean isSuscriber) {
        this.id = id;
        this.name = name;
        this.isSuscriber = isSuscriber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsSuscriber(boolean status) {
        this.isSuscriber = status;
    }

    public void addCertificate(String certificate){
        this.certificates.add(certificate);
    }

    public void addGift(String gift){
        this.gifts.add(gift);
    }

    @Override
    public void update(String event) {
        displayEvent(event);
    }
    public void displayEvent(String event){
        System.out.println("Hola " + this.name + ", tenemos noticias para ti:\n"
                + event );
    }
}

