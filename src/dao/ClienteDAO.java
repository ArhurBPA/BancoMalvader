package dao;

import dao.ConnectionFactory;
import java.util.logging.Logger;

import java.sql.*;

import model.Cliente;

public class ClienteDAO {
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    public void cadastrarCliente(Cliente cliente) {
        String sqlUsuario = "INSERT INTO tb_usuario (no_usuario, nr_cpf_usuario, dt_nascimento, nr_telefone, senha, tp_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO tb_cliente (id_usuario) VALUES (?)";
        Connection connection = null; // Declarar Connection aqui para ter acesso em qualquer parte do método

        try {
            connection = ConnectionFactory.connect(); // Obter a conexão
            connection.setAutoCommit(false); // Inicia a transação

            // Inserir na tabela TB_USUARIO
            try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, cliente.getNome());
                stmtUsuario.setString(2, cliente.getCpf());
                stmtUsuario.setDate(3, Date.valueOf(cliente.getDataNascimento()));
                stmtUsuario.setString(4, cliente.getTelefone());
                stmtUsuario.setString(5, cliente.getSenha());
                stmtUsuario.setString(6, "CLIENTE"); // Define o tipo de usuário como CLIENTE
                stmtUsuario.executeUpdate();

                // Obter o ID gerado para o usuário
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1)); // Define o ID do cliente a partir do ID gerado para o
                    // usuário
                }
            }

            // Inserir na tabela TB_CLIENTE
            try (PreparedStatement stmtCliente = connection.prepareStatement(sqlCliente)) {
                stmtCliente.setInt(1, cliente.getId());
                stmtCliente.executeUpdate();
            }

            connection.commit(); // Confirma a transação
            LOGGER.info("Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
            LOGGER.severe("Erro ao cadastrar cliente: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Em caso de erro, desfaz a transação
                } catch (SQLException rollbackEx) {
                    LOGGER.severe("Erro ao desfazer transação: " + rollbackEx.getMessage());
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close(); // Garante que a conexão seja fechada, mesmo em caso de erro
                } catch (SQLException e) {
                    LOGGER.severe("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    // Método para buscar um cliente por ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM tb_cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getString("NO_USUARIO"),
                            rs.getString("NR_CPF_USUARIO"),
                            rs.getDate("DT_NASCIMENTO").toLocalDate(),
                            rs.getString("NR_TELEFONE"),
                            null, // Endereço pode ser buscado de outra tabela se necessário
                            rs.getString("SENHA"));
                    cliente.setId(rs.getInt("id_cliente"));
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Erro ao buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    // Método para atualizar os dados de um cliente
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE tb_cliente SET no_usuario = ?, nr_cpf_usuario = ?, dt_nascimento = ?, nr_telefone = ?, senha = ? WHERE id_cliente = ?";
        boolean atualizado = false;

        try (Connection conn = util.ConexaoBanco.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getSenha());
            stmt.setInt(6, cliente.getId());

            atualizado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Erro ao atualizar cliente: " + e.getMessage());
        }
        return atualizado;
    }

    // Método para deletar um cliente
    public boolean deletar(int id) {
        String sql = "DELETE FROM tb_cliente WHERE id_cliente = ?";
        boolean deletado = false;

        try (Connection conn = util.ConexaoBanco.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            deletado = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Erro ao deletar cliente: " + e.getMessage());
        }
        return deletado;
    }
}