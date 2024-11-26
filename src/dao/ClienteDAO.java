package dao;

import java.sql.*;
import java.util.Optional;
import models.Cliente;

import models.Endereco;
import utils.DBUtils;

public class ClienteDAO {

    private static String sql;

    // metodo para obter um usuario (Cliente) do banco de dados baseado no email e senha fornecidos
    public Optional<Cliente> getUser(String email, String senha) {
        setSql("SELECT * FROM usuario WHERE email = ?");

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

                        if(tipoUser.equals("cliente")){
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

    // metodo para inserir um novo cliente na tabela 'usuario' do banco de dados
    public void inserirCliente(String nome, String email, String senha, String cpf, String telefone, String dataNascimento, Endereco enderecoUsuario, String tipoCliente, String cargo) {
        setSql("INSERT INTO usuario (nome, email, senha, cpf, telefone, data_nascimento, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)");
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.setString(4, cpf);
            ps.setString(5, telefone);
            ps.setString(6, dataNascimento);
            ps.setString(7, tipoCliente);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        DBUtils dbUtils = new DBUtils();

                        dbUtils.insertNewUser(idGerado, tipoCliente, cargo, enderecoUsuario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir cliente na tabela usuario: " + e.getMessage());
        }
    }

    // metodo para definir a query SQL a ser executada
    public static void setSql(String sql) {
        ClienteDAO.sql = sql;
    }

    // metodo para obter a query SQL atual
    public static String getSql() {
        return sql;
    }
}