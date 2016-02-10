package simplewebapp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class User implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String age;
    private String gender;
    private int icon;
    private boolean active;

    public User() {
        this.active = false;
    }

    public User(int userId, String username, String password, String firstname, String lastname, String age, String gender, int icon) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.icon = icon;
        this.active = true;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

   public void setAge(String age){ this.age = age; }

    public String getAge(){return age;}


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
