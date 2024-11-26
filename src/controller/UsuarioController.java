package controller;

import dao.ClienteDAO;
import model.Cliente;
import view.MainView;
import view.Senha;
import view.ClienteView;

import javax.swing.*;
import java.util.Optional;

public class UsuarioController {
    private final MainView mainView;

    // construtor que inicializa a interface principal
    public UsuarioController() {
        mainView = new MainView(this);
    }

    // metodo que abre um dialogo para o usuário inserir a senha, baseado no tipo de usuario
    public void abrirDialogoSenha(String tipoUsuario) {
        new Senha(this, tipoUsuario);
    }

    // metodo para verificar as credenciais fornecidas pelo usuario e, se validas, abre a interface correspondente ao tipo de usuario
    public boolean verificarSenha(String email, String senha) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Optional<Cliente> clienteLogado = clienteDAO.getUser(email, senha);

        if (clienteLogado.isPresent()) {
//                passwordDialogView.dispose();
            mainView.dispose();
            ClienteView clienteView = new ClienteView(clienteLogado.get());
            clienteView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clienteView.setSize(400, 600);
            clienteView.setLocationRelativeTo(null);
            clienteView.setVisible(true);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta ou usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
}