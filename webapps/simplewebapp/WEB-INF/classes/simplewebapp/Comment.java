package simplewebapp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Comment implements Serializable {

    private int commentId;
    private int articleId;
    private int userId;
    private String body;
    private boolean active;

    private User user;

    public Comment() {
        this.active = false;
    }

    public Comment(int commentId, int articleId, int user_id, String body) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userId = user_id;
        this.body = body;
        this.active = true;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}