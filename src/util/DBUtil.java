package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil {

    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());

    // Método para obter uma conexão com o banco de dados (usa ConexaoBanco)
    public static Connection getConnection() {
        try {
            return ConexaoBanco.conectar();
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter conexão com o banco de dados: " + e.getMessage(), e);
            throw new RuntimeException("Erro ao obter conexão com o banco de dados", e);
        }
    }

    // Método para fechar a conexão com o banco de dados
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                ConexaoBanco.desconectar(); // Usa ConexaoBanco para fechar a conexão
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao fechar a conexão com o banco de dados: " + e.getMessage(), e);
        }
    }
}