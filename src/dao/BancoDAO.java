package dao;

import models.Cliente;
import models.Transacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO {

    private final Cliente cliente;
    private final Connection conexao = ConnectionFactory.getConnection();
    private final int idConta;

    public BancoDAO(Cliente cliente) throws SQLException {
        this.cliente = cliente;
        this.idConta = obterIdConta();
    }

    private int obterIdConta() throws SQLException {
        String sql = "SELECT c.id_conta FROM conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente WHERE cl.id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return resultado.getInt("id_conta");
            } else {
                throw new SQLException("Conta não encontrada para o cliente.");
            }
        }
    }

    public double consultarSaldo() throws SQLException {
        String sql = "SELECT c.saldo FROM conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente WHERE cl.id_usuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                return resultado.getDouble("saldo");
            } else {
                throw new SQLException("Cliente não encontrado.");
            }
        }
    }

    public void realizarDeposito(double valorDeposito) throws SQLException {
        conexao.setAutoCommit(false);

        String atualizarSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo + ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";

        try (PreparedStatement stmtSaldo = conexao.prepareStatement(atualizarSaldo);
             PreparedStatement stmtTransacao = conexao.prepareStatement(inserirTransacao)) {

            stmtSaldo.setDouble(1, valorDeposito);
            stmtSaldo.setInt(2, cliente.getId());
            int linhasAfetadas = stmtSaldo.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhuma linha foi atualizada. Verifique o ID do cliente.");
            }

            stmtTransacao.setString(1, "deposito");
            stmtTransacao.setDouble(2, valorDeposito);
            stmtTransacao.setInt(3, idConta);
            stmtTransacao.executeUpdate();

            conexao.commit();

        } catch (SQLException e) {
            conexao.rollback();
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexao.setAutoCommit(true);
        }
    }

    public void realizarSaque(double valorSaque) throws SQLException {
        String atualizarSaldo = "UPDATE conta c JOIN cliente cl ON c.id_cliente = cl.id_cliente SET c.saldo = c.saldo - ? WHERE cl.id_usuario = ?";
        String inserirTransacao = "INSERT INTO transacao (tipo_transacao, valor, id_conta) VALUES (?, ?, ?)";
        try (PreparedStatement stmtSaldo = conexao.prepareStatement(atualizarSaldo);
             PreparedStatement stmtTransacao = conexao.prepareStatement(inserirTransacao)) {

            stmtSaldo.setDouble(1, valorSaque);
            stmtSaldo.setInt(2, idConta);
            stmtSaldo.executeUpdate();

            stmtTransacao.setString(1, "saque");
            stmtTransacao.setDouble(2, valorSaque);
            stmtTransacao.setInt(3, idConta);
            stmtTransacao.executeUpdate();
        }
    }

    public List<Transacao> obterTransacoes() throws SQLException {
        String sql = "SELECT * FROM transacao WHERE id_conta = ? ORDER BY id_transacao DESC";
        List<Transacao> transacoes = new ArrayList<>();
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                int idTransacao = resultado.getInt("id_transacao");
                String tipo = resultado.getString("tipo_transacao");
                double valor = resultado.getDouble("valor");
                String dataTransacao = resultado.getString("data_hora");
                int idConta = resultado.getInt("id_conta");
                transacoes.add(new Transacao(idTransacao, idConta, tipo, valor, dataTransacao));
            }
        }
        return transacoes;
    }
}