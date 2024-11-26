package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    public AbstractButton botaoLogin;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginView() {
        setTitle("Login - Banco Malvader");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(false); // Impede redimensionamento
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo ou título do sistema
        JLabel titleLabel = new JLabel("Banco Malvader", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        add(titleLabel, gbc);

        // Espaço para um logo opcional
        JLabel logoLabel = new JLabel(new ImageIcon("logo.png"), SwingConstants.CENTER); // Substitua por um caminho válido
        gbc.gridy = 1;
        add(logoLabel, gbc);

        // Campo de nome de usuário
        JLabel usernameLabel = new JLabel("Usuário:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Campo de senha
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(80, 80, 80));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Botão de login
        loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa duas colunas
        add(loginButton, gbc);

        // Botão de registro
        registerButton = new JButton("Registrar-se");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(102, 204, 0));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridy = 5;
        add(registerButton, gbc);

        // Rodapé
        JLabel footerLabel = new JLabel("© 2024 Banco Malvader. Todos os direitos reservados.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(150, 150, 150));
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 10, 10, 10); // Espaço superior maior para o rodapé
        add(footerLabel, gbc);

        setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
