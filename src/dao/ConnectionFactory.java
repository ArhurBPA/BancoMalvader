package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    static Connection connection = null;

    // Método para obter a conexão com o banco de dados
    public static Connection conectar() {
        try {
            if (connection == null || connection.isClosed()) {
                loadDatabaseCredentials();
                // Conectar ao banco de dados
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar com o banco de dados.");
        }
        return connection;
    }

    // Método para fechar a conexão com o banco de dados
    public static void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão encerrada com sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao fechar a conexão com o banco de dados.");
        }
    }

    // Método para verificar se a conexão está ativa
    public static boolean isConectado() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Método para carregar as credenciais do banco de dados de um arquivo de propriedades
private static void loadDatabaseCredentials() {
    Properties properties = new Properties();
    try (InputStream input = new FileInputStream("db.properties")) {
        properties.load(input);
        URL = properties.getProperty("db.url");
        USER = properties.getProperty("db.user");
        PASSWORD = properties.getProperty("db.password");
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Erro ao carregar as credenciais do banco de dados.");
    }
}

    public static Connection connect() {
        return null;
    }
}