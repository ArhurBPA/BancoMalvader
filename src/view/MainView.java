package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.UsuarioController;

public class MainView extends JFrame{
    private final JLabel tituloLabel;
    private final JButton clienteButton;
    private final JButton funcionarioButton;
    private final JButton encerrarButton;
    private UsuarioController controller;

    // construtor
    public MainView(UsuarioController controller) {
        super("Banco Malvader");
        this.controller = controller;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // layout da tela


        // label de titulo e descricao
        tituloLabel = new JLabel("Banco Malvader");


        // botoes
        clienteButton = new JButton("Login - Cliente");

        funcionarioButton = new JButton("Login - Funcionario");

        encerrarButton = new JButton("Encerrar Programa");

        // adicionar elementos
        add(Box.createVerticalStrut(20));
        add(Box.createVerticalStrut(10));
        add(tituloLabel);
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