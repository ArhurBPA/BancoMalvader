package view;

import controller.ControllerUsuario;
import dao.FuncionarioDAO;
import models.Cliente;
import models.ContaCliente;
import models.GerarRelatorio;
import models.UsuarioConta;

import javax.swing.*;
import java.awt.*;

public class MenuFuncionarioView extends JFrame {

    private final JLabel noTitulo;
    private final JButton botaoX;
    private final JButton botaoY;
    private final JButton botaoZ;
    private final JButton botao1;
    private final JButton botao2;
    private final JButton botao3;
    private final JButton botaoSair;

    public MenuFuncionarioView(Cliente usuario) {
        super("Bem vindo " + usuario.getNome());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // layout da tela

        Font primariaFont = new Font("SansSerif", Font.BOLD, 30); // fonte
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        noTitulo = new JLabel("Area do Funcionario");
        noTitulo.setFont(primariaFont);
        noTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoX = new JButton("Abrir Conta");
        botaoX.setFont(secundariaFont);
        botaoX.setPreferredSize(new Dimension(200, 50));
        botaoX.setMaximumSize(new Dimension(200, 50));
        botaoX.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoX.addActionListener(e -> abrirConta());

        botaoY = new JButton("Encerrar Conta");
        botaoY.setFont(secundariaFont);
        botaoY.setPreferredSize(new Dimension(200, 50));
        botaoY.setMaximumSize(new Dimension(200, 50));
        botaoY.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoY.addActionListener(e -> encerrarConta());

        botaoZ = new JButton("Consultar Dados");
        botaoZ.setFont(secundariaFont);
        botaoZ.setPreferredSize(new Dimension(200, 50));
        botaoZ.setMaximumSize(new Dimension(200, 50));
        botaoZ.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoZ.addActionListener(e -> consultarDados());

        botao1 = new JButton("Alterar Dados");
        botao1.setFont(secundariaFont);
        botao1.setPreferredSize(new Dimension(200, 50));
        botao1.setMaximumSize(new Dimension(200, 50));
        botao1.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao1.addActionListener(e -> alterarDados());

        botao2 = new JButton("Cadastrar Funcionario");
        botao2.setFont(secundariaFont);
        botao2.setPreferredSize(new Dimension(200, 50));
        botao2.setMaximumSize(new Dimension(200, 50));
        botao2.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao2.addActionListener(e -> cadastrarFuncionario());

        botao3 = new JButton("Gerar Relatório");
        botao3.setFont(secundariaFont);
        botao3.setPreferredSize(new Dimension(200, 50));
        botao3.setMaximumSize(new Dimension(200, 50));
        botao3.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao3.addActionListener(e -> gerarRelatorio());

        botaoSair = new JButton("Sair");
        botaoSair.setFont(secundariaFont);
        botaoSair.setPreferredSize(new Dimension(200, 50));
        botaoSair.setMaximumSize(new Dimension(200, 50));
        botaoSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        botaoSair.addActionListener(e -> {
            dispose();
            MenuPrincipalView menuPrincipalView = new MenuPrincipalView(new ControllerUsuario());
            menuPrincipalView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuPrincipalView.setSize(500, 500);
            menuPrincipalView.setVisible(true);
            menuPrincipalView.setLocationRelativeTo(null);
        });

        add(Box.createVerticalStrut(20));
        add(noTitulo);
        add(Box.createVerticalStrut(20));
        add(botaoX);
        add(Box.createVerticalStrut(20));
        add(botaoY);
        add(Box.createVerticalStrut(20));
        add(botaoZ);
        add(Box.createVerticalStrut(20));
        add(botao1);
        add(Box.createVerticalStrut(20));
        add(botao2);
        add(Box.createVerticalStrut(20));
        add(botao3);
        add(Box.createVerticalStrut(20));
        add(botaoSair);
        add(Box.createVerticalStrut(20));
    }

