package servlets;


import repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/home_page")
public class HomePageServlet extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   String name = request.getParameter("userName");
   request.setAttribute("name", name);



    try {
        request.setAttribute("messages", UserRepository.userMessages(name));
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    request.getRequestDispatcher("home_page.jsp").forward(request, response);

}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // String name = req.getParameter("name");
response.getWriter().println("hollaaa");
       // request.getRequestDispatcher("home_page.jsp").forward(request, response);


    }

}
