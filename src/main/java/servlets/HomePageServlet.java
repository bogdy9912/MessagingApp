package servlets;


import models.Friend;
import models.Group;
import models.Message;
import models.User;
import repositories.FriendRepository;
import repositories.GroupRepository;
import repositories.MessageRepository;
import repositories.UserRepository;
import utils.DbConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet("/home_page")
public class HomePageServlet extends HttpServlet {

    private void sendText(HttpServletRequest request, HttpServletResponse response, String userId,String groupId)throws IOException, ServletException{
        String textMessage = null;

        textMessage = request.getParameter("textMessage");

        if (textMessage != null) {
            //   textMessage = URLDecoder.decode(textMessage);

    /*        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());*/
            try {
                MessageRepository.save(new Message(textMessage, userId, groupId, null));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
          /*  if (textMessage.split("=").length == 2) {
                textMessage = textMessage.split("=")[1];
                try {
                    MessageRepository.save(new Message(textMessage, userId, formatter.format(date), groupId));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }*/
        }
    }

    private void deleteMessage(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

        String deleteMessage = null;

        deleteMessage = request.getParameter("messageDelete");
        if (deleteMessage != null) {
            try {
                MessageRepository.delete(deleteMessage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    private void reactMessage(HttpServletRequest request, HttpServletResponse response, String name) throws IOException, ServletException, SQLException {
        String actionMessage = request.getParameter("messageReplyAndReact");

        if (actionMessage != null) {

            String handler = request.getParameter("react");
            String textReply = request.getParameter("inputReply");
            if (handler != null)
            if (handler.equals("React") && textReply.equals("")) {
                try {
                    Objects.requireNonNull(MessageRepository.findByMessageId(actionMessage)).toogleReact(
                            UserRepository.findByUserName(name)
                    );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                MessageRepository.updateMessage(MessageRepository.findByMessageId(actionMessage),
                        MessageRepository.findByMessageId(actionMessage).getReacts());

            }
        }
    }

    private void replyMessage(User user,Group group,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String actionMessage = null;
        actionMessage = request.getParameter("messageReplyAndReact");
        if (actionMessage != null) {
            String handler = null;
            handler = request.getParameter("reply");
            if (handler !=null)
            if (handler.equals("Reply")) {
                String textReply = request.getParameter("inputReply");
                if(textReply != null){
                    try {
                        MessageRepository.save(new Message("Original message: "+MessageRepository.findByMessageId(actionMessage).getText() +"\n Reply: "+ textReply,
                                user.getId(),group.getId(),null));
                        System.out.println(new Message("Original message: "+MessageRepository.findByMessageId(actionMessage).getText() +"\n Reply: "+ textReply,
                                user.getId(),group.getId(),null));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
        }
    }}

    public void createGroup(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        String groupName = request.getParameter("newGroup");
        if (groupName != null) {
            try {
                GroupRepository.save(new Group(groupName));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                GroupRepository.addMember(user, GroupRepository.findByName(groupName).get(0));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addPeopleInGrorp(User currentUser,String groupId,HttpServletRequest request)throws IOException,ServletException {
        String newUser = request.getParameter("newMember");
        if (newUser != null) {
            try {
                if (currentUser.checkFriend(UserRepository.findByUserName(newUser)) == false){
                    System.out.println("Nu esti prieten cu persoana: " + newUser);
                }
                else
                try {
                    if (GroupRepository.findIfUserIsInGroup(groupId,UserRepository.findByUserName(newUser).getId()) != true)
                    {
                        GroupRepository.addMember(UserRepository.findByUserName(newUser), GroupRepository.findById(groupId));

                    }
                    else{
                        System.out.println("Persona: " + newUser +" este deja in grup");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void addFriend(User user,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String newFriend = request.getParameter("newFriend");
        if(newFriend != null){
            try {
                if(user.checkFriend(UserRepository.findByUserName(newFriend))){
                    response.getWriter().println("Sunteti deja prieteni");
                    System.out.println("Sunteti deja prieteni");
                }
                else
                try {
                    user.addFriend(UserRepository.findByUserName(newFriend));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void showRaportNrMessages(User user,HttpServletRequest request) throws IOException, ServletException, SQLException {
        request.setAttribute("nrMessages", UserRepository.userMessages(user.getName()).size());
    }

    public void addBestFriend(User user,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String newBestFriend = request.getParameter("newBestFriend");

        if(newBestFriend != null){
            try {
                if ( request.getParameter("addBestFriend") != null && request.getParameter("addBestFriend").equals(" ADD BestFriend")) {
                    if (!user.checkFriend(UserRepository.findByUserName(newBestFriend))) {
                        response.getWriter().println("Nu ai niciun prieten pe numele: " + newBestFriend);
                        System.out.println("Nu ai niciun prieten pe numele: " + newBestFriend);
                    } else if (user.checkBestFriend(UserRepository.findByUserName(newBestFriend))) {
                        response.getWriter().println("E deja la bestfriends");
                        System.out.println("E deja la bestfriends");
                    } else {
                        FriendRepository.findByUserAndFriendIds(user, UserRepository.findByUserName(newBestFriend)).toogleIsBestFriend();
                    }
                }
                else{
                    if(request.getParameter("removeBestFriend").equals(" Remove BestFriend")){
                        if (user.checkBestFriend(UserRepository.findByUserName(newBestFriend))) {
                            FriendRepository.findByUserAndFriendIds(user, UserRepository.findByUserName(newBestFriend)).toogleIsBestFriend();
                        }
                        else{
                            response.getWriter().println("Nu ai niciun BestFriend pe numele: " + newBestFriend);
                            System.out.println("Nu ai niciun BestFriend pe numele: " + newBestFriend);
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void showRaportBestFriends(User user,HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException, SQLException{


        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM friend WHERE idUser like '" + user.getId() + "' and isBestFriend like 'true'";
        ResultSet resultSet = statement.executeQuery(sql);
        List<String> friends = new ArrayList<>();
        List<User> friendsUser = new ArrayList<>();
        if(resultSet != null ) {
            while (resultSet.next()) {
                friends.add(resultSet.getString("idFriend")) ;
                friendsUser.add(UserRepository.findByUserId(resultSet.getString("idFriend")));
                //request.setAttribute("ListOfBestFriends", user.listBestFriends(UserRepository.findByUserId(resultSet.getString("idFriend"))));
            }
        }
        request.setAttribute("ListOfBestFriends",friendsUser);
    }


public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   String name = request.getParameter("userName");
   request.setAttribute("name", name);
   String groupId = request.getParameter("groupId");
   request.setAttribute("groupId", groupId);

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
    try {
        showRaportNrMessages(UserRepository.findByUserName(name),request);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    try {
        showRaportBestFriends(UserRepository.findByUserName(name),request,response);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    request.getRequestDispatcher("home_page.jsp").forward(request, response);

}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
         String name = request.getParameter("userName");
         String groupId = request.getParameter("groupId");

        String userId = null;

        if (name != null){
        try {
            User userHandle = UserRepository.findByUserName(name);
            if (userHandle != null){
             userId = userHandle.getId();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        }


        sendText(request,response,userId,groupId);

        deleteMessage(request,response);

        try {
            reactMessage(request,response,name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            createGroup(request,response, UserRepository.findByUserId(userId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            addPeopleInGrorp(UserRepository.findByUserName(name),groupId,request);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            addFriend(UserRepository.findByUserName(name),request,response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            replyMessage(UserRepository.findByUserName(name),GroupRepository.findById(groupId),request,response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            addBestFriend(UserRepository.findByUserName(name),request,response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        response.sendRedirect(request.getContextPath() +
                "/home_page?userName=" + name + "&groupId=" + groupId);

    }

}
