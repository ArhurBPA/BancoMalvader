package view;

import controller.ControllerUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalView extends JFrame {

    public MenuPrincipalView(ControllerUsuario controller) {
        super("Banco Malvader");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Font primaryFont = new Font("SansSerif", Font.BOLD, 30);
        Font secondaryFont = new Font("SansSerif", Font.BOLD, 15);

        JLabel noTitulo = new JLabel("Bem-vindo ao Banco Malvader!");
        noTitulo.setFont(primaryFont);
        noTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botaoX = new JButton("Acessar como Cliente");
        botaoX.setFont(secondaryFont);
        botaoX.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoX.setMinimumSize(new Dimension(200, 50));
        botaoX.setPreferredSize(new Dimension(200, 50));
        botaoX.setMaximumSize(new Dimension(200, 50));

        JButton botaoY = new JButton("Acessar como Funcionário");
        botaoY.setFont(secondaryFont);
        botaoY.setPreferredSize(new Dimension(200, 50));
        botaoY.setMaximumSize(new Dimension(200, 50));
        botaoY.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botaoSair = new JButton("Sair do Programa");
        botaoSair.setFont(secondaryFont);
        botaoSair.setPreferredSize(new Dimension(200, 50));
        botaoSair.setMaximumSize(new Dimension(200, 50));
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(noTitulo);
        add(Box.createVerticalStrut(20));
        add(botaoX);
        add(Box.createVerticalStrut(20));
        add(botaoY);
        add(Box.createVerticalStrut(20));
        add(botaoSair);
        add(Box.createVerticalStrut(20));

        botaoX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.abrirDialogoSenha("Cliente");
            }
        });

        botaoY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.abrirDialogoSenha("Funcionario");
            }
        });

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}