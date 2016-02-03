package simplewebapp;

/**
 * Created by klev006 on 2/02/2016.
 */
public class Comment {

    private int comment_id;
    private int article_id;
    private int user_id;
    private String body;

    Comment(int comment_id, int article_id, String body) {
        this.comment_id = comment_id;
        this.article_id = article_id;
        //this.user_id = user_id;
        this.body = body;

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
}