import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;
import simplewebapp.Comment;
import simplewebapp.CommentDAO;
import simplewebapp.UploadedFileDAO;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

        List<simplewebapp.File> files = UploadedFileDAO.getInstance().getByArticleID(article.getArticleId());
        List<Comment> comments = CommentDAO.getInstance().getCommentsByArticleID(article.getArticleId());
        List<User> users = UserDAO.getInstance().getAll();

        request.setAttribute("files", files);
        request.setAttribute("comments", comments);
        request.setAttribute("users", users);

        // If the user's logged in, load the comment box
        User user = (User) request.getAttribute("user");
        String comment = request.getParameter("commentBox");

        if (user != null && comment != null && !comment.equals("")) {
            String newComment = request.getParameter("commentBox");
            CommentDAO.getInstance().addNewComment(newComment, article.getArticleId(), user.getUserId());
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        } else if (user == null) {
            printError(request, "You must be logged in to post a comment");
        } else if (comment == null || comment.equals("")) {
            printError(request, "Comment can not be blank");
        } else if (comment.length() > 200) {
            printError(request, "Comment must not be longer than 200 characters");
        }

        navigate("/WEB-INF/article.jsp", request, response);
    }

    protected static void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();

            List<FileItem> files = new ArrayList<>();

            String newTitle = "";
            String newBody = "";
            String embeddedContent = "";

            while (i.hasNext()) {
                FileItem file = (FileItem) i.next();
                if (!file.isFormField() && file.getName() != null && !file.getName().equals("")) {
                    files.add(file);
                } else {
                    switch (file.getFieldName()) {
                        case "articleTitle": newTitle = file.getString(); break;
                        case "articleText": newBody = file.getString(); break;
                        case "embeddedContent": embeddedContent = file.getString(); break;
                    }
                }
            }

            int articleId = 0;
            if (!newTitle.equals("") && !newBody.equals("")) {
                articleId = ArticleDAO.getInstance().addNewArticleWithId(user.getUserId(), newTitle, newBody, embeddedContent);
            } else if (checkArticleContents(request, newTitle, newBody)) {
                navigate("/WEB-INF/addArticle.jsp", request, response);
                return;
            }

            if (articleId > 0) {
                for (FileItem file : files) {
                    String filePath = saveFile(file);
                    UploadedFileDAO.getInstance().addNewFile(articleId, filePath);
                }
            }

            if (!newTitle.equals("") && !newBody.equals("")) {
                response.sendRedirect(request.getContextPath());
                return;
            }
        } catch (FileUploadException e) {
            System.out.println("No input detected. Continuing");
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
            if (checkArticleContents(request, newTitle, newBody)) {
                navigate("/WEB-INF/editArticle.jsp", request, response);
                return;
            }

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

    private static boolean checkArticleContents(HttpServletRequest request, String newTitle, String newBody) {
        if (newTitle.length() > 250) {
            printError(request, "Title must not be longer than 250 characters");
        } if (newTitle.equals("")) {
            printError(request, "Article must have a title."); return true;
        } else if (newBody.equals("")) {
            printError(request, "Article must have some content."); return true;
        }
        return false;
    }

    protected static String saveFile(FileItem fi) {
        String filepath = "\\uploads\\";
        String fileName = fi.getName();

        File file = new File("webapps" + filepath, fileName);
        try {
            fi.write(file);
            return (filepath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
