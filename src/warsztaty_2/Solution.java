package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private Users user;

    ////////////konstruktor dla administratora////////////////////////////
    public Solution(Exercise exercise, Users user) {
        this.exercise = exercise;
        this.user = user;
    }

    /////////////konstruktor dla użytkownika////////////////
    public Solution(String description, Exercise exercise, Users user) {
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public Solution() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}';
    }


    //można zmodyfikować jakie parametry ma metoda przyjmować,
    //można przepisać żeby nie przyjmowała parametrów i robiłą się na podstawie obiektu

    private static final String FIND_SOLUTIONS_BY_USER_ID = "select * from solution where users_id = ?";

    public Solution[] findAllByUserId(int id) {
//        int id = getUser().getId();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_SOLUTIONS_BY_USER_ID);) {

            preparedStatement.setInt(1, id);

            Solution[] solutions = new Solution[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    solution.setCreated(resultSet.getDate("created"));
                    solution.setUpdated(resultSet.getDate("updated"));
                    solution.setDescription(resultSet.getString("description"));

                    int exerciseId = resultSet.getInt("exercise_id");
                    ExerciseDAO exerciseDAO = new ExerciseDAO();
                    Exercise exercise = exerciseDAO.readExerciseById(exerciseId);
                    solution.setExercise(exercise);

                    int userId = resultSet.getInt("users_id");
                    UsersDAO usersDAO = new UsersDAO();
                    Users users = usersDAO.readUserById(userId);
                    solution.setUser(users);

                    solutions = ArrayUtils.add(solutions, solution);
                }
                return solutions;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    private static final String FIND_SOLUTIONS_BY_EXERCISE_ID = "select * from solution where exercise_id = ?";

    public Solution[] findAllByExerciseId(int id) {
 //       int id = getExercise().getId();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_SOLUTIONS_BY_EXERCISE_ID);) {

            preparedStatement.setInt(1, id);

            Solution[] solutions = new Solution[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    solution.setCreated(resultSet.getDate("created"));
                    solution.setUpdated(resultSet.getDate("updated"));
                    solution.setDescription(resultSet.getString("description"));

                    int exerciseId = resultSet.getInt("exercise_id");
                    ExerciseDAO exerciseDAO = new ExerciseDAO();
                    Exercise exercise = exerciseDAO.readExerciseById(exerciseId);
                    solution.setExercise(exercise);

                    int userId = resultSet.getInt("users_id");
                    UsersDAO usersDAO = new UsersDAO();
                    Users users = usersDAO.readUserById(userId);
                    solution.setUser(users);

                    solutions = ArrayUtils.add(solutions, solution);
                }
                return solutions;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
