package view;

import controllers.BancoController;
import models.Cliente;
import javax.swing.*;
import java.awt.*;

public class ClienteView extends JFrame {
    private final JLabel tituloLabel;
    private final JButton saldoButton;
    private final JButton depositarButton;
    private final JButton sacarButton;
    private final JButton extratoButton;
    private final JButton sairButton;

    private final BancoController bancoController;
    public ClienteView(Cliente cliente) {
        super("Bem-vindo " + cliente.getNome());
        bancoController = new BancoController(cliente);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // layout da tela

        Font primariaFont = new Font("SansSerif", Font.BOLD, 30); // fonte
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        // label de titulo
        tituloLabel = new JLabel("Área do Cliente");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        saldoButton = new JButton("Exibir Saldo");
        saldoButton.setFont(secundariaFont);
        saldoButton.setPreferredSize(new Dimension(200, 50));
        saldoButton.setMaximumSize(new Dimension(200, 50));
        saldoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saldoButton.addActionListener(e -> exibirSaldo());

        depositarButton = new JButton("Realizar Depósito");
        depositarButton.setFont(secundariaFont);
        depositarButton.setPreferredSize(new Dimension(200, 50));
        depositarButton.setMaximumSize(new Dimension(200, 50));
        depositarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositarButton.addActionListener(e -> realizarDeposito());

        sacarButton = new JButton("Realizar Saque");
        sacarButton.setFont(secundariaFont);
        sacarButton.setPreferredSize(new Dimension(200, 50));
        sacarButton.setMaximumSize(new Dimension(200, 50));
        sacarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sacarButton.addActionListener(e -> realizarSaque());

        extratoButton = new JButton("Exibir Extrato");
        extratoButton.setFont(secundariaFont);
        extratoButton.setPreferredSize(new Dimension(200, 50));
        extratoButton.setMaximumSize(new Dimension(200, 50));
        extratoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        extratoButton.addActionListener(e -> exibirExtrato());

        sairButton = new JButton("Sair");
        sairButton.setFont(secundariaFont);
        sairButton.setPreferredSize(new Dimension(200, 50));
        sairButton.setMaximumSize(new Dimension(200, 50));
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairButton.addActionListener(e -> dispose());

        // adicionar elementos
        add(Box.createVerticalStrut(20));
        add(tituloLabel);
        add(Box.createVerticalStrut(20));
        add(saldoButton);
        add(Box.createVerticalStrut(20));
        add(depositarButton);
        add(Box.createVerticalStrut(20));
        add(sacarButton);
        add(Box.createVerticalStrut(20));
        add(extratoButton);
        add(Box.createVerticalStrut(20));
        add(sairButton);
        add(Box.createVerticalStrut(20));
    }

    // metodo para exibir o pop up para exibir saldo
    private void exibirSaldo() {
        try {
            double saldo = bancoController.consultarSaldo();
            JOptionPane.showMessageDialog(this, "Seu saldo atual é R$ " + saldo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar saldo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para exibir o pop up para realizar deposito
    private void realizarDeposito() {
        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do depósito:");
        double valor = Double.parseDouble(valorStr);
        bancoController.realizarDeposito(valor);
    }

    // metodo para exibir o pop up para realizar saque
    private void realizarSaque() {
        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor do saque:");
        double valor = Double.parseDouble(valorStr);
        bancoController.realizarSaque(valor);

    }

    // metodo para exibir o pop up para exibir extrato
    private void exibirExtrato() {
        try {
            bancoController.gerarRelatorio();
            JOptionPane.showMessageDialog(this, "Relatório gerado. Verifique o console.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}