    private void abrirConta() {
        String[] tiposConta = {"Poupança", "Corrente"};
        String tipoConta = (String) JOptionPane.showInputDialog(this, "Selecione o tipo de conta:", "Abertura de Conta",
                JOptionPane.PLAIN_MESSAGE, null, tiposConta, tiposConta[0]);

        if (tipoConta != null) {
            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

            panel.add(new JLabel("Agência:"));
            JTextField agenciaField = new JTextField();
            panel.add(agenciaField);

            panel.add(new JLabel("Número da conta:"));
            JTextField numeroContaField = new JTextField();
            panel.add(numeroContaField);

            panel.add(new JLabel("Nome do cliente:"));
            JTextField nomeClienteField = new JTextField();
            panel.add(nomeClienteField);

            panel.add(new JLabel("CPF:"));
            JTextField cpfField = new JTextField();
            panel.add(cpfField);

            panel.add(new JLabel("Data de nascimento:"));
            JTextField dataNascimentoField = new JTextField();
            panel.add(dataNascimentoField);

            panel.add(new JLabel("Telefone:"));
            JTextField telefoneField = new JTextField();
            panel.add(telefoneField);

            panel.add(new JLabel("Endereço:"));
            JTextField enderecoField = new JTextField();
            panel.add(enderecoField);

            panel.add(new JLabel("Senha:"));
            JPasswordField senhaField = new JPasswordField();
            panel.add(senhaField);

            JTextField limiteField = null;
            JTextField dataVencimentoField = null;

            if (tipoConta.equals("Corrente")) {
                panel.add(new JLabel("Limite da conta:"));
                limiteField = new JTextField();
                panel.add(limiteField);

                panel.add(new JLabel("Data de vencimento:"));
                dataVencimentoField = new JTextField();
                panel.add(dataVencimentoField);
            }

            int result = JOptionPane.showConfirmDialog(this, panel, "Abertura de Conta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                ContaCliente conta;
                String agencia = agenciaField.getText();
                String numeroConta = numeroContaField.getText();
                String nomeCliente = nomeClienteField.getText();
                String cpf = cpfField.getText();
                String dataNascimento = dataNascimentoField.getText();
                String telefone = telefoneField.getText();
                String endereco = enderecoField.getText();
                String senha = new String(senhaField.getPassword());

                if (tipoConta.equals("Corrente")) {
                    double limite = Double.parseDouble(limiteField.getText());
                    String dataVencimento = dataVencimentoField.getText();
                    conta = new ContaCliente(agencia, numeroConta, nomeCliente, cpf, dataNascimento,
                            telefone, endereco, senha, tipoConta, limite, dataVencimento);
                } else {
                    conta = new ContaCliente(agencia, numeroConta, nomeCliente, cpf, dataNascimento,
                            telefone, endereco, senha, tipoConta);
                }

                FuncionarioDAO funcionarioDao = new FuncionarioDAO();


                JOptionPane.showMessageDialog(this, funcionarioDao.inserirConta(conta));
            }
        }
    }

    private void encerrarConta() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Senha do Administrador:"));
        JPasswordField senhaAdminField = new JPasswordField();
        panel.add(senhaAdminField);

