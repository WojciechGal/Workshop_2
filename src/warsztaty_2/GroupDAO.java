package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDAO {

    private static final String CREATE_GROUP = "insert into user_group values (null, ?)";
    private static final String READ_GROUP_BY_ID = "select * from user_group where id = ?";
    private static final String READ_GROUP_BY_NAME = "select * from user_group where name = ?";
    private static final String CHANGE_GROUP_ATTRIBUTES = "update user_group set name = ? where id = ?";
    private static final String DELETE_GROUP = "delete from user_group where id = ?";
    private static final String READ_ALL_GROUPS = "select * from user_group";


    public Group createGroup(Group group) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_GROUP, PreparedStatement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, group.getName());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Otrzymane ID: " + id);
                group.setId(id);
            }

            return group;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Group readGroupById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_GROUP_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("id"));
                    group.setName(resultSet.getString("name"));
                    return group;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Group readGroupByName(String name) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_GROUP_BY_NAME);) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("id"));
                    group.setName(resultSet.getString("name"));
                    return group;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateGroup(Group group) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_GROUP_ATTRIBUTES);) {

            preparedStatement.setString(1, group.getName());

            preparedStatement.setInt(2, group.getId());

            int edit = preparedStatement.executeUpdate();

            if (edit > 0) {
                System.out.println("Zmieniono.");
            } else {
                System.out.println("Nie ma takiej grupy.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteGroupById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_GROUP);) {

            preparedStatement.setInt(1, id);
            int del = preparedStatement.executeUpdate();

            if(del > 0) {
                System.out.println("Usunięto.");
            } else {
                System.out.println("Nie ma takiej grupy.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Group[] readAllGroups() {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_ALL_GROUPS);) {

            Group[] groups = new Group[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Group group = new Group();
                    group.setId(resultSet.getInt("id"));
                    group.setName(resultSet.getString("name"));
                    groups = ArrayUtils.add(groups, group);
                }
                return groups;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
