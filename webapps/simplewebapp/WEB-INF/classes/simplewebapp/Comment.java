package simplewebapp;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Comment implements Serializable {

    private int comment_id;
    private int article_id;
    private int user_id;
    private String body;
    private boolean active;

    public Comment() {
        this.active = false;
    }

    public Comment(int comment_id, int article_id, int user_id, String body) {
        this.comment_id = comment_id;
        this.article_id = article_id;
        this.user_id = user_id;
        this.body = body;
        this.active = true;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}