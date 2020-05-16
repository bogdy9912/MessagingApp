package repositories;

import models.Group;
import models.Message;
import models.User;
import models.UserGroup;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

/*
    public static void save(User user) throws SQLException {
        String sql = "INSERT INTO user(id, name, password) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
         preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.execute();
    }
    */

    public static void save(User user) throws SQLException {
        String sql = "INSERT INTO user(name, password) VALUES(?, ?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());

        preparedStatement.execute();
    }

    //  INSERT INTO `chatapp01`.`user` (`name`, `password`) VALUES ('C', 'c');

    public static List<User> findByName(String userName) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user WHERE name like '" + userName + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password")
            );
            users.add(user);

        }
        return users;
    }

    public static User findByUserName(String userName) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user WHERE name like '" + userName + "'";

        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password")
            );

            return user;
        } else
            return null;

    }

    public static List<Group> findGroupsUser(String userId)throws SQLException{
        List <UserGroup> groupsUser = UserGroupRepository.findAllGroupsByUserId(userId);
        List<Group> groups = new ArrayList<>();
        for (UserGroup groupUser : groupsUser) {
            Group group = GroupRepository.findById(groupUser.getIdGroup());
            groups.add(group);
        }
        return groups;
    }

    public static User findByUserId(String userId) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user WHERE id like '" + userId + "'";

        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password")
            );

            return user;
        } else
            return null;

    }


    public static User userLogin(String userName, String userPassword) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user WHERE name like '" + userName + "'" + " and password like '" + userPassword + "'";

        ResultSet resultSet = statement.executeQuery(sql);
        //   System.out.print("SQL PROLEMATIC");
        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("password")
            );
            return user;
        } else
            return null;

    }

    public static List<Message> userMessages(String userName) throws SQLException {
        User user = findByUserName(userName);
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM message WHERE idUser like '" + user.getId() + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        List<Message> messages = new ArrayList<>();

        while (resultSet.next()) {
            Message message = new Message(
                    resultSet.getString("id"),
                    resultSet.getString("text"),
                    resultSet.getString("idUser"),
                    resultSet.getString("date"),
                    resultSet.getString("idGroup")

            );
            messages.add(message);

        }
        return messages;
    }
}