package controller;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import models.Cliente;
import view.MenuFuncionarioView;
import view.MenuPrincipalView;
import view.ModalSenhaVW;
import view.MenuClienteView;

import javax.swing.*;
import java.util.Optional;

public class ControllerUsuario {
    private final MenuPrincipalView mainView;

    // construtor
    public ControllerUsuario() {
        mainView = new MenuPrincipalView(this);
    }

    public void abrirDialogoSenha(String tipoUsuario) {
        new ModalSenhaVW(this, tipoUsuario);
    }

    public void verificarSenha(String tipoUsuario, String email, String senha, ModalSenhaVW passwordDialogView) {
        if (tipoUsuario.equals("Cliente")) {
            ClienteDAO clienteDAO = new ClienteDAO();
            Optional<Cliente> clienteLogado = clienteDAO.getUser(email, senha);

            if (clienteLogado.isPresent()) {
                passwordDialogView.dispose();
                mainView.dispose();
                MenuClienteView clienteView = new MenuClienteView(clienteLogado.get());
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
                MenuFuncionarioView funcionarioView = new MenuFuncionarioView(funcionarioLogado.get());
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

    public boolean verificarSenha(String usuario, String s) {
        return false;
    }
}
//    public MenuPrincipalView getMainView() {
//        return mainView;
//    }
