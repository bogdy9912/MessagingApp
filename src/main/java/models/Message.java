package models;

public class Message {
    private String id;
    private final String text;
    private final String idUser;

    public Message(String id, String text, String idUser) {
        this.id = id;
        this.text = text;
        this.idUser = idUser;
    }

    public Message(String text, String idUser) {
        this.text = text;
        this.idUser = idUser;
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
    @Override
    public String toString() {
        return id + ": " + text+ ", by: " + idUser;
    }

}
