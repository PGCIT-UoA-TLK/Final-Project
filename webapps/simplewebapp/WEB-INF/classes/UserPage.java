import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.IOException;

public class UserPage extends Page {
    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/addUser.jsp", request, response);
    }

    public static void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Setting Defaults
        request.setAttribute("hasError", Boolean.FALSE);
        request.setAttribute("errorMessage", "");

        if (username != null && password != null) {
            UserDAO userDAO = UserDAO.getInstance();

            User thisUser = userDAO.loginUser(username, password);

            if (thisUser != null) {
                request.getSession().setAttribute("user", thisUser);

                String returnPage = "";
                if (request.getParameter("backpage") != null) {
                    returnPage = "&page=" + request.getParameter("backpage");
                }

                response.sendRedirect("/simplewebapp/?loginSuccess=1" + returnPage);
            } else {
                request.setAttribute("hasError", Boolean.TRUE);
                request.setAttribute("errorMessage", "Incorrect Username or Password");
            }
        }

        navigate("/WEB-INF/loginUser.jsp", request, response);
    }

    public static void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/editUser.jsp", request, response);
    }
}
