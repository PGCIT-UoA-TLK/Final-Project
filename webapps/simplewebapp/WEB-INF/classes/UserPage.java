import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

public class UserPage extends Page {
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6Lfl2xcTAAAAAKC4PYbk_0AVGlMFaCFl8hP7getE";
    private final static String USER_AGENT = "Mozilla/5.0";

    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String age = request.getParameter("age");
        String selection = request.getParameter("optionsRadios");
        String gender = request.getParameter("input-gender");

        if (request.getParameterMap().size() > 1) {
            Boolean allowed = checkRequiredUserParameters(request, username, password, firstname, lastname);

            List<User> allUsers = UserDAO.getInstance().getAll();

            String userResponse = request.getParameter("g-recaptcha-response");
            System.out.print("respond : " + userResponse);
            System.out.print(verify(userResponse));


            for (User u : allUsers) {
                // username = username.trim();
                if (username != null && u.getUsername() != null && username.equals(u.getUsername()) && verify(userResponse)) {
                    printError(request, "That username is taken.");
                    allowed = false;
                    break;
                }

                if (!verify(userResponse)) {
                    request.setAttribute("errorMessage", "reCAPTCHA is invalid.");
                    System.out.println("reCAPTCHA is invalid");
                    allowed = false;
                    break;
                }
            }

            if (allowed && username != null && !username.equals("") && password != null && !password.equals("") &&
                    firstname != null && !firstname.equals("") && lastname != null && !lastname.equals("")) {
                UserDAO userDAO = UserDAO.getInstance();


                int icon;
                switch (selection) {
                    case "option1":
                        icon = 1;
                        break;
                    case "option2":
                        icon = 2;
                        break;
                    case "option3":
                        icon = 3;
                        break;
                    default:
                        icon = 0;
                        break;
                }

                User newUser = userDAO.addUser(username, password, firstname, lastname, gender, age, icon);

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
                printError(request, "Incorrect Username or Password.");
            }
        }

        navigate("/WEB-INF/loginUser.jsp", request, response);
    }

    public static void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            printError(request, "You must be logged in to do this.");
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
            request.getSession().setAttribute("user", null);
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
            printSuccess(request, "Account Details Saved!");
        } else if (request.getParameter("failure") != null && request.getAttribute("errorMessage") != null) {
            printError(request, "Something went wrong! Please try again.");
        }

        request.setAttribute("user", user);

        navigate("/WEB-INF/editUser.jsp", request, response);
    }

    private static Boolean checkRequiredUserParameters(HttpServletRequest request, String username, String password, String firstname, String lastname) {
        List<User> allUsers = UserDAO.getInstance().getAll();

        for (User u : allUsers) {
            if (username != null && u.getUsername() != null && username.equals(u.getUsername())) {
                printError(request, "That username is taken.");
                return false;
            }
        }

        if (username == null || username.equals("")) {
            printError(request, "Username is required.");
            return false;
        }

        if (password == null || password.equals("")) {
            printError(request, "Password is required.");
            return false;
        }

        if (firstname == null || firstname.equals("") || lastname == null || lastname.equals("")) {
            printError(request, "Firstname and Lastname are required.");
            return false;
        }

        return true;
    }


    public static boolean verify(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + postParams);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

            //parse JSON response and return 'success' value
            JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
