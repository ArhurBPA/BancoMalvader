package dao;

import java.sql.*;
import java.util.Optional;

import models.*;

import java.util.UUID;

import utils.DBUtils;

public class FuncionarioDAO {

    private static String sql;

    // metodo para obter um usuario (Cliente) do banco de dados baseado no email e senha fornecidos
    public Optional<Cliente> getUser(String email, String senha) {
        setSql("SELECT * FROM tb_usuario WHERE cpf = ?");

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String senhaQuery = rs.getString("senha");

                    if(senhaQuery.equals(senha)){
                        int id = rs.getInt("id_usuario");
                        String nome = rs.getString("nome");
                        String cpf = rs.getString("cpf");
                        Date dataNascimento = rs.getDate("data_nascimento");
                        String telefone = rs.getString("telefone");
                        String tipoUser = rs.getString("tipo_usuario");

                        if(tipoUser.equals("funcionario")){
                            System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email);
                            return Optional.of(new Cliente(id, nome, email, cpf, telefone, senhaQuery, true));
                        }
                        else{
                            System.out.println("Nenhum usuario encontrado.");
                            return Optional.empty();
                        }
                    }
                    else{
                        System.out.println("Nenhum usuario encontrado.");
                        return Optional.empty();
                    }
                } else {
                    System.out.println("Nenhum usuario encontrado.");
                    return Optional.empty();
                }

            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar os dados: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // metodo para inserir uma nova conta para um cliente com base nos detalhes fornecidos
    public String inserirConta(ContaCliente conta) {
        String selectUsuarioSql = "SELECT id_usuario FROM tb_usuario WHERE cpf = ?";
        String selectClienteSql = "SELECT id_cliente FROM tb_cliente WHERE id_usuario = ?";
        String insertContaSql = "INSERT INTO tb_conta (numero_conta, agencia, saldo, tipo_conta, id_cliente) VALUES (?, ?, ?, ?, ?)";
        String insertContaCorrenteSql = "INSERT INTO conta_corrente (id_conta, taxa_rendimento, limite_conta, data_vencimento) VALUES (?, ?, ?, ?)";
        String insertContaPoupancaSql = "INSERT INTO conta_poupanca (id_conta, taxa_rendimento) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {

            // buscar o id_usuario pelo CPF
            int idUsuario = -1;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(selectUsuarioSql)) {
                stmtUsuario.setString(1, conta.getCpf());
                try (ResultSet rs = stmtUsuario.executeQuery()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt("id_usuario");
                    } else {
                        return "Usuário não encontrado com o CPF fornecido.";
                    }
                }
            }

            // buscar o id_cliente usando o id_usuario encontrado
            int idCliente = -1;
            try (PreparedStatement stmtCliente = conn.prepareStatement(selectClienteSql)) {
                stmtCliente.setInt(1, idUsuario);
                try (ResultSet rs = stmtCliente.executeQuery()) {
                    if (rs.next()) {
                        idCliente = rs.getInt("id_cliente");
                    } else {
                        return "Cliente não encontrado para o id_usuario fornecido.";
                    }
                }
            }

            // inserir a conta usando o id_cliente encontrado
            int idConta = -1;  // Variável para armazenar o id_conta gerado
            try (PreparedStatement stmt = conn.prepareStatement(insertContaSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, conta.getNumeroConta());
                stmt.setString(2, conta.getAgencia());
                stmt.setDouble(3, 0.0);  // saldo inicial zero
                stmt.setString(4, conta.getTipoConta());
                stmt.setInt(5, idCliente);

                stmt.executeUpdate();

                // obter o id_conta gerado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idConta = generatedKeys.getInt(1);
                    } else {
                        return "Falha ao obter o ID da conta.";
                    }
                }
            }

            // se a conta for do tipo "Corrente", inserir na tabela conta_corrente
            if ("Corrente".equals(conta.getTipoConta())) {
                try (PreparedStatement stmtCorrente = conn.prepareStatement(insertContaCorrenteSql)) {
                    stmtCorrente.setInt(1, idConta);
                    stmtCorrente.setDouble(2, 0.5);
                    stmtCorrente.setDouble(3, conta.getLimite());
                    stmtCorrente.setString(4, conta.getDataVencimento());

                    stmtCorrente.executeUpdate();
                }
            }
            // se a conta for do tipo "Poupança", inserir na tabela conta_poupanca
            else if ("Poupança".equals(conta.getTipoConta())) {
                try (PreparedStatement stmtPoupanca = conn.prepareStatement(insertContaPoupancaSql)) {
                    stmtPoupanca.setInt(1, idConta);
                    stmtPoupanca.setDouble(2, 0.5);

                    stmtPoupanca.executeUpdate();
                }
            }

            return "Conta inserida com sucesso!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao inserir a conta: " + e.getMessage();
        }
    }

    // metodo para encerrar uma conta com base no número da conta fornecido
    public String encerrarConta(String numeroConta) {
        String selectContaSql = "SELECT id_conta, tipo_conta FROM conta WHERE numero_conta = ?";
        String deleteContaCorrenteSql = "DELETE FROM conta_corrente WHERE id_conta = ?";
        String deleteContaPoupancaSql = "DELETE FROM conta_poupanca WHERE id_conta = ?";
        String deleteContaSql = "DELETE FROM conta WHERE id_conta = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            int idConta = -1;
            String tipoConta = null;

            // buscar a conta pelo numero da conta
            try (PreparedStatement stmtSelect = conn.prepareStatement(selectContaSql)) {
                stmtSelect.setString(1, numeroConta);

                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (rs.next()) {
                        idConta = rs.getInt("id_conta");
                        tipoConta = rs.getString("tipo_conta");
                    } else {
                        return "Conta não encontrada.";
                    }
                }
            }

            // excluir registros em tabelas associadas com base no tipo de conta
            if ("Corrente".equals(tipoConta)) {
                try (PreparedStatement stmtDeleteCorrente = conn.prepareStatement(deleteContaCorrenteSql)) {
                    stmtDeleteCorrente.setInt(1, idConta);
                    stmtDeleteCorrente.executeUpdate();
                }
            } else if ("Poupança".equals(tipoConta)) {
                try (PreparedStatement stmtDeletePoupanca = conn.prepareStatement(deleteContaPoupancaSql)) {
                    stmtDeletePoupanca.setInt(1, idConta);
                    stmtDeletePoupanca.executeUpdate();
                }
            }

            // excluir a conta da tabela principal
            try (PreparedStatement stmtDeleteConta = conn.prepareStatement(deleteContaSql)) {
                stmtDeleteConta.setInt(1, idConta);
                stmtDeleteConta.executeUpdate();
            }

            return "Conta encerrada com sucesso!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao encerrar a conta: " + e.getMessage();
        }
    }

    // metodo para consulta os dados do usuario e suas contas com base no CPF fornecido
    public UsuarioConta consultarDadosUsuario(String cpf) {
        // definindo a consulta SQL para obter os dados
        String sqlUsuario = "SELECT id_usuario, nome, email, cpf, data_nascimento, telefone, tipo_usuario FROM usuario WHERE cpf = ? ";
        String sqlCliente = "SELECT id_cliente FROM cliente WHERE id_usuario = ?";
        String sqlContas = "SELECT * FROM conta WHERE id_cliente = ?";

        // modelo para armazenar os dados do usuario e contas
        UsuarioConta usuarioConta = null;

        try (Connection conn = ConnectionFactory.getConnection()) {
            // consultar os dados do usuario
            int idUsuario = -1;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, cpf);

                try (ResultSet rs = stmtUsuario.executeQuery()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt("id_usuario");
                        String nome = rs.getString("nome");
                        String email = rs.getString("email");
                        String dataNascimento = rs.getString("data_nascimento");
                        String telefone = rs.getString("telefone");
                        String tipoUsuarioDB = rs.getString("tipo_usuario");

                        // criando o objeto UsuarioConta com os dados do usuario
                        usuarioConta = new UsuarioConta(nome, email, cpf, dataNascimento, telefone, tipoUsuarioDB);
                    } else {
                        System.out.println("Usuário não encontrado.");
                        return null;
                    }
                }
            }

            // consultar o id_cliente com base no id_usuario
            int idCliente = -1;
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
                stmtCliente.setInt(1, idUsuario);

                try (ResultSet rsCliente = stmtCliente.executeQuery()) {
                    if (rsCliente.next()) {
                        idCliente = rsCliente.getInt("id_cliente");
                    } else {
                        System.out.println("Cliente não encontrado.");
                        return null;
                    }
                }
            }

            // consultar as contas do usuario
            try (PreparedStatement stmtContas = conn.prepareStatement(sqlContas)) {
                stmtContas.setInt(1, idCliente);

                try (ResultSet rsContas = stmtContas.executeQuery()) {
                    while (rsContas.next()) {
                        int idConta = rsContas.getInt("id_conta");
                        String numeroConta = rsContas.getString("numero_conta");
                        String agencia = rsContas.getString("agencia");
                        double saldo = rsContas.getDouble("saldo");
                        String tipoConta = rsContas.getString("tipo_conta");

                        // adiciona as informacoes da conta ao objeto UsuarioConta
                        usuarioConta.adicionarConta(idConta, numeroConta, agencia, saldo, tipoConta);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // retorna o objeto com todos os dados
        return usuarioConta;
    }

    // metodo para alterar os dados do usuario com base no CPF fornecido
    public boolean alterarDadosUsuario(String cpf, String telefone) {
        String sqlUsuario = "UPDATE usuario SET telefone = ? WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {

            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, telefone);
                stmtUsuario.setString(2, cpf);

                int linhasAfetadasUsuario = stmtUsuario.executeUpdate();
                if (linhasAfetadasUsuario == 0) {
                    System.out.println("Nenhum dado de usuário foi alterado.");
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // metodo para alterar o cargo de um funcionario com base no CPF fornecido
    public void alterarCargoFuncionario(String cpf, String cargo) {
        // definindo as consultas SQL
        String sqlAlterarUsuario = "UPDATE usuario SET tipo_usuario = 'funcionario' WHERE cpf = ?";
        String sqlSelecionarIdUsuario = "SELECT id_usuario FROM usuario WHERE cpf = ?";
        String sqlAlterarFuncionario = "UPDATE funcionario SET cargo = ? WHERE id_usuario = ?";
        String sqlVerificarFuncionarioExistente = "SELECT id_usuario FROM funcionario WHERE id_usuario = ?";
        String sqlInserirFuncionario = "INSERT INTO funcionario (codigo_funcionario, cargo, id_usuario) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // alterar o tipo de usuario para 'funcionario'
            try (PreparedStatement stmtAlterarUsuario = conn.prepareStatement(sqlAlterarUsuario)) {
                stmtAlterarUsuario.setString(1, cpf);
                int rowsAffected = stmtAlterarUsuario.executeUpdate();

                if (rowsAffected == 0) {
                    System.out.println("Usuário não encontrado para o CPF fornecido.");
                    return;
                }
                System.out.println("Tipo de usuário alterado para 'funcionario'.");

                // selecionar o id_usuario com base no cpf
                int idUsuario = -1;
                try (PreparedStatement stmtSelecionarIdUsuario = conn.prepareStatement(sqlSelecionarIdUsuario)) {
                    stmtSelecionarIdUsuario.setString(1, cpf);
                    try (ResultSet rs = stmtSelecionarIdUsuario.executeQuery()) {
                        if (rs.next()) {
                            idUsuario = rs.getInt("id_usuario");
                        } else {
                            System.out.println("Não foi possível encontrar o id_usuario.");
                            return;
                        }
                    }
                }

                // verificar se o funcionario ja existe na tabela 'funcionario'
                boolean funcionarioExistente = false;
                try (PreparedStatement stmtVerificarFuncionario = conn.prepareStatement(sqlVerificarFuncionarioExistente)) {
                    stmtVerificarFuncionario.setInt(1, idUsuario);
                    try (ResultSet rsFuncionario = stmtVerificarFuncionario.executeQuery()) {
                        if (rsFuncionario.next()) {
                            funcionarioExistente = true;
                        }
                    }
                }

                // caso o funcionario ja exista, atualizar o cargo
                if (funcionarioExistente) {
                    try (PreparedStatement stmtAlterarFuncionario = conn.prepareStatement(sqlAlterarFuncionario)) {
                        stmtAlterarFuncionario.setString(1, cargo);
                        stmtAlterarFuncionario.setInt(2, idUsuario);
                        int rowsUpdated = stmtAlterarFuncionario.executeUpdate();

                        if (rowsUpdated > 0) {
                            System.out.println("Cargo atualizado com sucesso para o funcionário.");
                        } else {
                            System.out.println("Falha ao atualizar o cargo do funcionário.");
                        }
                    }
                } else {
                    // caso o funcionario nao exista, inserir um novo funcionario
                    String codigoFuncionario = UUID.randomUUID().toString();
                    try (PreparedStatement stmtInserirFuncionario = conn.prepareStatement(sqlInserirFuncionario)) {
                        stmtInserirFuncionario.setString(1, codigoFuncionario);
                        stmtInserirFuncionario.setString(2, cargo);
                        stmtInserirFuncionario.setInt(3, idUsuario);
                        int rowsInserted = stmtInserirFuncionario.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Novo funcionário inserido na tabela 'funcionario'.");
                        } else {
                            System.out.println("Falha ao inserir novo funcionário.");
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo para gerra um relatorio de um usuario com base no CPF fornecido
    public RelatorioUsuario gerarRelatorioDAO(String cpf) {
        String sqlSelecionarIdUsuario = "SELECT id_usuario FROM usuario WHERE cpf = ?";
        String sqlSelecionarIdCliente = "SELECT id_cliente FROM cliente WHERE id_usuario = ?";
        String sqlSelecionarContas = "SELECT id_conta, numero_conta, agencia, saldo, tipo_conta FROM conta WHERE id_cliente = ?";
        String sqlSelecionarTransacoes = "SELECT id_transacao, id_conta, tipo_transacao, valor, data_hora FROM transacao WHERE id_conta = ?";

        RelatorioUsuario relatorio = new RelatorioUsuario(); // Classe modelo para armazenar os dados do relatório

        try (Connection conn = ConnectionFactory.getConnection()) {
            // 1. Selecionar o id_usuario com base no cpf
            int idUsuario = -1;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlSelecionarIdUsuario)) {
                stmtUsuario.setString(1, cpf);
                try (ResultSet rsUsuario = stmtUsuario.executeQuery()) {
                    if (rsUsuario.next()) {
                        idUsuario = rsUsuario.getInt("id_usuario");
                    } else {
                        System.out.println("Usuário não encontrado.");
                        return null;
                    }
                }
            }

            // 2. Selecionar o id_cliente com base no id_usuario
            int idCliente = -1;
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlSelecionarIdCliente)) {
                stmtCliente.setInt(1, idUsuario);
                try (ResultSet rsCliente = stmtCliente.executeQuery()) {
                    if (rsCliente.next()) {
                        idCliente = rsCliente.getInt("id_cliente");
                    } else {
                        System.out.println("Cliente não encontrado.");
                        return null;
                    }
                }
            }

            // 3. Selecionar todas as contas do cliente
            try (PreparedStatement stmtContas = conn.prepareStatement(sqlSelecionarContas)) {
                stmtContas.setInt(1, idCliente);
                try (ResultSet rsContas = stmtContas.executeQuery()) {
                    while (rsContas.next()) {
                        int idConta = rsContas.getInt("id_conta");
                        String numeroConta = rsContas.getString("numero_conta");
                        String agencia = rsContas.getString("agencia");
                        double saldo = rsContas.getDouble("saldo");
                        String tipoConta = rsContas.getString("tipo_conta");

                        // Adicionar conta ao relatório
                        UsuarioConta.Conta conta = new UsuarioConta.Conta(idConta, numeroConta, agencia, saldo, tipoConta);
                        relatorio.adicionarConta(conta);

                        // 4. Selecionar transações para cada conta
                        try (PreparedStatement stmtTransacoes = conn.prepareStatement(sqlSelecionarTransacoes)) {
                            stmtTransacoes.setInt(1, idConta);
                            try (ResultSet rsTransacoes = stmtTransacoes.executeQuery()) {
                                while (rsTransacoes.next()) {
                                    int idTransacao = rsTransacoes.getInt("id_transacao");
                                    String tipo = rsTransacoes.getString("tipo_transacao");
                                    double valor = rsTransacoes.getDouble("valor");
                                    String dataTransacao = rsTransacoes.getString("data_hora");

                                    // Adicionar transação ao relatório
                                    Transacao transacao = new Transacao(idTransacao, idConta, tipo, valor, dataTransacao);
                                    relatorio.adicionarTransacao(transacao);
                                }
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar o relatório completo
        return relatorio;
    }


    // metodo para definir a query SQL a ser executada
    public static void setSql(String sql) {
        FuncionarioDAO.sql = sql;
    }

    // metodo para obter a query SQL atual
    public static String getSql() {
        return sql;
    }
}