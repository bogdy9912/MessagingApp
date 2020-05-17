package servlets;


import models.User;
import repositories.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String userNameParam = request.getParameter("name");
        try{
            List<User> userList = UserRepository.findByName(userNameParam);
            for (int i=0;i<userList.size();i++){
                response.getWriter().println(userList.get(i));
            }

        }catch(SQLException | IOException e){e.printStackTrace();}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* Map<String, String> map = new HashMap<>();

        String linie;

        while ((linie = request.getReader().readLine()) != null) {
            map.put(linie.split("=")[0], linie.split("=")[1]);
        }

        User user = new User(
                map.get("id"),
                map.get("nume"),
                map.get("password")

        );

        try {
            if (UserRepository.findByName(user.getName()) == null) {
                UserRepository.save(user);
                response.getWriter().println("Studentul a fost salvat");
            } else
                response.getWriter().println("Exista deja un student cu username-ul dat");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/



    }

}
