package view;

import controller.ControllerUsuario;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Inicia a aplicação na thread do Swing
        SwingUtilities.invokeLater(() -> {
            ControllerUsuario controller = new ControllerUsuario();
            LoginView loginView = new LoginView();

            // Adiciona um listener para o botão de login
            loginView.botaoLogin.addActionListener(e -> {
                String usuario = loginView.espacoUsuario.getText();
                char[] senha = loginView.lugarSenha.getPassword();

                // Autenticação
                if (controller.verificarSenha(usuario, new String(senha))) {
                    // Autenticação bem sucedida, abre o menu principal
                    loginView.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginView, "Usuário ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                // Limpa o array de senha
                java.util.Arrays.fill(senha, ' ');
            });

            loginView.setVisible(true);
        });
    }
}