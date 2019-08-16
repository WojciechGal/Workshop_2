package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseDAO {

    private static final String CREATE_EXERCISE = "insert into exercise values (null, ?, ?)";
    private static final String READ_EXERCISE_BY_ID = "select * from exercise where id = ?";
    private static final String READ_EXERCISE_BY_TITLE = "select * from exercise where title = ?";
    private static final String CHANGE_EXERCISE_ATTRIBUTES = "update exercise set title = ?, description = ? where id = ?";
    private static final String DELETE_EXERCISE = "delete from exercise where id = ?";
    private static final String READ_ALL_EXERCISES = "select * from exercise";


    public Exercise createExercise(Exercise exercise) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_EXERCISE, PreparedStatement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, exercise.getTitle());
            preparedStatement.setString(2, exercise.getDescription());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Otrzymane ID: " + id);
                exercise.setId(id);
            }

            return exercise;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Exercise readExerciseById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_EXERCISE_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Exercise exercise = new Exercise();
                    exercise.setId(resultSet.getInt("id"));
                    exercise.setTitle(resultSet.getString("title"));
                    exercise.setDescription(resultSet.getString("description"));
                    return exercise;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Exercise readExerciseByTitle(String title) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_EXERCISE_BY_TITLE);) {

            preparedStatement.setString(1, title);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Exercise exercise = new Exercise();
                    exercise.setId(resultSet.getInt("id"));
                    exercise.setTitle(resultSet.getString("title"));
                    exercise.setDescription(resultSet.getString("description"));
                    return exercise;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateExercise(Exercise exercise) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_EXERCISE_ATTRIBUTES);) {

            preparedStatement.setString(1, exercise.getTitle());
            preparedStatement.setString(2, exercise.getDescription());

            preparedStatement.setInt(3, exercise.getId());

            int edit = preparedStatement.executeUpdate();

            if (edit > 0) {
                System.out.println("Zmieniono.");
            } else {
                System.out.println("Nie ma takiego zadania.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteExerciseById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_EXERCISE);) {

            preparedStatement.setInt(1, id);
            int del = preparedStatement.executeUpdate();

            if(del > 0) {
                System.out.println("Usunięto");
            } else {
                System.out.println("Nie ma takiego zadania.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Exercise[] readAllExercises() {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_ALL_EXERCISES);) {

            Exercise[] exercises = new Exercise[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Exercise exercise = new Exercise();
                    exercise.setId(resultSet.getInt("id"));
                    exercise.setTitle(resultSet.getString("title"));
                    exercise.setDescription(resultSet.getString("description"));
                    exercises = ArrayUtils.add(exercises, exercise);
                }
                return exercises;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

}
