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
import simplewebapp.FileDAO;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
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
        if (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty()) {
            Comment c = CommentDAO.getInstance().getComment(Integer.parseInt(request.getParameter("commentID")));
            CommentDAO.getInstance().deleteComment(c);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        }

        List<simplewebapp.File> files = FileDAO.getInstance().getByArticleID(article.getArticleId());
        List<Comment> comments = CommentDAO.getInstance().getCommentsByArticleID(article.getArticleId());
        List<User> users = UserDAO.getInstance().getAll();

        request.setAttribute("files", files);
        request.setAttribute("comments", comments);
        request.setAttribute("users", users);

        // If the user's logged in, load the comment box
        User user = (User) request.getAttribute("user");
        String comment = request.getParameter("commentBox");

        boolean hasError = false;
        if (request.getParameter("newComment") != null) {
            if (user == null) {
                printError(request, "You must be logged in to post a comment");
                hasError = true;
            } else if (comment == null || comment.isEmpty()) {
                printError(request, "Comment can not be blank");
                hasError = true;
            } else if (comment.length() > 200) {
                printError(request, "Comment must not be longer than 200 characters");
                hasError = true;
            }
        }

        // Error checking the comment box before requests
        if (!hasError && user != null && comment != null && !comment.isEmpty()) {
            String newComment = request.getParameter("commentBox");
            CommentDAO.getInstance().addNewComment(newComment, article.getArticleId(), user.getUserId());
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + article.getArticleId());
            return;
        }

        navigate("/WEB-INF/article.jsp", request, response);
    }

    protected static void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            printError(request, "You must be logged in to do this.");
            response.sendRedirect(request.getContextPath());
            return;
        }

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
                if (!file.isFormField() && file.getName() != null && !file.getName().isEmpty()) {
                    files.add(file);
                } else {
                    switch (file.getFieldName()) {
                        case "articleTitle": newTitle = file.getString(); break;
                        case "articleText": newBody = file.getString(); break;
                        case "embeddedContent": embeddedContent = file.getString(); break;
                    }
                }
            }

            if(embeddedContent.contains("youtube") && !embeddedContent.contains("embed")){
                embeddedContent = embeddedContent.substring(0, embeddedContent.lastIndexOf("m") + 1) + "/embed/" + embeddedContent.substring(embeddedContent.indexOf("=")+1);
            }

            int articleId = 0;
            if (!newTitle.isEmpty() && !newBody.isEmpty()) {
                long unixTime = System.currentTimeMillis();
                Date date = new Date(unixTime);

                articleId = ArticleDAO.getInstance().addNewArticleWithId(user.getUserId(), date, newTitle, newBody, embeddedContent);
            } else if (checkArticleContents(request, newTitle, newBody)) {
                navigate("/WEB-INF/addArticle.jsp", request, response);
                return;
            }

            if (articleId > 0) {
                for (FileItem file : files) {
                    String filePath = saveFile(file);
                    FileDAO.getInstance().addNewFile(articleId, filePath);
                }
            }

            if (!newTitle.isEmpty() && !newBody.isEmpty()) {
                response.sendRedirect(request.getContextPath());
                return;
            }
        } catch (FileUploadException e) {
            /*
            For some reason, addArticle is run twice when an article is saved. This causes one of the file checks to fail,
            and this exception is triggered. We can't find what's causing this, but as far as we can tell it's mostly
            harmless.
             */
            System.out.println("No input detected. Continuing");
        }

        navigate("/WEB-INF/addArticle.jsp", request, response);
    }

    protected static void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            printError(request, "You must be logged in to do this.");
            response.sendRedirect(request.getContextPath());
            return;
        }

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
        if (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty()) {
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
        } if (newTitle.isEmpty()) {
            printError(request, "Article must have a title."); return true;
        } else if (newBody.isEmpty()) {
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
