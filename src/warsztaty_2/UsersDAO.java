package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    private static final String CREATE_USER = "insert into users values (null, ?, ?, ?, ?)";
    private static final String READ_USER_BY_ID = "select * from users where id = ?";
    private static final String READ_USER_BY_EMAIL = "select * from users where email = ?";
    private static final String CHANGE_USER_ATTRIBUTES = "update users set name = ?, email = ?, password = ?, user_group_id = ? where id = ?";
    private static final String DELETE_USER = "delete from users where id = ?";
    private static final String READ_ALL_USERS = "select * from users";


    public Users createUser(Users user) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_USER, PreparedStatement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.setInt(4, user.getGroup().getId());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Otrzymane ID: " + id);
                user.setId(id);
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users readUserById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_USER_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Users user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));

                    int groupId = resultSet.getInt("user_group_id");
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = groupDAO.readGroupById(groupId);
                    user.setGroup(group);

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users readUserByEmail(String email) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_USER_BY_EMAIL);) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Users user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));

                    int groupId = resultSet.getInt("user_group_id");
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = groupDAO.readGroupById(groupId);
                    user.setGroup(group);

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(Users user) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_USER_ATTRIBUTES);) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.setInt(4, user.getGroup().getId());

            preparedStatement.setInt(5, user.getId());

            int edit = preparedStatement.executeUpdate();

            if (edit > 0) {
                System.out.println("Zmieniono.");
            } else {
                System.out.println("Nie ma tekigo użytkownika");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUserById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER);) {

            preparedStatement.setInt(1, id);
            int del = preparedStatement.executeUpdate();

            if(del > 0) {
                System.out.println("Usunięto");
            } else {
                System.out.println("Nie ma takiego użytkownika.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Users[] readAllUsers() {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_ALL_USERS);) {

            Users[] users = new Users[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Users user = new Users();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));

                    int groupId = resultSet.getInt("user_group_id");
                    GroupDAO groupDAO = new GroupDAO();
                    Group group = groupDAO.readGroupById(groupId);
                    user.setGroup(group);

                    users = ArrayUtils.add(users, user);
                }
                return users;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
