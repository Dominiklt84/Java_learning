package lt.viko.eif.dalencinovic;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JettyHTMLServletForms extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<html><form>");
        out.println("<label for=\"fname\">First name:</label><br>");
        out.println("<input type=\"text\" id=\"fname\" name=\"fname\"><br>");
        out.println("<label for=\"lname\">Last name:</label><br><br>");
        out.println("<input type=\"text\" id=\"lname\" name=\"lname\">");
        out.println(" <input type=\"submit\" value=\"Submit\">");
        out.println("</form></html>");
    }
}
