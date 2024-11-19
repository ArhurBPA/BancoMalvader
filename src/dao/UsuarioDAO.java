package dao;

import java.sql.*;
import model.*;

public class UsuarioDAO{

    private Connection connection;

    public UsuarioDAO() {
        connection = ConnectionFactory.conectar(); // Conexão com o banco de dados
    }

    // Método para autenticar o usuário no banco de dados
    public Usuario autenticarUsuario(String usuario, String senha, String tipoUsuario) {
        String sql = "SELECT * FROM Usuario WHERE NO_USUARIO = ? AND SENHA = ? AND TP_USUARIO = ?";
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario); // Define o nome de usuário
            stmt.setString(2, senha); // Define a senha
            stmt.setString(3, tipoUsuario); // Define o tipo de usuário (Funcionario ou Cliente)

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Se encontrar o usuário no banco de dados, cria o objeto Usuario
                return new Usuario(
                        rs.getInt("ID_USUARIO"),
                        rs.getString("NO_USUARIO"),
                        rs.getString("NR_CPF_USUARIO"),
                        rs.getDate("DT_NASCIMENTO").toLocalDate(),
                        rs.getString("NR_TELEFONE"),
                        rs.getString("SENHA"),
                        rs.getString("TP_USUARIO")) {
                    @Override
                    public boolean login(String senha1) {
                        return false;
                    }

                    @Override
                    public void logout() {

                    }

                    @Override
                    public String consultarDados() {
                        return "";
                    }
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // Se não encontrar o usuário
    }
}