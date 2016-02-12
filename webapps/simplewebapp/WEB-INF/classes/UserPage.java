import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
import java.math.BigInteger;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserPage extends Page {
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    public static final String secret = "6Lfl2xcTAAAAALB8ESkpLs5W3UvoI104QOdIBgT9";
    private final static String USER_AGENT = "Mozilla/5.0";

    private static final Map<String, String> AGES = new TreeMap<>();

    static {
        AGES.put("", "Please select an age");
        AGES.put("0-15", "0-15");
        AGES.put("16-25", "16-25");
        AGES.put("26-35", "26-35");
        AGES.put("36-45", "36-45");
        AGES.put("46-55", "46-55");
        AGES.put("56-65", "56-65");
        AGES.put("66-75", "66-75");
        AGES.put("75 and over", "75 and over");
    }

    private static final Map<String, String> GENDERS = new HashMap<>();

    static {
        GENDERS.put("Male", "Male");
        GENDERS.put("Female", "Female");
    }

    private static final Map<String, String> IMAGES = new TreeMap<>();

    static {
        IMAGES.put("Donald", "userimage1.jpg");
        IMAGES.put("Fresco", "userimage2.jpg");
        IMAGES.put("Muppet", "userimage3.jpg");
    }

    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String age = request.getParameter("age");
        String image = request.getParameter("image");
        String gender = request.getParameter("gender");

        if (request.getParameterMap().size() > 1) {
            Boolean allowed = checkRequiredUserParameters(request, username, password, firstname, lastname, age, image, gender);

            User temp = UserDAO.getInstance().getUserByUsername(username);

            // If no user found, username isn't taken
            if (temp != null) {
                printError(request, "That username is taken.");
                allowed = false;
            }

            String userResponse = request.getParameter("g-recaptcha-response");
            if (!verify(userResponse)) {
                printError(request, "reCAPTCHA is invalid.");
                allowed = false;
            }

            if (allowed) {
                UserDAO userDAO = UserDAO.getInstance();

                String salt = new BigInteger(130, new SecureRandom()).toString(32);
                byte[] hashedPassword = hashPassword(password.toCharArray(), salt.getBytes());

                User newUser = userDAO.addUser(username, hashedPassword, salt, firstname, lastname, gender, age, image);

                if (newUser != null) {
                    request.getSession().setAttribute("user", newUser);

                    response.sendRedirect(request.getContextPath() + "?registerSuccess");
                    return;
                }
            }
        }

        request.setAttribute("genders", GENDERS);
        request.setAttribute("ages", AGES);
        request.setAttribute("images", IMAGES);

        navigate("/WEB-INF/addUser.jsp", request, response);
    }

    private static Boolean checkRequiredUserParameters(HttpServletRequest request, String username, String password, String firstname, String lastname, String age, String image, String gender) {
        List<User> allUsers = UserDAO.getInstance().getAll();

        for (User u : allUsers) {
            if (username != null && u.getUsername() != null && username.equals(u.getUsername())) {
                printError(request, "That username is taken.");
                return false;
            }
        }

        if (username == null || username.isEmpty()) {
            printError(request, "Username is required.");
            return false;
        }

        if (password == null || password.isEmpty()) {
            printError(request, "Password is required.");
            return false;
        }

        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty()) {
            printError(request, "Firstname and Lastname are required.");
            return false;
        }

        if (age == null || age.isEmpty() || AGES.get(age) == null || AGES.get(age).isEmpty()) {
            printError(request, "Please select an age.");
            return false;
        }

        if (image == null || image.isEmpty()) {
            printError(request, "Please select an image.");
            return false;
        }

        if (gender == null || gender.isEmpty() || GENDERS.get(gender) == null || GENDERS.get(gender).isEmpty()) {
            printError(request, "Please select a gender.");
            return false;
        }

        return true;
    }

    public static void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Setting Defaults
        if (username != null && password != null) {
            UserDAO userDAO = UserDAO.getInstance();

            User thisUser = userDAO.getUserByUsername(username);

            byte[] hashedPassword = new byte[0];
            if (thisUser != null) {
                hashedPassword = hashPassword(password.toCharArray(), thisUser.getSalt().getBytes());
            }

            if (thisUser != null && Arrays.equals(thisUser.getPassword(), hashedPassword)) {
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
        String age = request.getParameter("age");
        String image = request.getParameter("image");
        String gender = request.getParameter("gender");

        boolean edited = false;

        if (firstname != null && !firstname.isEmpty()) {
            user.setFirstname(firstname);
            edited = true;
        }

        if (lastname != null && !lastname.isEmpty()) {
            user.setLastname(lastname);
            edited = true;
        }

        if (age != null && !age.isEmpty()) {
            user.setAge(age);
            edited = true;
        }

        if (image != null && !image.isEmpty()) {
            user.setImage(image);
            edited = true;
        }
        if (gender != null && !gender.isEmpty()) {
            user.setGender(gender);
            edited = true;
        }

        if (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty()) {
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

        request.setAttribute("genders", GENDERS);
        request.setAttribute("ages", AGES);
        request.setAttribute("images", IMAGES);

        navigate("/WEB-INF/editUser.jsp", request, response);
    }

    // Provided with reCaptcha implementation. May have been modified
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

    private final static int PASSWORD_ITERATIONS = 10;
    private final static int KEY_LENGTH = 256;

    public static byte[] hashPassword(final char[] password, final byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, PASSWORD_ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
