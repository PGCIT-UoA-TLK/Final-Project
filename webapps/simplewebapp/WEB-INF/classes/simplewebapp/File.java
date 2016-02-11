package simplewebapp;

import java.io.Serializable;
import java.sql.Blob;

@SuppressWarnings("unused")
public class File implements Serializable {
    private int fileId;
    private int articleId;
    private String filepath;



    public File() {

    }

    public File(int fileId, int articleId, String filepath) {
        this.fileId = fileId;
        this.articleId = articleId;
        this.filepath = filepath;

    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


}


