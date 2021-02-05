package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    // Connect to MySQL
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_hostName = "localhost";
    private static final String DB_Name = "jm_db";
    private static final String DB_connectionURL =
            "jdbc:mysql://" + DB_hostName + ":3306/"
            + DB_Name + "?useUnicode=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_connectionURL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }


/*    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";

        String dbName = "PROSELYTE_TUTORIALS";
        String userName = "root";
        String password = "12345";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        Class.forName("com.mysql.cj.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?useUnicode=true&serverTimezone=UTC";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }*/
}
