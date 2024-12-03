package classes;

import subscription.Subscriber;

public class User implements Subscriber {
    private int id;
    private String name;
    private String email;
    private boolean isSuscriber;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email, boolean isSuscriber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isSuscriber = isSuscriber;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setIsSuscriber(boolean isSuscriber) {
        this.isSuscriber = isSuscriber;
    }

    @Override
    public void update(String event) {
        displayEvent(event);
    }
    public void displayEvent(String event){
        System.out.println("Hola " + this.name + ", tenemos noticias para ti:\n"
                + event );
    }

    public boolean isSuscriber() {
        return isSuscriber;
    }

}

