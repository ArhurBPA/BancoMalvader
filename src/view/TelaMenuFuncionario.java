package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenuFuncionario extends JFrame {

    public TelaMenuFuncionario() {
        setTitle("Menu Funcionário - Banco Malvader");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 10, 10));

        JButton botaoAbrirConta = new JButton("Abrir Conta");
        JButton botaoEncerrarConta = new JButton("Encerrar Conta");
        JButton botaoConsultarConta = new JButton("Consultar Conta");
        JButton botaoAlterarDadosConta = new JButton("Alterar Dados da Conta");
        JButton botaoGerarRelatorio = new JButton("Gerar Relatório");
        JButton botaoVoltar = new JButton("Voltar");

        botaoAbrirConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir uma nova conta
                JOptionPane.showMessageDialog(null, "Abrir Conta");
            }
        });

        // Adicionar ActionListener para os outros botões

        add(botaoAbrirConta);
        add(botaoEncerrarConta);
        add(botaoConsultarConta);
        add(botaoAlterarDadosConta);
        add(botaoGerarRelatorio);
        add(botaoVoltar);

        setVisible(true);
    }
}