package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import controllers.UsuarioController;

public class MainView extends JFrame{
    private final JLabel tituloLabel;
    private final JLabel descricaoLabel;
    private final JButton clienteButton;
    private final JButton funcionarioButton;
    private final JButton encerrarButton;
    private UsuarioController controller;

    // construtor
    public MainView(UsuarioController controller) {
        super("Banco Malvader");
        this.controller = controller;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // layout da tela

        Font primariaFont = new Font("SansSerif", Font.BOLD, 30); // fonte
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        // label de titulo e descricao
        tituloLabel = new JLabel("Banco Malvader");
        tituloLabel.setFont(primariaFont); // setar fonte
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descricaoLabel = new JLabel("Aqui o seu dinheiro tem forca!");
        descricaoLabel.setFont(secundariaFont);
        descricaoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        clienteButton = new JButton("Login - Cliente");
        clienteButton.setFont(secundariaFont);
        clienteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clienteButton.setMinimumSize(new Dimension(200, 50));
        clienteButton.setPreferredSize(new Dimension(200, 50));
        clienteButton.setMaximumSize(new Dimension(200, 50));

        funcionarioButton = new JButton("Login - Funcionario");
        funcionarioButton.setFont(secundariaFont);
        funcionarioButton.setPreferredSize(new Dimension(200, 50));
        funcionarioButton.setMaximumSize(new Dimension(200, 50));
        funcionarioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        encerrarButton = new JButton("Encerrar Programa");
        encerrarButton.setFont(secundariaFont);
        encerrarButton.setPreferredSize(new Dimension(200, 50));
        encerrarButton.setMaximumSize(new Dimension(200, 50));
        encerrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // adicionar elementos
        add(Box.createVerticalStrut(20));
        add(Box.createVerticalStrut(10));
        add(tituloLabel);
        add(descricaoLabel);
        add(Box.createVerticalStrut(20));
        add(clienteButton);
        add(Box.createVerticalStrut(20));
        add(funcionarioButton);
        add(Box.createVerticalStrut(20));
        add(encerrarButton);
        add(Box.createVerticalStrut(20));

        // eventos dos botoes
        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // criar pop-up para login de cliente
                controller.abrirDialogoSenha("Cliente");
            }
        });

        funcionarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // criar pop-up para login de funcionário
                controller.abrirDialogoSenha("Funcionario");
            }
        });

        encerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // fechar o sistema
                System.exit(0);
            }
        });
    }
}