package dao;

import models.Cliente;
import models.Transacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    private final Cliente cliente;
    private final Connection connection = ConnectionFactory.getConnection();
    private final int idConta;  // variavel de instancia para armazenar o id_conta

    // construtor que inicializa o DAO com o cliente fornecido
    public BancoDAO(Cliente cliente) throws SQLException {
        this.cliente = cliente;
        this.idConta = buscarIdConta();  // inicializa o idConta ao criar a instancia do DAO
    }

    // metodo para buscr o id_conta do cliente no banco de dados
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

    // metodo para consultar o saldo da conta do cliente
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

    // metodo para realizar um deposito na conta do cliente
    public void realizarDeposito(double valor) throws SQLException {
        connection.setAutoCommit(false);  // desabilita auto-commit para transacoes manuais

        String updateSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo + ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";

        try (PreparedStatement stmtSaldo = connection.prepareStatement(updateSaldo);
             PreparedStatement stmtTransacao = connection.prepareStatement(inserirTransacao)) {

            // atualiza saldo
            stmtSaldo.setDouble(1, valor);
            stmtSaldo.setInt(2, cliente.getId());
            int linhasAfetadas = stmtSaldo.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma linha foi atualizada. Verifique o ID do cliente.");
            }

            // insere transacoo usando o idConta armazenado no construtor
            stmtTransacao.setString(1, "deposito");
            stmtTransacao.setDouble(2, valor);
            stmtTransacao.setInt(3, idConta);
            int transacaoAfetada = stmtTransacao.executeUpdate();

            if (transacaoAfetada > 0) {
                System.out.println("Transação realizada com sucesso. ID da Conta: " + idConta);
            } else {
                throw new SQLException("Erro ao inserir a transação.");
            }

            connection.commit();  // confirma a transacao

        } catch (SQLException e) {
            connection.rollback();  // reverte a transação em caso de erro
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);  // restaura o auto-commit padrão
        }
    }

    // metodo para realizar um saque na conta do cliente
    public void realizarSaque(double valor) throws SQLException {
        String updateSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo - ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";
        try (PreparedStatement stmtSaldo = connection.prepareStatement(updateSaldo);
             PreparedStatement stmtTransacao = connection.prepareStatement(inserirTransacao)) {

            // atualiza saldo
            stmtSaldo.setDouble(1, valor);
            stmtSaldo.setInt(2, idConta);
            stmtSaldo.executeUpdate();

            // insere transacao
            stmtTransacao.setString(1, "saque");
            stmtTransacao.setDouble(2, valor);
            stmtTransacao.setInt(3, idConta);
            stmtTransacao.executeUpdate();
        }
    }

    // metodo para obter a lista de transacoes da conta do cliente
    public List<Transacao> obterTransacoes() throws SQLException {
        String query = "SELECT * FROM transacao WHERE id_conta = ? ORDER BY id_transacao DESC";
        List<Transacao> transacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idConta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idTransacao = rs.getInt("id_transacao");
                String tipo = rs.getString("tipo_transacao");
                double valor = rs.getDouble("valor");
                String dataTransacao = rs.getString("data_hora");
                int idConta = rs.getInt("id_conta");
                transacoes.add(new Transacao(idTransacao, idConta, tipo, valor, dataTransacao));
            }
        }
        return transacoes;
    }
}