import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserPage extends Page {
    public static void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/addUser.jsp", request, response);
    }
}
