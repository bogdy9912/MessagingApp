package models;

import repositories.MessageRepository;
import repositories.UserRepository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Message {
    private String id;
    private final String text;
    private final String idUser;
    private  String date;
    private final String idGroup;
    private String reacts;
    private List<User> usersWhoReact;

    public Message(String id, String text, String idUser, String date,String idGroup,String reacts) {
        this.id = id;
        this.text = text;
        this.idUser = idUser;
        this.date = date;
        this.idGroup = idGroup;
        this.reacts = reacts;
        usersWhoReact = new ArrayList<>();

    }

    public Message(String text, String idUser, String idGroup, String reacts) {
        this.text = text;
        this.idUser = idUser;
        this.idGroup = idGroup;
        this.reacts = reacts;
        usersWhoReact = new ArrayList<>();

    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getDate() {
        return date;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public String getReacts() {
        return reacts;
    }

    public List<User> getUsersWhoReact() {
        return usersWhoReact;
    }

    public void setReacts(List<User> users) {
        reacts = null;
        for(User user : users){
        if (reacts == null){
            reacts = user.getName();
        }
        else
        reacts += ", " + user.getName();

        }

    }

    public void setUsersWhoReact(String reacts) throws SQLException {

        String[] tokens = reacts.split(", ");

        for (String t : tokens)
            usersWhoReact.add(UserRepository.findByUserName(t));
    }
    public void addReact(User user){
        usersWhoReact.add(user);
    }
    public void deleteReact(User user){
        usersWhoReact.remove(user);
    }

    public boolean containsName(final List<User> list, final String userId){
        return list.stream().filter(o -> o.getId().equals(userId)).findFirst().isPresent();
    }


    public synchronized void removeUser(List<User> users, String userId) {


        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            String value = iterator.next().getId();
            if (value.equals(userId)) {
                iterator.remove();
            }

        }
    }

    public void toogleReact(User user){

        if (reacts != null)
        try {
            setUsersWhoReact(reacts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(usersWhoReact);
        if (user == null)
            System.out.println("User este null");
        else
        if (usersWhoReact.isEmpty()) {


            usersWhoReact.add(user);
        }
        else {
            boolean ans = containsName(usersWhoReact, user.getId());
        if (ans){

            //usersWhoReact.removeIf(user1 -> user1.getId().equals(user.getId()));
            removeUser(usersWhoReact,user.getId());
        }
        else {
            usersWhoReact.add(user);
        }
        }
        setReacts(usersWhoReact);
        try {
            MessageRepository.updateMessage(MessageRepository.findByMessageId(id), reacts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return id + ": " +
                text+ ", by: " + idUser
                + " at " + date + "in group: " + idGroup + "  " + reacts;
    }


}
