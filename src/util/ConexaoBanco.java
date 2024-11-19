package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBanco {

    private static final Logger LOGGER = Logger.getLogger(ConexaoBanco.class.getName());
    private static volatile Connection connection;

    private static final String BUNDLE_NAME = "config";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private static final String URL = getString("db.url");
    private static final String USER = getString("db.user");
    private static final String PASSWORD = getString("db.password");

    private static String getString(String key) {
        return bundle.getString(key);
    }

    public static Connection conectar() {
        if (connection == null) {
            synchronized (ConexaoBanco.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        LOGGER.log(Level.INFO, "Conexão estabelecida com sucesso.");
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Erro ao conectar com o banco de dados: " + e.getMessage(), e);
                        throw new RuntimeException("Falha ao conectar ao banco de dados", e);
                    }
                }
            }
        }
        return connection;
    }

    public static void desconectar() {
        if (connection != null) {
            synchronized (ConexaoBanco.class) {
                if (connection != null) {
                    try {
                        connection.close();
                        connection = null;
                        LOGGER.log(Level.INFO, "Conexão fechada com sucesso.");
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), e);
                    }
                }
            }
        }
    }
}