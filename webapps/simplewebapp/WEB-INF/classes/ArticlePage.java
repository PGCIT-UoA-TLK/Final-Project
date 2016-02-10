import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import simplewebapp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();

            List<FileItem> files = new ArrayList<>();

            String newTitle = "";
            String articleText = "";

            while (i.hasNext()) {
                FileItem file = (FileItem) i.next();
                if (!file.isFormField() && file.getName() != null && !file.getName().equals("")) {
                    files.add(file);
                } else if (file.getFieldName().equals("articleTitle")) {
                    newTitle = file.getString();
                } else if (file.getFieldName().equals("articleText")) {
                    articleText = file.getString();
                }
            }

            int articleId = 0;
            if (!articleText.equals("") && !newTitle.equals("")) {
                articleId = ArticleDAO.getInstance().addNewArticleWithId(user.getUserId(), newTitle, articleText);
            }

            if (articleId > 0) {
                for (FileItem file : files) {
                    String filePath = saveFile(file);
                    UploadedFileDAO.getInstance().addNewFile(articleId, filePath);
                }
            }

            if (!articleText.equals("") && !newTitle.equals("")) {
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

    protected static String saveFile(FileItem fi) {
        String filepath = "webapps\\uploads\\";
        String fileName = fi.getName();

        File file = new File(filepath, fileName);
        try {
            fi.write(file);
            return (filepath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
