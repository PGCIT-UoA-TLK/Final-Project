package simplewebapp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class User implements Serializable {
    private int userId;
    private String username;
    private byte[] password;
    private String salt;
    private String firstname;
    private String lastname;
    private String age;
    private String gender;
    private String image;
    private boolean active;

    public User() {
        this.active = false;
    }

    public User(int userId, String username, byte[] password, String salt, String firstname, String lastname, String age, String gender, String image) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.image = image;
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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
