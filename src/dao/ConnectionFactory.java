package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private final String url;
    private final String usuario;
    private final String senha;
    private static volatile ConnectionFactory instance;
    private Connection connection;

    private ConnectionFactory() {
        this.url = System.getenv("jdbc:mysql://localhost:3306/mydb");
        this.usuario = System.getenv("root");
        this.senha = System.getenv("fFLUZAO2004.");
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (ConnectionFactory.class) {
                if (instance == null) {
                    instance = new ConnectionFactory();
                }
            }
        }
        return instance;
    }

    public static Connection conectar() {
        synchronized (ConnectionFactory.class) {
            if (instance.connection == null) {
                try {
                    instance.connection = DriverManager.getConnection(instance.url, instance.usuario, instance.senha);
                    LOGGER.info("Conexão estabelecida com sucesso.");
                } catch (SQLException e) {
                    LOGGER.severe("Erro ao conectar com o banco de dados: " + e.getMessage());
                    throw new RuntimeException("Erro ao conectar com o banco de dados.", e);
                }
            }
            return instance.connection;
        }
    }

    public void desconectar() {
        synchronized (ConnectionFactory.class) {
            if (connection != null) {
                try {
                    connection.close();
                    connection = null;
                    LOGGER.info("Conexão fechada com sucesso.");
                } catch (SQLException e) {
                    LOGGER.severe("Erro ao desconectar do banco de dados: " + e.getMessage());
                    throw new RuntimeException("Erro ao desconectar do banco de dados.", e);
                }
            }
        }
    }
}