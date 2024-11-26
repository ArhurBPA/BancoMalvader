package view;
import controller.UsuarioController;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Inicia a aplicação na thread do Swing
        SwingUtilities.invokeLater(() -> {
            UsuarioController controller = new UsuarioController();
            LoginView loginView = new LoginView();

            // Adiciona um listener para o botão de login
            loginView.getLoginButton().addActionListener(e -> {
                String usuario = loginView.getUsernameField().getText();
                char[] senha = loginView.getPasswordField().getPassword();

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