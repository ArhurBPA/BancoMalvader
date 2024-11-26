package view;

import javax.swing.*;

public class LoginView extends JFrame {
    public JButton botaoLogin;
    public JPasswordField lugarSenha;
    public JTextField espacoUsuario;
    public LoginView() {
        setTitle("Banco Malvader - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel rotuloUsuario = new JLabel("Usuário:");
        rotuloUsuario.setBounds(10, 20, 80, 25);
        panel.add(rotuloUsuario);

        espacoUsuario = new JTextField(20);
        espacoUsuario.setBounds(100, 20, 165, 25);
        panel.add(espacoUsuario);

        JLabel rotuloSenha = new JLabel("Senha:");
        rotuloSenha.setBounds(10, 50, 80, 25);
        panel.add(rotuloSenha);

        lugarSenha = new JPasswordField(20);
        lugarSenha.setBounds(100, 50, 165, 25);
        panel.add(lugarSenha);

        botaoLogin = new JButton("Login");
        botaoLogin.setBounds(10, 80, 80, 25);
        panel.add(botaoLogin);

    }


    public static class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                LoginView frame = new LoginView();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensuring the application exits when the window is closed.
                frame.setVisible(true);
            });
        }
    }
}