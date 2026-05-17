package com.cloud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC Utility class for MySQL database connection
 */
public class DBUtil {
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/clouddb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";
    private static final String[] PASSWORDS = {"tiger", "password", "root", ""};

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (System.getenv("DB_URL") != null) {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        
        SQLException lastException = null;
        for (String pass : PASSWORDS) {
            try {
                return DriverManager.getConnection(URL, USERNAME, pass);
            } catch (SQLException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
