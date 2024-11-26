package view;

import dao.ClienteDAO;
import models.Endereco;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroUsuarioView {
    private final JFrame janelaCadastro;

    public CadastroUsuarioView() {
        janelaCadastro = new JFrame("--- Cadastro de Usuário ---");
        janelaCadastro.setSize(500, 700);
        janelaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaCadastro.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel rotuloX = new JLabel("Nome:");
        JTextField espacoX = new JTextField();

        JLabel rotuloY = new JLabel("Email:");
        JTextField espacoPraEmail = new JTextField();

        JLabel rotuloZ = new JLabel("Senha:");
        JPasswordField lugarSenha = new JPasswordField();

        JLabel rotuloA = new JLabel("CPF:");
        JTextField espacoPraCPF = new JTextField();

        JLabel rotuloB = new JLabel("Telefone:");
        JTextField espacoPraFone = new JTextField();

        JLabel rotuloC = new JLabel("Data de Nascimento (--/--/----):");
        JTextField espacoPraDataNasc = new JTextField();

        JLabel rotuloTpCliente = new JLabel("Tipo de Cliente:");
        JComboBox<String> campoTpCliente = new JComboBox<>(new String[]{"CLIENTE", "FUNCIONARIO"});

        JLabel rotuloCargo = new JLabel("Cargo:");
        JComboBox<String> optCargo = new JComboBox<>(new String[]{"ADMINISTRADOR", "GERENTE", "ATENDENTE", "DESENVOLVEDOR"});
        optCargo.setEnabled(false);

        JLabel rotuloCep = new JLabel("CEP:");
        JTextField espacoCep = new JTextField();

        JLabel rotuloLogradouro = new JLabel("Logradouro:");
        JTextField espacoLogradouro = new JTextField();

        JLabel rotuloNum = new JLabel("Número:");
        JTextField espacoNumero = new JTextField();

        JLabel rotuloBairro = new JLabel("Bairro:");
        JTextField espacoBairro = new JTextField();

        JLabel rotuloCidade = new JLabel("Cidade:");
        JTextField espacoCidade = new JTextField();

        JLabel rotuloEstado = new JLabel("Estado:");
        JTextField espacoEstado = new JTextField();

        JButton botaoX = new JButton("Cadastrar");
        JButton botaoZ = new JButton("Cancelar");

        adicionarComponente(janelaCadastro, rotuloX, gbc, 0, 0);
        adicionarComponente(janelaCadastro, espacoX, gbc, 1, 0);
        adicionarComponente(janelaCadastro, rotuloY, gbc, 0, 1);
        adicionarComponente(janelaCadastro, espacoPraEmail, gbc, 1, 1);
        adicionarComponente(janelaCadastro, rotuloZ, gbc, 0, 2);
        adicionarComponente(janelaCadastro, lugarSenha, gbc, 1, 2);
        adicionarComponente(janelaCadastro, rotuloA, gbc, 0, 3);
        adicionarComponente(janelaCadastro, espacoPraCPF, gbc, 1, 3);
        adicionarComponente(janelaCadastro, rotuloB, gbc, 0, 4);
        adicionarComponente(janelaCadastro, espacoPraFone, gbc, 1, 4);
        adicionarComponente(janelaCadastro, rotuloC, gbc, 0, 5);
        adicionarComponente(janelaCadastro, espacoPraDataNasc, gbc, 1, 5);
        adicionarComponente(janelaCadastro, rotuloTpCliente, gbc, 0, 6);
        adicionarComponente(janelaCadastro, campoTpCliente, gbc, 1, 6);
        adicionarComponente(janelaCadastro, rotuloCargo, gbc, 0, 7);
        adicionarComponente(janelaCadastro, optCargo, gbc, 1, 7);
        adicionarComponente(janelaCadastro, rotuloCep, gbc, 0, 8);
        adicionarComponente(janelaCadastro, espacoCep, gbc, 1, 8);
        adicionarComponente(janelaCadastro, rotuloLogradouro, gbc, 0, 9);
        adicionarComponente(janelaCadastro, espacoLogradouro, gbc, 1, 9);
        adicionarComponente(janelaCadastro, rotuloNum, gbc, 0, 10);
        adicionarComponente(janelaCadastro, espacoNumero, gbc, 1, 10);
        adicionarComponente(janelaCadastro, rotuloBairro, gbc, 0, 11);
        adicionarComponente(janelaCadastro, espacoBairro, gbc, 1, 11);
        adicionarComponente(janelaCadastro, rotuloCidade, gbc, 0, 12);
        adicionarComponente(janelaCadastro, espacoCidade, gbc, 1, 12);
        adicionarComponente(janelaCadastro, rotuloEstado, gbc, 0, 13);
        adicionarComponente(janelaCadastro, espacoEstado, gbc, 1, 13);

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(botaoX);
        painelBotoes.add(botaoZ);
        janelaCadastro.add(painelBotoes, gbc);

        botaoX.addActionListener(e -> {
            String nome = espacoX.getText();
            String email = espacoPraEmail.getText();
            String senha = new String(lugarSenha.getPassword());
            String cpf = espacoPraCPF.getText();
            String telefone = espacoPraFone.getText();
            String dataNascimentoStr = espacoPraDataNasc.getText();
            String tipoCliente = (String) campoTpCliente.getSelectedItem();
            String cargo = null;

            String cep = espacoCep.getText();
            String logradouro = espacoLogradouro.getText();
            int numero = Integer.parseInt(espacoNumero.getText());
            String bairro = espacoBairro.getText();
            String cidade = espacoCidade.getText();
            String estado = espacoEstado.getText();

            if (tipoCliente.equals("FUNCIONARIO")) {
                cargo = (String) optCargo.getSelectedItem();
            }

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || telefone.isEmpty() ||
                    dataNascimentoStr.isEmpty() || cep.isEmpty() || logradouro.isEmpty() || bairro.isEmpty() ||
                    cidade.isEmpty() || estado.isEmpty()) {
                JOptionPane.showMessageDialog(janelaCadastro,
                        "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                ClienteDAO clienteDAO = new ClienteDAO();
                Endereco enderecoCliente = new Endereco(cep, logradouro, numero, bairro, cidade, estado);
                clienteDAO.inserirCliente(nome, email, senha, cpf, telefone, dataNascimento.toString(), enderecoCliente, tipoCliente, cargo);

                JOptionPane.showMessageDialog(janelaCadastro,
                        "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                janelaCadastro.dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(janelaCadastro,
                        "Data de nascimento inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janelaCadastro,
                        "Número da casa inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(janelaCadastro,
                        "Erro ao realizar o cadastro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        botaoZ.addActionListener(e -> janelaCadastro.dispose());

        campoTpCliente.addActionListener(e -> {
            if (campoTpCliente.getSelectedItem().equals("FUNCIONARIO")) {
                rotuloCargo.setVisible(true);
                optCargo.setEnabled(true);
            } else {
                rotuloCargo.setVisible(false);
                optCargo.setEnabled(false);
            }
        });

        rotuloCargo.setVisible(false);
        optCargo.setEnabled(false);

        janelaCadastro.setLocationRelativeTo(null);
        janelaCadastro.setVisible(true);
    }

    private void adicionarComponente(Container container, Component componente, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        componente.setPreferredSize(new Dimension(200, 25));
        container.add(componente, gbc);
    }

    public static void main(String[] args) {
        new CadastroUsuarioView();
    }
}