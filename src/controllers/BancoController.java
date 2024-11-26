package controllers;

import dao.BancoDAO;
import models.Cliente;
import models.Transacao;
import utils.CSVExporter;

import javax.swing.*;
import java.sql.*;
import java.util.List;

public class BancoController {
    private BancoDAO bancoDAO;

    // construtor que inicializa o BancoDAO com o cliente fornecido
    public BancoController(Cliente cliente) {
        try {
            this.bancoDAO = new BancoDAO(cliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            this.bancoDAO = null;
        }
    }

    // metodo para consultar o saldo do cliente
    public double consultarSaldo() {
        try {
            return bancoDAO.consultarSaldo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar saldo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return -1.0;
        }
    }

    // metodo para realizar um depósito na conta do cliente
    public void realizarDeposito(double valor) {
        try {
            bancoDAO.realizarDeposito(valor);
            JOptionPane.showMessageDialog(null, "Depósito de R$ " + valor + " realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar depósito: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para realizar um saque da conta do cliente
    public void realizarSaque(double valor) {
        try {
            double saldoAtual = bancoDAO.consultarSaldo();
            if (saldoAtual >= valor) {
                bancoDAO.realizarSaque(valor);
                JOptionPane.showMessageDialog(null, "Saque de R$ " + valor + " realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar o saque.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar saque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para gerar um relatorio das transacoes do cliente
    public void gerarRelatorio() {
        try {
            List<Transacao> transacoes = bancoDAO.obterTransacoes();
            if (transacoes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma transação encontrada.", "Relatório", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder relatorio = new StringBuilder("Relatório de Transações:\n");
                for (Transacao transacao : transacoes) {
                    relatorio.append("- ").append(transacao.getTipoTransacao()).append(": R$ ").append(transacao.getValor()).append("\n");
                }
                JOptionPane.showMessageDialog(null, relatorio.toString(), "Relatório", JOptionPane.INFORMATION_MESSAGE);
                new CSVExporter().exportarParaCSV(transacoes, "transacoes.csv");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}