package controllers;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import models.Cliente;
import view.FuncionarioView;
import view.MainView;
import view.PasswordDialogView;
import view.ClienteView;

import javax.swing.*;
import java.util.Optional;

public class UsuarioController {
    private MainView mainView;

    // construtor que inicializa a interface principal
    public UsuarioController() {
        mainView = new MainView(this);
    }

    // metodo que abre um dialogo para o usuário inserir a senha, baseado no tipo de usuario
    public void abrirDialogoSenha(String tipoUsuario) {
        new PasswordDialogView(this, tipoUsuario);
    }

    // metodo para verificar as credenciais fornecidas pelo usuario e, se validas, abre a interface correspondente ao tipo de usuario
    public void verificarSenha(String tipoUsuario, String email ,String senha, PasswordDialogView passwordDialogView) {
        if (tipoUsuario.equals("Cliente")) {
            ClienteDAO clienteDAO = new ClienteDAO();
            Optional<Cliente> clienteLogado = clienteDAO.getUser(email, senha);

            if (clienteLogado.isPresent()) {
                passwordDialogView.dispose();
                mainView.dispose();
                ClienteView clienteView = new ClienteView(clienteLogado.get());
                clienteView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clienteView.setSize(400, 600);
                clienteView.setLocationRelativeTo(null);
                clienteView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Senha incorreta ou usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tipoUsuario.equals("Funcionario")) {
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            Optional<Cliente> funcionarioLogado = funcionarioDAO.getUser(email, senha);

            if (funcionarioLogado.isPresent()) {
                passwordDialogView.dispose();
                mainView.dispose();
                FuncionarioView funcionarioView = new FuncionarioView(funcionarioLogado.get());
                funcionarioView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                funcionarioView.setSize(400, 600);
                funcionarioView.setLocationRelativeTo(null);
                funcionarioView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Senha incorreta ou usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Erro ao identificar usuario.");
            System.exit(0);
        }
    }

    // metodo para retornar a referencia à mainView
    public MainView getMainView() {
        return mainView;
    }
}