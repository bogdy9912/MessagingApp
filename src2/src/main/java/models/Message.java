package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String id;
    private final String text;
    private final String idUser;
    private final String date;
    private final String idGroup;

    public Message(String id, String text, String idUser, String date,String idGroup) {
        this.id = id;
        this.text = text;
        this.idUser = idUser;
        this.date = date;
        this.idGroup = idGroup;
    }

    public Message(String text, String idUser, String date, String idGroup) {
        this.text = text;
        this.idUser = idUser;
        this.date = date;
        this.idGroup = idGroup;
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

    @Override
    public String toString() {
        return id + ": " +
                text+ ", by: " + idUser
                + " at " + date + "in group: " + idGroup;
    }

}
