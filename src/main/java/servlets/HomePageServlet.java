package servlets;


import models.Group;
import models.Message;
import models.User;
import repositories.GroupRepository;
import repositories.MessageRepository;
import repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet("/home_page")
public class HomePageServlet extends HttpServlet {

    private void sendText(HttpServletRequest request, HttpServletResponse response, String userId,String groupId)throws IOException, ServletException{
        String textMessage = null;

        textMessage = request.getParameter("textMessage");
        System.out.println(textMessage);
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
        System.out.println("Id mesaj este " + actionMessage);
        System.out.println("Id utilixator " + UserRepository.findByUserName(name));
        if (actionMessage != null) {

            String handler = request.getParameter("react");
            if (handler.equals("React")) {
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

    private void replyMessage(HttpServletRequest request, HttpServletResponse response, String name) throws IOException, ServletException{
        String actionMessage = null;
        actionMessage = request.getParameter("messageReplyAndReact");
        if (actionMessage != null) {

            String handler = null;
            handler = request.getParameter("reply");
            if (handler.equals("Reply")) {
                try {
                    MessageRepository.findByMessageId(actionMessage).toogleReact(
                            UserRepository.findByUserName(name)
                    );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
    }

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

    public void addPeopleInGrorp(String groupId,HttpServletRequest request)throws IOException,ServletException {
        String newUser = request.getParameter("newMember");
        if (newUser != null) {
            try {
                GroupRepository.addMember(UserRepository.findByUserName(newUser), GroupRepository.findById(groupId));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
   String name = request.getParameter("userName");
   request.setAttribute("name", name);
   String groupId = request.getParameter("groupId");
   request.setAttribute("groupId", groupId);
/*
System.out.println(groupId);
System.out.println(request.getAttribute(name));*/

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

         System.out.println("Imediat ce am citit parametru userName: " + name);

         String groupId = request.getParameter("groupId");
        String userId = null;
     //   System.out.println("Name is " + name);
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
    //    System.out.println(userId);

        sendText(request,response,userId,groupId);
        /*
        String textMessage = null;

         textMessage = request.getParameter("textMessage");
        System.out.println(textMessage);
       if (textMessage != null) {
         //   textMessage = URLDecoder.decode(textMessage);

    /*        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());*/
        /*   try {
               MessageRepository.save(new Message(textMessage, userId, groupId, null));
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }*/
          /*  if (textMessage.split("=").length == 2) {
                textMessage = textMessage.split("=")[1];
                try {
                    MessageRepository.save(new Message(textMessage, userId, formatter.format(date), groupId));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }*/
    //    }


    //    System.out.println(name);

  /*      try {
            List<Message> groupMessages = GroupRepository.groupMessages(groupId);
            request.setAttribute("messages", groupMessages);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
*/
        deleteMessage(request,response);
/*
        String deleteMessage = null;

        deleteMessage = request.getParameter("messageDelete");
        if (deleteMessage != null) {
            try {
                MessageRepository.delete(deleteMessage);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

*/

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


        addPeopleInGrorp(groupId,request);
        /*

        String actionMessage = null;
        actionMessage = request.getParameter("messageReplyAndReact");
        if(actionMessage != null){

            String handler = null;
            handler = request.getParameter("react");
            if(handler.equals("React"))
            {
                try {
                    MessageRepository.findByMessageId(actionMessage).toogleReact(
                            UserRepository.findByUserName(name)
                    );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

            else {
            handler = request.getParameter("reply");

            if(handler.equals("Reply")){
            // aici functia de reply
            }
            }

            //
        }
*/
        // request.getRequestDispatcher("home_page.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() +
                "/home_page?userName=" + name + "&groupId=" + groupId);

    }

}
