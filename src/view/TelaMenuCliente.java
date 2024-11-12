package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenuCliente extends JFrame {

    public TelaMenuCliente() {
        setTitle("Menu Cliente - Banco Malvader");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton botaoConsultarSaldo = new JButton("Consultar Saldo");
        JButton botaoDepositar = new JButton("Depositar");
        JButton botaoSacar = new JButton("Sacar");
        JButton botaoConsultarExtrato = new JButton("Consultar Extrato");
        JButton botaoVoltar = new JButton("Voltar");

        botaoConsultarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para consultar saldo
                JOptionPane.showMessageDialog(null, "Consultar Saldo");
            }
        });

        // Adicionar ActionListener para os outros botões

        add(botaoConsultarSaldo);
        add(botaoDepositar);
        add(botaoSacar);
        add(botaoConsultarExtrato);
        add(botaoVoltar);

        setVisible(true);
    }
}