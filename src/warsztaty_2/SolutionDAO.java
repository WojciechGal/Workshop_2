package warsztaty_2;

import org.apache.commons.lang3.ArrayUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SolutionDAO {

    private static final String CREATE_SOLUTION = "insert into solution values (null, now(), null, ?, ?, ?)";
    private static final String READ_SOLUTION_BY_ID = "select * from solution where id = ?";
    private static final String READ_SOLUTION_BY_DESCRIPTION = "select * from solution where description = ?";
    //dane rozwiązanie zawsze będzie należeć do danego użytkownika, można zmienić zapytanie
    private static final String CHANGE_SOLUTION_ATTRIBUTES = "update solution set description = ?, updated = now() where id = ?";
    private static final String DELETE_SOLUTION = "delete from solution where id = ?";
    private static final String READ_ALL_SOLUTIONS = "select * from solution";


    public Solution createSolution(Solution solution) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CREATE_SOLUTION, PreparedStatement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, solution.getDescription());
            preparedStatement.setInt(2, solution.getExercise().getId());
            preparedStatement.setInt(3, solution.getUser().getId());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Otrzymane ID: " + id);
                solution.setId(id);
            }

            solution.setCreated(readSolutionById(solution.getId()).getCreated());

            return solution;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution readSolutionById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_SOLUTION_BY_ID);) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    /////////////////mogą być nulle////////////////////////
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

                    return solution;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Solution readSolutionByDescription(String description) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_SOLUTION_BY_DESCRIPTION);) {

            preparedStatement.setString(1, description);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    /////////////////mogą być nulle////////////////////////
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

                    return solution;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateSolution(Solution solution) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(CHANGE_SOLUTION_ATTRIBUTES);) {

            preparedStatement.setString(1, solution.getDescription());

            preparedStatement.setInt(2, solution.getId());

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

    public void deleteSolutionById(int id) {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SOLUTION);) {

            preparedStatement.setInt(1, id);
            int del = preparedStatement.executeUpdate();

            if(del > 0) {
                System.out.println("Usunięto");
            } else {
                System.out.println("Nie ma takiego rozwiązania.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Solution[] readAllSolutions() {

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_ALL_SOLUTIONS);) {

            Solution[] solutions = new Solution[0];

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    Solution solution = new Solution();
                    solution.setId(resultSet.getInt("id"));
                    //////////mogą być nulle///////////
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
