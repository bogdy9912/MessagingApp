package models;

import repositories.FriendRepository;

import java.sql.SQLException;

public class Friend {
    private final String idUser;
    private final String idFriend;
    private String isBestFriend;

    public Friend(String idUser, String idFriend, String isBestFriend) {
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.isBestFriend = isBestFriend;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdFriend() {
        return idFriend;
    }

    public String isBestFriend() {
        return isBestFriend;
    }

    public void toogleIsBestFriend(){
        if (isBestFriend.equals("false"))
            isBestFriend="true";
        else
            isBestFriend="false";
        try {
            FriendRepository.updateFriend(this,isBestFriend);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
