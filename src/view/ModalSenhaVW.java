package view;

import controller.ControllerUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModalSenhaVW {
    private JDialog hashSenha;

    public ModalSenhaVW(ControllerUsuario controller, String tipoUsuario) {
        hashSenha = new JDialog((Frame) null, "Autenticação - " + tipoUsuario, true);
        hashSenha.setSize(400, 200); // Ajusta a altura para caber o novo botão
        hashSenha.setLayout(new GridLayout(4, 2, 10, 10));


        JLabel labelSenha = new JLabel("Digite sua senha:");
        JPasswordField campoSenha = new JPasswordField(15);
        JLabel labelEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField();
        JButton botaoConfirmar = new JButton("Confirmar");
        JButton botaoSemCadastro = new JButton("Não tem cadastro?"); // Novo botão

        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] senha = campoSenha.getPassword();
                String email = campoEmail.getText();
                controller.verificarSenha(tipoUsuario, email, new String(senha), ModalSenhaVW.this);
            }
        });

        botaoSemCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hashSenha.dispose();
                new CadastroUsuarioView();
            }
        });

        hashSenha.add(labelEmail);
        hashSenha.add(campoEmail);
        hashSenha.add(labelSenha);
        hashSenha.add(campoSenha);
        hashSenha.add(botaoConfirmar);
        hashSenha.add(botaoSemCadastro);

        hashSenha.setLocationRelativeTo(null);
        hashSenha.setVisible(true);
    }

    public void dispose() {
        hashSenha.dispose();
    }
}