package simplewebapp;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class Article implements Serializable {
    private int articleId;
    private int userId;
    private String title;
    private String body;
    private boolean active;
    private List<Comment> comments;

    public Article() {
        this.active = false;
    }

    public Article(int articleId, int userID, String title, String body) {
        this.articleId = articleId;
        this.userId = userID;
        this.title = title;
        this.body = body;
        this.active = true;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}