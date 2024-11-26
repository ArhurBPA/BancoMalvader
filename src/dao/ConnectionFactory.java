package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Dados para conexão com o banco
    private static final String url = "jdbc:mysql://localhost:3306/banco_malvader";
    private static final String usuario = "root";
    private static final String senha = "fFLUZAO2004.";

    // Metodo para conectar com o banco
    public static Connection getConnection() throws SQLException {
        try {
            // Registrar o driver manualmentev
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar o driver do MySQL!", e);
        }
        return DriverManager.getConnection(url, usuario, senha);
    }
}