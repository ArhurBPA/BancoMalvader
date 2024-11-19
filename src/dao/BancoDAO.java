package dao;

import model.Cliente;
import model.Transacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    private final Cliente cliente;
    private final Connection connection = ConnectionFactory.conectar();
    private final int idConta;  // Variável de instância para armazenar o id_conta

    public BancoDAO(Cliente cliente) throws SQLException {
        this.cliente = cliente;
        this.idConta = buscarIdConta();  // Inicializa o idConta ao criar a instância do DAO
    }

    // Método para buscar o id_conta
    private int buscarIdConta() throws SQLException {
        String query = "SELECT c.id_conta " +
                "FROM conta c " +
                "JOIN cliente cl ON c.id_cliente = cl.id_cliente " +
                "WHERE cl.id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_conta");
            } else {
                throw new SQLException("Conta não encontrada para o cliente.");
            }
        }
    }

    public double consultarSaldo() throws SQLException {
        String query = "SELECT c.saldo FROM conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente WHERE cl.id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            } else {
                throw new SQLException("Cliente não encontrado.");
            }
        }
    }

    public void realizarDeposito(double valor) throws SQLException {
        connection.setAutoCommit(false); // Desabilita auto-commit para transações manuais

        String updateSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo + ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";
        try (PreparedStatement stmtSaldo = connection.prepareStatement(updateSaldo);
             PreparedStatement stmtTransacao = connection.prepareStatement(inserirTransacao)) {

            // Atualiza saldo
            stmtSaldo.setDouble(1, valor);
            stmtSaldo.setInt(2, cliente.getId());
            int linhasAfetadas = stmtSaldo.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma linha foi atualizada. Verifique o ID do cliente.");
            }

            // Insere transação usando o idConta armazenado no construtor
            stmtTransacao.setString(1, "deposito");
            stmtTransacao.setDouble(2, valor);
            stmtTransacao.setInt(3, idConta);
            int transacaoAfetada = stmtTransacao.executeUpdate();

            if (transacaoAfetada > 0) {
                System.out.println("Transação realizada com sucesso. ID da Conta: " + idConta);
            } else {
                throw new SQLException("Erro ao inserir a transação.");
            }

            connection.commit(); // Confirma a transação

        } catch (SQLException e) {
            connection.rollback(); // Reverte a transação em caso de erro
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true); // Restaura o auto-commit padrão
        }
    }

    public void realizarSaque(double valor) throws SQLException {
        String updateSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo - ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";
        try (PreparedStatement stmtSaldo = connection.prepareStatement(updateSaldo);
             PreparedStatement stmtTransacao = connection.prepareStatement(inserirTransacao)) {

            // Atualiza saldo
            stmtSaldo.setDouble(1, valor);
            stmtSaldo.setInt(2, idConta);
            stmtSaldo.executeUpdate();

            // Insere transação
            stmtTransacao.setString(1, "saque");
            stmtTransacao.setDouble(2, valor);
            stmtTransacao.setInt(3, idConta);
            stmtTransacao.executeUpdate();
        }
    }

    public List<Transacao> obterTransacoes() throws SQLException {
        String query = "SELECT tipo_transacao, valor FROM transacao WHERE id_conta = ? ORDER BY id_transacao DESC";
        List<Transacao> transacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idConta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tipo = rs.getString("tipo_transacao");
                double valor = rs.getDouble("valor");
                transacoes.add(new Transacao(idConta, tipo, valor));
            }
        }
        return transacoes;
    }
}