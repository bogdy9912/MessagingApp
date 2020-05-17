package servlets;

import models.User;
import repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;



@WebServlet("/register")
public class RegisterServlet extends HttpServlet {



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {

        String userCredentials = request.getReader().readLine();
        userCredentials = URLDecoder.decode(userCredentials);
        String userName = "";
        String userPassword = "";
        System.out.println(userCredentials);

        userName = userCredentials.split("&")[0];
        userPassword = userCredentials.split("&")[1];

        if (userName.split("=").length != 2 || userPassword.split("=").length != 2) {
            response.getWriter().println("Credentiale incomplete");
            //   request.getRequestDispatcher("login.jsp").forward(request, response);

        } else {
            userName = userName.split("=")[1];
            userPassword = userPassword.split("=")[1];



            System.out.println("USER NAME :  " + userName);
            System.out.println("USER PASS  :  " + userPassword);


            try {
                if (UserRepository.findByUserName(userName) == null) {
                    UserRepository.save(new User(userName, userPassword));
                    response.getWriter().println("Cont creat cu succes");
                } else {
                    response.getWriter().println("Cont deja existent");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
