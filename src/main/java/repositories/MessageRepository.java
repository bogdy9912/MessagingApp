package repositories;

import models.Message;
import models.User;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageRepository {


    public static void save(Message message) throws SQLException {
        String sql = "INSERT INTO message(text, idUser, date, idGroup) VALUES(?, ?, ?, ?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, message.getText());
        preparedStatement.setString(2, message.getIdUser());


        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");

        preparedStatement.setString(3, formatter.format(date));
        preparedStatement.setString(4, message.getIdGroup());

        preparedStatement.execute();
    }

    public static Message findByMessageId(String messageId) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM message WHERE id like '" + messageId + "'";

        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            Message message = new Message(
                    resultSet.getString("id"),
                    resultSet.getString("text"),
                    resultSet.getString("idUser"),
                    resultSet.getString("date"),
                    resultSet.getString("idGroup"),
                    resultSet.getString("react")

            );

            return message;
        } else
            return null;

    }

    public static void delete (String messageId) throws SQLException{
        String sql = "DELETE FROM `chatapp01`.`message` WHERE (`id` = '"+messageId+"') " +
                "and (`idUser` = '"+findByMessageId(messageId).getIdUser()+"') and (`idGroup` = '"+
                findByMessageId(messageId).getIdGroup()+"');";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.execute();
    }

    public static void updateMessage(Message message, String reacts) throws SQLException{
        System.out.println("IN UPDATE" + message.getReacts());
        String sql = "UPDATE `chatapp01`.`message` SET `react` = '"+reacts+"' WHERE (`id` = '"+
                message.getId()+"') and (`idUser` = '"+ message.getIdUser()+"') and (`idGroup` = '"+
                message.getIdGroup()+"');";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.execute();
    }



}
