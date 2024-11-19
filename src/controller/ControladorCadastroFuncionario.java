package controller;

import dao.FuncionarioDAO;
import model.Endereco;
import model.Funcionario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControladorCadastroFuncionario implements ActionListener {

    private final CadastroFuncionarioView cadastroView;

    public ControladorCadastroFuncionario(CadastroFuncionarioView cadastroView) {
        this.cadastroView = cadastroView;
        this.cadastroView.cadastrarButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Obtém os dados da view
            String nome = cadastroView.nomeField.getText();
            String cpf = cadastroView.cpfField.getText();
            String dataNascimentoStr = cadastroView.dataNascimentoField.getText();
            String telefone = cadastroView.telefoneField.getText();
            String cep = cadastroView.cepField.getText();
            String local = cadastroView.localField.getText();
            int numeroCasa = Integer.parseInt(cadastroView.numeroCasaField.getText());
            String bairro = cadastroView.bairroField.getText();
            String cidade = cadastroView.cidadeField.getText();
            String estado = cadastroView.estadoField.getText();
            String codigoFuncionario = cadastroView.codigoFuncionarioField.getText();
            String cargo = cadastroView.cargoField.getText();
            String senha = cadastroView.senhaField.getText();

            // Validações básicas (implementar validações mais robustas)
            if (nome.isEmpty() || cpf.isEmpty() || dataNascimentoStr.isEmpty() || telefone.isEmpty() ||
                    cep.isEmpty() || local.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || estado.isEmpty() ||
                    codigoFuncionario.isEmpty() || cargo.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(cadastroView, "Preencha todos os campos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Converte a data de nascimento
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Cria o objeto Endereco
            Endereco endereco = new Endereco(cep, local, numeroCasa, bairro, cidade, estado);

            // Cria o objeto Funcionario
            Funcionario funcionario = new Funcionario(nome, cpf, dataNascimento, telefone, endereco,
                    senha, codigoFuncionario, cargo);

            // Salva o funcionário no banco de dados
            FuncionarioDAO.criarFuncionario(funcionario);

            // Exibe mensagem de sucesso
            JOptionPane.showMessageDialog(cadastroView, "Funcionário cadastrado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpa os campos da view
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(cadastroView, "Número da casa inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(cadastroView, "Data de nascimento inválida. Use o formato dd/MM/yyyy",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpa os campos da view
    private void limparCampos() {
        cadastroView.nomeField.setText("");
        cadastroView.cpfField.setText("");
        cadastroView.dataNascimentoField.setText("");
        cadastroView.telefoneField.setText("");
        cadastroView.cepField.setText("");
        cadastroView.localField.setText("");
        cadastroView.numeroCasaField.setText("");
        cadastroView.bairroField.setText("");
        cadastroView.cidadeField.setText("");
        cadastroView.estadoField.setText("");
        cadastroView.codigoFuncionarioField.setText("");
        cadastroView.cargoField.setText("");
        cadastroView.senhaField.setText("");
    }
}