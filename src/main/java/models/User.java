package models;

import repositories.FriendRepository;
import repositories.UserRepository;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {

    private  String id;
    private final String name;
    private final String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return name + ": " + id + ", " + password;
    }
/*
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
   /*     if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return user.id == id;
    }*/

    public void addFriend(User user) throws SQLException {
        String sql = "INSERT INTO friend(idUser, idFriend) VALUES(?, ?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, user.getId());

        preparedStatement.execute();

        preparedStatement.setString(2, id);
        preparedStatement.setString(1, user.getId());

        preparedStatement.execute();

    }


    public boolean checkFriend(User friend)throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM friend WHERE idUser like '" + id + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            if( friend.getId().equals(resultSet.getString("idFriend")))
                return true;

        }
        return false;
    }

    public boolean checkBestFriend(User friend)throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM friend WHERE idUser like '" + id + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            if( friend.getId().equals(resultSet.getString("idFriend")) && resultSet.getString("isBestFriend").equals("true"))
                return true;

        }
        return false;
    }

    public List<User> listBestFriends(User friend)throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM friend WHERE idUser like '" + id + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> friends = new ArrayList<>();

        while (resultSet.next()) {
            if( friend.getId().equals(resultSet.getString("idFriend")) && resultSet.getString("isBestFriend").equals("true"))
             {
                Friend newFriend = new Friend(
                        resultSet.getString("idUser"),
                        resultSet.getString("idFriend"),
                        resultSet.getString("isBestFriend")


                );
                friends.add(UserRepository.findByUserId(newFriend.getIdFriend()));

            }
            return friends;

        }
        return null;
    }



}
