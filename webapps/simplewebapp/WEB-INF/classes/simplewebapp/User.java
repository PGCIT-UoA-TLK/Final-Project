package simplewebapp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int icon;
    private boolean active;

    public User() {
        this.active = false;
    }

    public User(int id, String username, String password, String firstname, String lastname, int icon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.active = true;
        this.icon = icon;
    }

    public User(int id, String username, String firstname, String lastname, int icon) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.active = true;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }
    public int getIcon() {
        return icon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
