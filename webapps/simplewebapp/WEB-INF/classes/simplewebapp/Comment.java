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

    public int getComment_id(){
        return this.comment_id;
    }

    public int getArticle_id(){
        return this.article_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public String getBody(){
        return this.body;
    }

}