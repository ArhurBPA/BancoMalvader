package view;

import controller.ControllerUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ModalSenhaVW {
    private final JDialog hashSenha;

    public ModalSenhaVW(ControllerUsuario controller, String tipoUsuario) {
        hashSenha = new JDialog((Frame) null, "Autenticação - " + tipoUsuario, true);
        hashSenha.setSize(400, 200);
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

                // Verificar campos vazios
                if (email.isEmpty() || senha.length == 0) {
                    JOptionPane.showMessageDialog(null, "Email e senha são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controller.verificarSenha(tipoUsuario, email, Arrays.toString(senha), ModalSenhaVW.this);
                Arrays.fill(senha, ' '); // Limpar array de senha
            }
        });

        botaoSemCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hashSenha.dispose();
                CadastroUsuarioView cadastroView = new CadastroUsuarioView();
                cadastroView.setVisible(true);
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