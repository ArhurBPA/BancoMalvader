package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.UsuarioController;

public class Senha {
    private JDialog dialogoSenha;

    // metodo para exibir pop up de autenticacao
    public Senha(UsuarioController controller, String tipoUsuario) {
        dialogoSenha = new JDialog((Frame) null, "Autenticação - " + tipoUsuario, true);
        dialogoSenha.setSize(400, 200); // Ajusta a altura para caber o novo botão
        dialogoSenha.setLayout(new GridLayout(4, 2, 10, 10));


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
                controller.verificarSenha(email, new String(senha));
            }
        });

        botaoSemCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoSenha.dispose();
                new CadastroView();
            }
        });

        dialogoSenha.add(labelEmail);
        dialogoSenha.add(campoEmail);
        dialogoSenha.add(labelSenha);
        dialogoSenha.add(campoSenha);
        dialogoSenha.add(botaoConfirmar);
        dialogoSenha.add(botaoSemCadastro);

        dialogoSenha.setLocationRelativeTo(null);
        dialogoSenha.setVisible(true);
    }

    public void dispose() {
        dialogoSenha.dispose();
    }
}