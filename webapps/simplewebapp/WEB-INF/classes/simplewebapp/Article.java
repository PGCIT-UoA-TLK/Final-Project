package simplewebapp;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@SuppressWarnings("unused")
public class Article implements Serializable {
    private int articleId;
    private int userId;
    private User user;
    private Date date;
    private String title;
    private String body;
    private String embeddedContent;
    private boolean active;
    private List<Comment> comments;

    public Article() {
        this.active = false;
    }

    public Article(int articleId, int userID, Date date, String title, String body, String embeddedContent) {
        this.articleId = articleId;
        this.userId = userID;
        this.user = null;
        this.date = date;
        this.title = title;
        this.body = body;
        this.embeddedContent = embeddedContent;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getEmbeddedContent() {
        return embeddedContent;
    }

    public void setEmbeddedContent(String embeddedContent) {
        this.embeddedContent = embeddedContent;
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