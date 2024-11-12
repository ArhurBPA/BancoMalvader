package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaLogin() {
        setTitle("Login - Banco Malvader");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel labelUsuario = new JLabel("Usuário:");
        campoUsuario = new JTextField(20);
        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField(20);
        JButton botaoLogin = new JButton("Login");
        JButton botaoSair = new JButton("Sair");

        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para realizar o login
                String usuario = campoUsuario.getText();
                String senha = new String(campoSenha.getPassword());
                JOptionPane.showMessageDialog(null, "Usuário: " + usuario + ", Senha: " + senha);
            }
        });

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(labelUsuario);
        add(campoUsuario);
        add(labelSenha);
        add(campoSenha);
        add(botaoLogin);
        add(botaoSair);

        setVisible(true);
    }
}