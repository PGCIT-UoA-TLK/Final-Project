package simplewebapp;

public class Article {

    private int id;
    private int userID;
    private String title;
    private String body;
    private boolean active;

    public Article(int id, int userID, String title, String body) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.body = body;
    }

    public int getID() {
        return this.id;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}