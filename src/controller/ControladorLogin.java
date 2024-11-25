package controller;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import model.Cliente;
import model.Funcionario;
import view.LoginView;
import view.MenuClienteView;
import view.MenuFuncionarioView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorLogin implements ActionListener {

    private final LoginView loginView;

    public ControladorLogin(LoginView loginView) {
        this.loginView = loginView;
        this.loginView.loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String usuario = loginView.usuarioField.getText();
        String senha = new String(loginView.senhaField.getPassword());

        // Verifica se é um funcionário
        Funcionario funcionario = FuncionarioDAO.buscarFuncionarioPorCodigo(usuario);
        if (funcionario != null && funcionario.login(senha)) {
            JOptionPane.showMessageDialog(loginView, "Bem-vindo, funcionário " + funcionario.getNome() + "!",
                    "Login efetuado", JOptionPane.INFORMATION_MESSAGE);
            new MenuFuncionarioView();
            loginView.dispose();
            return;
        }

        // Verifica se é um cliente
        Cliente cliente = ClienteDAO.buscarClientePorCPF(usuario);
        if (cliente != null && cliente.login(senha)) {
            JOptionPane.showMessageDialog(loginView, "Bem-vindo, cliente " + cliente.getNome() + "!",
                    "Login efetuado", JOptionPane.INFORMATION_MESSAGE);
            new MenuClienteView();
            loginView.dispose();
            return;
        }

        // Se não for funcionário nem cliente, exibe mensagem de erro
        JOptionPane.showMessageDialog(loginView, "Usuário ou senha inválidos.", "Erro de login",
                JOptionPane.ERROR_MESSAGE);
    }
}