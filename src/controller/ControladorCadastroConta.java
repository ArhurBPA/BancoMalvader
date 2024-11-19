package controller;

import dao.ClienteDAO;
import dao.ContaDAO;
import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControladorCadastroConta implements ActionListener {

    private final CadastroContaView cadastroView;

    public ControladorCadastroConta(CadastroContaView cadastroView) {
        this.cadastroView = cadastroView;
        this.cadastroView.cadastrarButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Obtém os dados da view
            String tipoConta = (String) cadastroView.tipoContaComboBox.getSelectedItem();
            int numero = Integer.parseInt(cadastroView.numeroField.getText());
            String agencia = cadastroView.agenciaField.getText();
            double saldo = Double.parseDouble(cadastroView.saldoField.getText());
            int idCliente = Integer.parseInt(cadastroView.idClienteField.getText());

            // Validações básicas (implementar validações mais robustas)
            if (agencia.isEmpty() || tipoConta == null) {
                JOptionPane.showMessageDialog(cadastroView, "Preencha todos os campos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Busca o cliente no banco de dados
            Cliente cliente = ClienteDAO.buscarClientePorId(idCliente);
            if (cliente == null) {
                JOptionPane.showMessageDialog(cadastroView, "Cliente não encontrado.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cria o objeto Conta (ContaCorrente ou ContaPoupanca)
            Conta conta;
            if (tipoConta.equals("Corrente")) {
                double limite = Double.parseDouble(cadastroView.limiteField.getText());
                LocalDate dataVencimento = LocalDate.parse(cadastroView.dataVencimentoField.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                conta = new ContaCorrente(numero, agencia, saldo, cliente, limite, dataVencimento);
            } else { // Poupança
                double taxaRendimento = Double.parseDouble(cadastroView.taxaRendimentoField.getText());
                conta = new ContaPoupanca(numero, agencia, saldo, cliente, taxaRendimento);
            }

            // Salva a conta no banco de dados
            ContaDAO.criarConta(conta);

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(cadastroView, "Conta cadastrada com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpa os campos da view
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cadastroView, "Número, saldo, ID do cliente, limite ou taxa de rendimento inválidos.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(cadastroView, "Data de vencimento inválida. Use o formato dd/MM/yyyy",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpa os campos da view
    private void limparCampos() {
        cadastroView.numeroField.setText("");
        cadastroView.agenciaField.setText("");
        cadastroView.saldoField.setText("");
        cadastroView.idClienteField.setText("");
        cadastroView.limiteField.setText("");
        cadastroView.dataVencimentoField.setText("");
        cadastroView.taxaRendimentoField.setText("");
    }
}