package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConexaoBanco {

    private static volatile Connection connection;

    private static final String BUNDLE_NAME = "config";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private static final String URL = getString("db.url");
    private static final String USER = getString("db.user");
    private static final String PASSWORD = getString("db.password");

    private static String getString(String key) {
        return bundle.getString(key);
    }

    public static Connection connect() {
        if (connection == null) {
            connection = establishConnection();
        }
        return connection;
    }

    private static Connection establishConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static void disconnect() {
        if (connection != null) {
            closeConnection();
        }
    }

    private static void closeConnection() {
        try {
            connection.close();
            connection = null;
            System.out.println("Connection closed successfully!");
        } catch (Exception e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }
}