package dao;

import model.Usuario;
import model.Funcionario;
import model.Cliente;

import java.sql.*;
import java.util.logging.Logger;

public class UsuarioDAO {
    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    public void save(Usuario usuario) {
        String sql = "INSERT INTO tb_usuario (NO_USUARIO, NR_CPF_USUARIO, DT_NASCIMENTO, NR_TELEFONE, SENHA, TP_USUARIO) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = ConnectionFactory.getInstance().conectar();

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setDate(3, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getTipoUsuario());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o usuário, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                    if (usuario instanceof Funcionario) {
                        saveFuncionario((Funcionario) usuario, conn);
                    } else if (usuario instanceof Cliente) {
                        saveCliente((Cliente) usuario, conn);
                    }
                } else {
                    throw new SQLException("Falha ao salvar o usuário, não foi possível obter o ID.");
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error while saving the user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionFactory.getInstance().desconectar();
        }
    }

    private void saveFuncionario(Funcionario funcionario, Connection conn) throws SQLException {
        String sql = "INSERT INTO tb_funcionario (ID_USUARIO, CD_FUNCIONARIO, NO_CARGO) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getId());
            stmt.setString(2, funcionario.getCodigoFuncionario());
            stmt.setString(3, funcionario.getCargo());
            stmt.executeUpdate();
        }
    }

    private void saveCliente(Cliente cliente, Connection conn) throws SQLException {
        String sql = "INSERT INTO tb_cliente (ID_USUARIO) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            stmt.executeUpdate();
        }
    }
}