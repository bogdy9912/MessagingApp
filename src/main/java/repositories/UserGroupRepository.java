package repositories;

import models.Group;
import models.User;
import models.UserGroup;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserGroupRepository {

    public static void save(UserGroup userGroup) throws SQLException {
        String sql = "INSERT INTO user_group (idGrup, idUser) VALUES(?, ?)";
        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, userGroup.getIdGroup());
        preparedStatement.setString(2, userGroup.getIdUser());

        preparedStatement.execute();
    }
    public static List<UserGroup> findAllUsersByGroupId(String groupId) throws SQLException{

        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user_group WHERE idGroup like '" + groupId + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        List<UserGroup> usersGroup = new ArrayList<>();

        while (resultSet.next()) {
            UserGroup userGroup = new UserGroup(
                    resultSet.getString("idGroup"),
                    resultSet.getString("idUser")
            );
            usersGroup.add(userGroup);

        }

        return usersGroup;
    }

    public static List<UserGroup> findAllGroupsByUserId(String userId) throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        String sql = "SELECT * FROM user_group WHERE idUser like '" + userId + "'";

        ResultSet resultSet = statement.executeQuery(sql);

        List<UserGroup> usersGroup = new ArrayList<>();

        while (resultSet.next()) {
            UserGroup userGroup = new UserGroup(
                    resultSet.getString("idGroup"),
                    resultSet.getString("idUser")
            );
            usersGroup.add(userGroup);

        }

        return usersGroup;

    }

}
