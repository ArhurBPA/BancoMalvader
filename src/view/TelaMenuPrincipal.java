package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenuPrincipal extends JFrame {

    public TelaMenuPrincipal() {
        setTitle("Menu Principal - Banco Malvader");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton botaoFuncionario = new JButton("Funcionário");
        JButton botaoCliente = new JButton("Cliente");
        JButton botaoSair = new JButton("Sair");

        botaoFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir o menu do funcionário
                JOptionPane.showMessageDialog(null, "Menu Funcionário");
            }
        });

        botaoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir o menu do cliente
                JOptionPane.showMessageDialog(null, "Menu Cliente");
            }
        });

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(botaoFuncionario);
        add(botaoCliente);
        add(botaoSair);

        setVisible(true);
    }
    public static void main(String[] args) {
        new TelaMenuPrincipal();
    }
}