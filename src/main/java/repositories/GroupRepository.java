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
import java.util.Comparator;
import java.util.List;

public class GroupRepository {

    public static void save(Group group) throws SQLException {
        String sql = "INSERT INTO chatapp01.group(name) VALUES(?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, group.getName());

        preparedStatement.execute();


    }

    public static List<Group> findByName(String groupName) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM chatapp01.group WHERE name like '" + groupName + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        List<Group> groups = new ArrayList<>();

        while (resultSet.next()) {
            Group group = new Group(
                    resultSet.getString("id"),
                    resultSet.getString("name")
            );
            groups.add(group);

        }
        return groups;
    }
    public static Group findById(String groupId) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM chatapp01.group WHERE id like '" + groupId + "'";

        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            Group group = new Group(
                    resultSet.getString("id"),
                    resultSet.getString("name")
            );

            return group;
        }
        else
            return null;

    }

    public static List<User> findUsersGroup(String groupId) throws SQLException{

        List<UserGroup> usersGroup = UserGroupRepository.findAllUsersByGroupId(groupId);
        List<User> users = new ArrayList<>();
        for (UserGroup userGroup : usersGroup) {
            User user = UserRepository.findByUserId(userGroup.getIdUser());
            users.add(user);

        }

        return users;
    }

    public static List<Message> groupMessages(String groupId) throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        List<User> users = findUsersGroup(groupId);
        List<Message> allMessages = new ArrayList<>();
        for(User user : users){
            List<Message> userMessages = UserRepository.userMessages(user.getName());
           /* for (Message message : userMessages){
                allMessages.add(message);

            }*/
            allMessages.addAll(userMessages);

        }
     /*    for (Message message : allMessages){
             if (message.getIdGroup() != groupId && message != null)
                allMessages.remove(message);

            }*/
          allMessages.removeIf(message -> !(message.getIdGroup().equals(groupId) ));
        allMessages.sort(Comparator.comparing(Message::getDate).reversed());

        return allMessages;
    }


    public static void addMember(User user, Group group) throws SQLException{
        String sql = "INSERT INTO `chatapp01`.`user_group` (`idGroup`, `idUser`) VALUES (?, ?);";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, group.getId());
        preparedStatement.setString(2, user.getId());


        preparedStatement.execute();

    }


}