        panel.add(new JLabel("Número da conta:"));
        JTextField numeroContaField = new JTextField();
        panel.add(numeroContaField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Encerrar Conta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String senhaAdmin = new String(senhaAdminField.getPassword());
            if (senhaAdmin.equals("admin123")) {
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

                JOptionPane.showMessageDialog(this, funcionarioDAO.encerrarConta(numeroContaField.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void consultarDados() {
        String[] opcoes = {"Conta", "Funcionário", "Cliente"};
        String escolha = (String) JOptionPane.showInputDialog(this, "Escolha uma opção para consultar:", "Consulta de Dados",
                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha != null) {
            String cpf = JOptionPane.showInputDialog(this, "Digite o CPF do usuário:", "Consulta de Dados", JOptionPane.PLAIN_MESSAGE);

            if (cpf != null && !cpf.trim().isEmpty()) {

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

                UsuarioConta contas = funcionarioDAO.consultarDadosUsuario(cpf);

                if(contas != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Layout vertical

                    panel.add(new JLabel("Nome: " + contas.getNome()));
                    panel.add(new JLabel("Email: " + contas.getEmail()));
                    panel.add(new JLabel("CPF: " + contas.getCpf()));
                    panel.add(new JLabel("Data de Nascimento: " + contas.getDataNascimento()));
                    panel.add(new JLabel("Telefone: " + contas.getTelefone()));
                    panel.add(new JLabel("Tipo de Usuário: " + contas.getTipoUsuario()));

                    // Exibindo as contas do usuário
                    panel.add(new JLabel("Contas do Usuário:"));
                    for (UsuarioConta.Conta conta : contas.getContas()) {
                        panel.add(new JLabel("Conta: " + conta.getNumeroConta() +
                                ", Agência: " + conta.getAgencia() +
                                ", Saldo: " + conta.getSaldo() +
                                ", Tipo de Conta: " + conta.getTipoConta()));
                    }

                    JOptionPane.showMessageDialog(null, panel, "Dados do Usuário", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(null, "Usuário não encontrado ou dados inconsistentes.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "CPF não informado. A consulta não será realizada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void alterarDados() {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField cpfField = new JTextField();
        panel.add(new JLabel("CPF do usuário:"));
        panel.add(cpfField);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        panel.add(new JLabel("Novo telefone:"));
        JTextField telefoneField = new JTextField();
        panel.add(telefoneField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Alteração de Dados", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String cpf = cpfField.getText();

            UsuarioConta usuarioConta = funcionarioDAO.consultarDadosUsuario(cpf);

            if (usuarioConta != null) {

                String telefone = telefoneField.getText();
                System.out.println(telefone);
                funcionarioDAO.alterarDadosUsuario(cpf, telefone);

                JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void cadastrarFuncionario() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("CPF:"));
        JTextField cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Cargo:"));
        JTextField cargoField = new JTextField();
        panel.add(cargoField);


        int result = JOptionPane.showConfirmDialog(this, panel, "Cadastro de Funcionário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String senhaAdmin = JOptionPane.showInputDialog(this, "Digite a senha do administrador:");
            if (senhaAdmin != null && senhaAdmin.equals("admin123")) {
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.alterarCargoFuncionario(cpfField.getText(), cargoField.getText());
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void gerarRelatorio() {
        String senhaAdmin = JOptionPane.showInputDialog(this, "Digite a senha do administrador:");
        if (senhaAdmin != null && senhaAdmin.equals("admin123")) {
            String cpfDoUsuario = JOptionPane.showInputDialog(this, "Digite o CPF do usuário que deseja o relatório:");

            FuncionarioDAO dao = new FuncionarioDAO();
            GerarRelatorio relatorio = dao.gerarRelatorioDAO(cpfDoUsuario);

//            if (relatorio != null) {
//                ControllerRelatorio controller = new ControllerRelatorio();
//                controller.exportarRelatorioParaCSV(relatorio);
//                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso!");
//
//                JFrame relatorioFrame = new JFrame("Relatório do Usuário");
//                relatorioFrame.setSize(500, 400);
//                relatorioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                relatorioFrame.setLocationRelativeTo(null);
//
//                JTextArea textArea = new JTextArea();
//                textArea.setEditable(false);
//
//                StringBuilder relatorioTexto = new StringBuilder();
//                relatorioTexto.append("Relatório do Usuário - CPF: ").append(cpfDoUsuario).append("\n\n");
//
//
//                for (UsuarioConta.Conta conta : relatorio.getContas()) {
//                    relatorioTexto.append("Conta: ").append(conta.getNumeroConta())
//                            .append(" | Agência: ").append(conta.getAgencia())
//                            .append(" | Saldo: R$ ").append(conta.getSaldo())
//                            .append("\n");
//                }
//                relatorioTexto.append("\n");
//
//                for (Transacao transacao : relatorio.getTransacoes()) {
//                    relatorioTexto.append("Transação: ").append(transacao.getTipoTransacao())
//                            .append(" | Valor: R$ ").append(transacao.getValor())
//                            .append(" | Data: ").append(transacao.getDataTransacao())
//                            .append("\n");
//                }
//
//                // definindo o texto no JTextArea e adicionando um scroll
//                textArea.setText(relatorioTexto.toString());
//                JScrollPane scrollPane = new JScrollPane(textArea);
//                relatorioFrame.add(scrollPane);
//
//
//                relatorioFrame.setVisible(true);
//            } else {
//                JOptionPane.showMessageDialog(this, "Relatorio não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }

}}}