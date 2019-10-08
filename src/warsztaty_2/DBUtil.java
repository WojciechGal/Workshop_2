package warsztaty_2;

import java.sql.*;

public class DBUtil {


    private static final String DB_URL= "jdbc:mysql://localhost:3306/warsztat2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";

    private static final String DB_USER = "root";

    private static final String DB_PASS = "jurnyzbyszek";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

}
