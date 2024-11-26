package view;

import javax.swing.*;
import java.awt.*;
import models.Cliente;

public class WelcomeView {
    private JFrame frame;

    // metodo para exibir tela de boas vindas
    public WelcomeView(Cliente cliente) {
        frame = new JFrame("Bem-vindo");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + cliente.getNome() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(welcomeLabel);
        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}