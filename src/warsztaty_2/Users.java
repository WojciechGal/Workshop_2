package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    private int id;
    private String name;
    private String email;
    private String password;
    private Group group;

    public Users(String name, String email, String password, Group group) {
        this.name = name;
        this.email = email;
        hashPassword(password);
        this.group = group;
    }

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    ////////metoda podobnie jak te w Solution będzie przyjmowała parametry/////////

    private static final String FIND_USERS_BY_GROUP_ID = "select * from users where user_group_id = ?";

    public Users[] findAllByGroupId(int id) {
 //       int id = getGroup().getId();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_USERS_BY_GROUP_ID);) {

            preparedStatement.setInt(1, id);

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
