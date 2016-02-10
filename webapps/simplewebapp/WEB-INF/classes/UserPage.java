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

        String selection = request.getParameter("optionsRadios");
        String gender = request.getParameter("input-gender");

        if (request.getParameterMap().size() > 1) {
            Boolean allowed = checkRequiredUserParameters(request, username, password, firstname, lastname);

            if (allowed) {
                UserDAO userDAO = UserDAO.getInstance();

                int icon;
                switch (selection) {
                    case "option1":
                        icon = 1;
                        break;
                    case "option2":
                        icon = 2;
                        break;
                    default:
                        icon = 0;
                        break;
                }

                User newUser = userDAO.addUser(username, password, firstname, lastname, gender, icon);

                if (newUser != null) {
                    request.getSession().setAttribute("user", newUser);

                    response.sendRedirect(request.getContextPath() + "?registerSuccess");
                    return;
                }
            }
        }

        navigate("/WEB-INF/addUser.jsp", request, response);
    }

    public static void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Setting Defaults
        if (username != null && password != null) {
            UserDAO userDAO = UserDAO.getInstance();

            User thisUser = userDAO.loginUser(username, password);

            if (thisUser != null) {
                request.getSession().setAttribute("user", thisUser);

                response.sendRedirect(request.getContextPath() + "?loginSuccess");
                return;
            } else {
                request.setAttribute("errorMessage", "Incorrect Username or Password");
            }
        }

        navigate("/WEB-INF/loginUser.jsp", request, response);
    }

    public static void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        boolean edited = false;
        if (firstname != null && !firstname.equals("")) {
            user.setFirstname(firstname);
            edited = true;
        }

        if (lastname != null && !lastname.equals("")) {
            user.setLastname(lastname);
            edited = true;
        }

        if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            UserDAO.getInstance().deleteUser(user);
            response.sendRedirect(request.getContextPath() + "?deleteSuccess");
            return;
        }

        if (edited) {
            UserDAO userDAO = UserDAO.getInstance();

            boolean done = userDAO.updateUser(user);

            if (done) {
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "?page=editUser&success=1");
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "?page=editUser&failure=1");
                return;
            }
        }

        if (request.getParameter("success") != null) {
            request.setAttribute("successMessage", "Account Details Saved!");
        } else if (request.getParameter("failure") != null && request.getAttribute("errorMessage") != null) {
            request.setAttribute("errorMessage", "Something went wrong! Please try again");
        }

        request.setAttribute("user", user);

        navigate("/WEB-INF/editUser.jsp", request, response);
    }

    private static Boolean checkRequiredUserParameters(HttpServletRequest request, String username, String password, String firstname, String lastname) {
        List<User> allUsers = UserDAO.getInstance().getAll();

        for (User u : allUsers) {
            if (username != null && u.getUsername() != null && username.equals(u.getUsername())) {
                request.setAttribute("errorMessage", "That username is taken.");
                return false;
            }
        }

        if (username == null || username.equals("")) {
            request.setAttribute("errorMessage", "Username is required.");
            return false;
        }

        if (password == null || password.equals("")) {
            request.setAttribute("errorMessage", "Password is required.");
            return false;
        }

        if (firstname == null || firstname.equals("") || lastname == null || lastname.equals("")) {
            request.setAttribute("errorMessage", "Firstname and Lastname are required.");
            return false;
        }

        return true;
    }
}
