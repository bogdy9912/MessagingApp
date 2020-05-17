package servlets;


import models.Message;
import repositories.GroupRepository;
import repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home_page")
public class HomePageServlet extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   String name = request.getParameter("userName");
   request.setAttribute("name", name);
   String groupId = request.getParameter("groupId");
   request.setAttribute("groupId", groupId);
   System.out.println(groupId);


/*
    try {
        request.setAttribute("messages", UserRepository.userMessages(name));
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }*/

    try {
        List<Message> groupMessages = GroupRepository.groupMessages(groupId);
        request.setAttribute("messages", groupMessages);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    try {
        request.setAttribute("groups", UserRepository.findGroupsUser(UserRepository.findByUserName(name).getId()));
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    request.getRequestDispatcher("home_page.jsp").forward(request, response);

}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         String name = request.getParameter("userName");
         String groupId = request.getParameter("groupId");


        System.out.println(name);
  /*      try {
            List<Message> groupMessages = GroupRepository.groupMessages(groupId);
            request.setAttribute("messages", groupMessages);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
*/

        // request.getRequestDispatcher("home_page.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/home_page?userName=" + name + "&groupId=" + groupId);

    }

}
