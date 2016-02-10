import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;
import simplewebapp.Comment;
import simplewebapp.CommentDAO;
import simplewebapp.User;
import simplewebapp.UserDAO;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import simplewebapp.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ArticlePage extends Page {
    protected static void article(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        //Parsing the parameter to an int
        int id;
        try {
            id = Integer.parseInt(request.getParameter("article"));
        } catch (Exception ignored) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        // Getting all the articles as a list
        Article article = articleDAO.getByArticleID(id);

        request.setAttribute("article", article);

        // Comment needs to be deleted
        if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            Comment c = CommentDAO.getInstance().getComment(Integer.parseInt(request.getParameter("commentID")));
            CommentDAO.getInstance().deleteComment(c);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        }

        List<Comment> comments = CommentDAO.getInstance().getCommentsByArticleID(article.getArticleId());
        List<User> users = UserDAO.getInstance().getAll();

        request.setAttribute("comments", comments);
        request.setAttribute("users", users);

        // If the user's logged in, load the comment box
        User user = (User) request.getAttribute("user");
        if (user != null && request.getParameter("commentBox") != null) {
            String newComment = request.getParameter("commentBox");
            CommentDAO.getInstance().addNewComment(newComment, article.getArticleId(), user.getUserId());
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        }

        navigate("/WEB-INF/article.jsp", request, response);
    }

    protected static void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        String newTitle = "";
        String articleText = "";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try{
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();

            while(i.hasNext()){
                FileItem fi = (FileItem)i.next();
                if(!fi.isFormField() && fi.getName() != null && !fi.getName().equals("")){
                    saveFile(fi);
                }else if(fi.getFieldName().equals("articleTitle")){
                    newTitle = fi.getString();
                }else{
                    articleText = fi.getString();
                }
            }
        } catch (FileUploadException e) {
            System.out.println("No input detected. Continuing");
        }

        if(!articleText.equals("") && !newTitle.equals("")) {
            ArticleDAO.getInstance().addNewArticle(user.getUserId(), newTitle, articleText);

            response.sendRedirect(request.getContextPath());
            return;
        }

        navigate("/WEB-INF/addArticle.jsp", request, response);
    }

    protected static void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleID = Integer.parseInt(request.getParameter("articleID"));

        Article article = ArticleDAO.getInstance().getByArticleID(articleID);

        String newTitle = request.getParameter("articleTitle");
        String newBody = request.getParameter("articleBody");

        String articleTitle = (newTitle != null ? newTitle : article.getTitle());
        String articleBody = (newBody != null ? newBody : article.getBody());

        // Update the article if there are changes
        if (!article.getTitle().equals(articleTitle) || !article.getBody().equals(articleBody)) {
            article.setTitle(newTitle);
            article.setBody(newBody);

            ArticleDAO.getInstance().updateArticle(article);

            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        }

        // Delete the article
        if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            ArticleDAO.getInstance().deleteArticle(article);
            response.sendRedirect(request.getContextPath());
            return;
        }

        request.setAttribute("article", article);

        navigate("/WEB-INF/editArticle.jsp", request, response);
    }

    protected static void saveFile(FileItem fi) throws ServletException, IOException, FileUploadException {
        String filepath = "\\webapps\\uploads\\";
        String fileName = fi.getName();

        File file = new File(filepath, fileName);
        try {
            fi.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
