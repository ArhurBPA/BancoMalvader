package dao;

import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

import java.sql.*;

public class ContaDAO {

    public static void criarConta(Conta conta) {
        String sql = "INSERT INTO TB_CONTA (NR_CONTA, NO_AGENCIA, VL_SALDO, TP_CONTA, ID_CLIENTE) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, conta.getNumero());
            stmt.setString(2, conta.getAgencia());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setString(4, conta.getTipoConta());
            stmt.setInt(5, conta.getCliente().getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar a conta, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idConta = generatedKeys.getInt(1);

                    // Agora você pode usar o idConta para inserir na tabela específica
                    // do tipo de conta (TB_CONTA_CORRENTE ou TB_CONTA_POUPANCA)
                    if (conta instanceof ContaCorrente) {
                        inserirNaTabelaContaCorrente((ContaCorrente) conta, idConta, conn);
                    } else if (conta instanceof ContaPoupanca) {
                        inserirNaTabelaContaPoupanca((ContaPoupanca) conta, idConta, conn);
                    }
                } else {
                    throw new SQLException("Falha ao criar a conta, não foi possível obter o ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar conta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inserirNaTabelaContaCorrente(ContaCorrente conta, int idConta, Connection conn) throws SQLException {
        String sqlContaCorrente = "INSERT INTO TB_CONTA_CORRENTE (ID_CONTA_CORRENTE, VL_LIMITE, DT_VENCIMENTO, ID_CONTA) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmtContaCorrente = conn.prepareStatement(sqlContaCorrente)) {
            stmtContaCorrente.setInt(1, conta.getNumero()); // Assumindo que o número da conta é o ID da conta corrente
            stmtContaCorrente.setDouble(2, conta.getLimite());
            stmtContaCorrente.setDate(3, Date.valueOf(conta.getDataVencimento()));
            stmtContaCorrente.setInt(4, idConta);
            stmtContaCorrente.executeUpdate();
        }
    }

    private static void inserirNaTabelaContaPoupanca(ContaPoupanca conta, int idConta, Connection conn) throws SQLException {
        String sqlContaPoupanca = "INSERT INTO TB_CONTA_POUPANCA (ID_CONTA_POUPANCA, VL_TAXA_RENDIMENTO, ID_CONTA) VALUES (?, ?, ?)";
        try (PreparedStatement stmtContaPoupanca = conn.prepareStatement(sqlContaPoupanca)) {
            stmtContaPoupanca.setInt(1, conta.getNumero()); // Assumindo que o número da conta é o ID da conta poupança
            stmtContaPoupanca.setDouble(2, conta.getTaxaRendimento());
            stmtContaPoupanca.setInt(3, idConta);
            stmtContaPoupanca.executeUpdate();
        }
    }
}