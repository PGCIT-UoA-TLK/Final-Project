import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.User;
import simplewebapp.UserDAO;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;


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

        Boolean allowed = true;

        List<User> allUsers = UserDAO.getInstance().getAll();

        String userResponse = request
                .getParameter("g-recaptcha-response");
        System.out.print("respond : "+userResponse);
        System.out.print( verify(userResponse));



        for (User u : allUsers) {
            // username = username.trim();
            if (username != null && u.getUsername() != null && username.equals(u.getUsername())&&verify(userResponse)) {
                request.setAttribute("errorMessage", "That username is taken.");
                allowed = false;
                break;
            }
        }

        if (allowed && username != null && !username.equals("") && password != null && !password.equals("") &&
                firstname != null && !firstname.equals("") && lastname != null && !lastname.equals("")) {
            UserDAO userDAO = UserDAO.getInstance();

            int icon;
            switch (selection) {
                case "option1": icon = 1; break;
                case "option2": icon = 2; break;
                case "option3": icon = 3; break;
                default: icon = 0; break;
            }

            User newUser = userDAO.addUser(username, password, firstname, lastname, age, gender, icon);

            if (newUser != null) {
                request.getSession().setAttribute("user", newUser);

                response.sendRedirect(request.getContextPath());
                return;
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

                response.sendRedirect(request.getContextPath() + "?loginSuccess=1");
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

        boolean edited = false;

        String firstname = request.getParameter("firstname");
        if (firstname != null && !firstname.equals("")) {
            user.setFirstname(firstname); edited = true;
        }

        String lastname = request.getParameter("lastname");
        if (lastname != null && !lastname.equals("")) {
            user.setLastname(lastname); edited = true;
        }

        if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            UserDAO.getInstance().deleteUser(user);
            response.sendRedirect(request.getContextPath() + "?logout=1");
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
        } else if (request.getParameter("failure") != null) {
            request.setAttribute("errorMessage", "Something went wrong! Please try again");
        }

        request.setAttribute("user", user);

        navigate("/WEB-INF/editUser.jsp", request, response);
    }




        public static boolean verify(String gRecaptchaResponse) throws IOException {
            if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
                return false;
            }

            try{
                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                // add reuqest header
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String postParams = "secret=" + secret + "&response="
                        + gRecaptchaResponse;

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

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());

                //parse JSON response and return 'success' value
//                JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
//                JsonObject jsonObject = jsonReader.readObject();
//                jsonReader.close();

//                return jsonObject.getBoolean("success");
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }

    }
}
