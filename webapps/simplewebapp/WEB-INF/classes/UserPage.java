import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.IOException;
import java.util.List;

public class UserPage extends Page {
    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        Boolean allowed = true;

        List<User> allUsers = UserDAO.getInstance().getAll();

        for (User u : allUsers) {
            // username = username.trim();
            if (username != null && u.getUsername() != null && username.equals(u.getUsername())) {
                request.setAttribute("errorMessage", "That username is taken.");
                allowed = false;
                break;
            }
        }

        System.err.println(allowed);

        if (allowed && username != null && !username.equals("") && password != null && !password.equals("") &&
                firstname != null && !firstname.equals("") && lastname != null && !lastname.equals("")) {
            UserDAO userDAO = UserDAO.getInstance();

            int icon;
            String selection = request.getParameter("optionsRadios");
            switch (selection) {
                case "option1": icon = 1; break;
                case "option2": icon = 2; break;
                default: icon = 0; break;
            }

            User newUser = userDAO.addUser(username, password, firstname, lastname, icon);

            if (newUser != null) {
                request.getSession().setAttribute("user", newUser);

                String returnPage = "";
                if (request.getParameter("backpage") != null) {
                    returnPage = "?page=" + request.getParameter("backpage");
                }

                response.sendRedirect("/simplewebapp/" + returnPage);
            }
        }

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

                response.sendRedirect("/simplewebapp/?loginSuccess=1");
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
