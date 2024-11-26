package dao;

import models.Cliente;
import models.Endereco;
import util.DBUtil;

import java.sql.*;
import java.util.Optional;

public class ClienteDAO {

    private static String consultaSQL;

    public Optional<Cliente> getUser(String email, String senha) {
        consultaSQL = "SELECT * FROM TB_USUARIO WHERE email = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(consultaSQL)) {

            stmt.setString(1, email);
            try (ResultSet resultado = stmt.executeQuery()) {

                if (resultado.next()) {
                    String senhaBanco = resultado.getString("SENHA");

                    if (senhaBanco.equals(senha)) {
                        int id = resultado.getInt("ID_USUARIO");

                        String nome = resultado.getString("NO_USUARIO");

                        String cpf = resultado.getString("NR_CPF_USUARIO");

                        Date dataNascimento = resultado.getDate("DT_NASCIMENTO");

                        String telefone = resultado.getString("NR_TELEFONE");

                        String tipoUsuario = resultado.getString("TP_USUARIO");

                        if (tipoUsuario.equals("cliente")) {
                            return Optional.of(new Cliente(id, nome, email, cpf, telefone, senhaBanco, true) {
                                @Override
                                public boolean login(String senha) {
                                    return false;
                                }

                                @Override
                                public void logout() {

                                }

                                @Override
                                public String consultarDados() {
                                    return "";
                                }
                            });
                        } else {
                            return Optional.empty();
                        }
                    } else {
                        return Optional.empty();
                    }
                } else {
                    return Optional.empty();
                }

            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar os dados: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public void inserirCliente(String nome, String email, String senha, String cpf, String telefone, String dataNascimento, Endereco enderecoUsuario, String tipoCliente, String cargo) {
        consultaSQL = "INSERT INTO TB_USUARIO (NO_USUARIO, email, SENHA, NR_CPF_USUARIO, NR_TELEFONE, DT_NASCIMENTO, TP_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(consultaSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, cpf);
            stmt.setString(5, telefone);
            stmt.setString(6, dataNascimento);
            stmt.setString(7, tipoCliente);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet chavesGeradas = stmt.getGeneratedKeys()) {
                    if (chavesGeradas.next()) {
                        int idGerado = chavesGeradas.getInt(1);
                        DBUtil utilitariosBanco = new DBUtil();

                        utilitariosBanco.insertNewUser(idGerado, tipoCliente, cargo, enderecoUsuario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir cliente na tabela usuario: " + e.getMessage());
        }
    }

    public static void definirConsultaSQL(String sql) {
        ClienteDAO.consultaSQL = sql;
    }

    public static String obterConsultaSQL() {
        return consultaSQL;
    }
}