package repositories;

import models.Friend;
import models.Message;
import models.User;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendRepository {
    public static void updateFriend(Friend friend, String bool) throws SQLException{
        String sql = "UPDATE `chatapp01`.`friend` SET `isBestFriend` = '"+bool+"' WHERE (`idUser` = '"+friend.getIdUser()+
                "') and (`idFriend` = '"+friend.getIdFriend()+"');";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.execute();
    }

    public static Friend findByUserAndFriendIds(User user, User friend)throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql ="SELECT * FROM friend WHERE idUser like '"+user.getId()+"' and idFriend like '" + friend.getId()+"';";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            Friend newFriend = new Friend(
                    resultSet.getString("idUser"),
                    resultSet.getString("idFriend"),
                    resultSet.getString("isBestFriend")
            );

            return newFriend;
        } else
            return null;

    }

    public static void setBestFriend(User user, User friend) throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql ="SELECT * FROM friend WHERE idUser like '"+user.getId()+"' and idFriend like '" + friend.getId()+"';";
        ResultSet resultSet = statement.executeQuery(sql);


    }

}
