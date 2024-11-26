package view;

import controller.ControllerBanco;
import models.Cliente;
import javax.swing.*;
import java.awt.*;

public class MenuClienteView extends JFrame {
    private final JLabel noTitulo;
    private final JButton botaoX;
    private final JButton botaoY;
    private final JButton botaoZ;
    private final JButton botao1;
    private final JButton botaoSair;

    private final ControllerBanco bancoController;

    public MenuClienteView(Cliente cliente) {
        super("Bem-vindo " + cliente.getNome());
        bancoController = new ControllerBanco(cliente);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Font primaryFont = new Font("SansSerif", Font.BOLD, 30);
        Font secondaryFont = new Font("SansSerif", Font.BOLD, 15);

        noTitulo = new JLabel("Área do Cliente");
        noTitulo.setFont(primaryFont);
        noTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoX = new JButton("Ver Saldo");
        botaoX.setFont(secondaryFont);
        botaoX.setPreferredSize(new Dimension(200, 50));
        botaoX.setMaximumSize(new Dimension(200, 50));
        botaoX.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoX.addActionListener(e -> showSaldo());

        botaoY = new JButton("Fazer Depósito");
        botaoY.setFont(secondaryFont);
        botaoY.setPreferredSize(new Dimension(200, 50));
        botaoY.setMaximumSize(new Dimension(200, 50));
        botaoY.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoY.addActionListener(e -> makeDeposito());

        botaoZ = new JButton("Fazer Saque");
        botaoZ.setFont(secondaryFont);
        botaoZ.setPreferredSize(new Dimension(200, 50));
        botaoZ.setMaximumSize(new Dimension(200, 50));
        botaoZ.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoZ.addActionListener(e -> makeSaque());

        botao1 = new JButton("Ver Extrato");
        botao1.setFont(secondaryFont);
        botao1.setPreferredSize(new Dimension(200, 50));
        botao1.setMaximumSize(new Dimension(200, 50));
        botao1.setAlignmentX(Component.CENTER_ALIGNMENT);
//        botao1.addActionListener(e -> showExtrato());

        botaoSair = new JButton("Sair");
        botaoSair.setFont(secondaryFont);
        botaoSair.setPreferredSize(new Dimension(200, 50));
        botaoSair.setMaximumSize(new Dimension(200, 50));
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoSair.addActionListener(e -> dispose());

        add(Box.createVerticalStrut(20));
        add(noTitulo);
        add(Box.createVerticalStrut(20));
        add(botaoX);
        add(Box.createVerticalStrut(20));
        add(botaoY);
        add(Box.createVerticalStrut(20));
        add(botaoZ);
        add(Box.createVerticalStrut(20));
        add(botao1);
        add(Box.createVerticalStrut(20));
        add(botaoSair);
        add(Box.createVerticalStrut(20));
    }

    private void showSaldo() {
        try {
            double saldo = bancoController.consultarSaldo();
            JOptionPane.showMessageDialog(this, "Seu saldo atual é R$ " + saldo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar saldo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makeDeposito() {
        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor para depósito:");
        double valor = Double.parseDouble(valorStr);
        bancoController.realizarDeposito(valor);
    }

    private void makeSaque() {
        String valorStr = JOptionPane.showInputDialog(this, "Digite o valor para saque:");
        double valor = Double.parseDouble(valorStr);
        bancoController.realizarSaque(valor);
    }
}
//    private void showExtrato() {
//        try {
//            bancoController.gerarRelatorio();
//            JOptionPane.showMessageDialog(this, "Extrato gerado. Verifique o console.");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Erro ao gerar extrato: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}