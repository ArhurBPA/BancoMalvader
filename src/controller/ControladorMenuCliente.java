package controller;

import dao.BancoDAO;

import model.Cliente;
import model.Transacao;
import view.MenuClienteView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ControladorMenuCliente implements ActionListener {
    private MenuClienteView menuView = null;
    private BancoDAO bancoDAO = null;

    public ControladorMenuCliente(MenuClienteView menuView, Cliente cliente) throws SQLException {
        this.menuView = menuView;
        this.bancoDAO = new BancoDAO(cliente); // Inicializa o BancoDAO com o cliente
        this.menuView.consultarSaldoButton.addActionListener(this);
        this.menuView.depositarButton.addActionListener(this);
        this.menuView.sacarButton.addActionListener(this);
        this.menuView.consultarExtratoButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuView.consultarSaldoButton) {
            try {
                double saldo = bancoDAO.consultarSaldo();
                JOptionPane.showMessageDialog(menuView, "Seu saldo é: R$" + saldo, "Saldo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(menuView, "Erro ao consultar saldo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == menuView.depositarButton) {
            try {
                String valorStr = JOptionPane.showInputDialog(menuView, "Digite o valor a depositar:", "Depósito", JOptionPane.QUESTION_MESSAGE);
                if (valorStr == null) {
                    return; // Cancelado pelo usuário
                }

                double valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(menuView, "Valor inválido. Digite um valor positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bancoDAO.realizarDeposito(valor);
                JOptionPane.showMessageDialog(menuView, "Depósito de R$" + valor + " realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuView, "Valor inválido. Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(menuView, "Erro ao realizar depósito: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == menuView.sacarButton) {
            try {
                String valorStr = JOptionPane.showInputDialog(menuView, "Digite o valor a sacar:", "Saque", JOptionPane.QUESTION_MESSAGE);
                if (valorStr == null) {
                    return; // Cancelado pelo usuário
                }

                double valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(menuView, "Valor inválido. Digite um valor positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double saldo = bancoDAO.consultarSaldo();
                if (saldo < valor) {
                    JOptionPane.showMessageDialog(menuView, "Saldo insuficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                bancoDAO.realizarSaque(valor);
                JOptionPane.showMessageDialog(menuView, "Saque de R$" + valor + " realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuView, "Valor inválido. Digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(menuView, "Erro ao realizar saque: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == menuView.consultarExtratoButton) {
            try {
                List<Transacao> transacoes = bancoDAO.obterTransacoes();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == menuView.consultarExtratoButton) {
        try {
            List<Transacao> transacoes = bancoDAO.obterTransacoes();

            // Formata as transações para exibição
            StringBuilder extrato = new StringBuilder();
            extrato.append("Extrato Bancário:\n");
            for (Transacao transacao : transacoes) {
                extrato.append("Tipo: ").append(transacao.getTipoTransacao()).append("\n");
                extrato.append("Valor: R$ ").append(transacao.getValorTransacao()).append("\n");
                extrato.append("Data e Hora: ").append(transacao.getDataHoraTransacao()).append("\n");
                extrato.append("------------------------\n");
            }

            JOptionPane.showMessageDialog(menuView, extrato.toString(), "Extrato", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(menuView, "Erro ao consultar extrato: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
}
