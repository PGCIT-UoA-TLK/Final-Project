package simplewebapp;

/**
 * Created by klev006 on 9/02/2016.
 */

import java.io.Serializable;
import java.sql.Blob;

@SuppressWarnings("unused")
public class File implements Serializable {
    private int file_id;
    private int article_id;
    private String image;
    private String audio;

    public File() {

    }

    public File(int file_id, int article_id, String image, String audio) {
        this.file_id = file_id;
        this.article_id = article_id;
        this.image = image;
        this.audio = audio;

    }

    public int getFile_id(){ return file_id; }

    public int getArticle_id() {
        return article_id;
    }

    public String getAudio() {
        return audio;
    }

    public String getImage() {
        return image;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}