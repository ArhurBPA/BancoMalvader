package dao;

import model.Endereco;
import model.Funcionario;

import java.sql.*;
import java.time.LocalDate;

public class FuncionarioDAO {

    public static void criarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO TB_USUARIO (NO_USUARIO, NR_CPF_USUARIO, DT_NASCIMENTO, NR_TELEFONE, SENHA, TP_USUARIO) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, funcionario.getSenha()); // Assuming Funcionario class has getSenha() method for hashed password
            stmt.setString(6, "FUNCIONARIO"); // Define o tipo de usuário como "FUNCIONARIO"

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar o funcionário, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);
                    funcionario.setId(idUsuario);

                    // Agora você pode usar o idUsuario para inserir na tabela TB_FUNCIONARIO
                    inserirNaTabelaFuncionario(funcionario, conn);

                    // E também inserir o endereço na tabela TB_ENDERECO
                    inserirEndereco(funcionario.getEndereco(), idUsuario, conn);
                } else {
                    throw new SQLException("Falha ao criar o funcionário, não foi possível obter o ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar funcionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inserirNaTabelaFuncionario(Funcionario funcionario, Connection conn) throws SQLException {
        String sqlFuncionario = "INSERT INTO TB_FUNCIONARIO (ID_USUARIO, CD_FUNCIONARIO, NO_CARGO) VALUES (?, ?, ?)";
        try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
            stmtFuncionario.setInt(1, funcionario.getId());
            stmtFuncionario.setString(2, funcionario.getCodigoFuncionario());
            stmtFuncionario.setString(3, funcionario.getCargo());
            stmtFuncionario.executeUpdate();
        }
    }

    private static void inserirEndereco(Endereco endereco, int idUsuario, Connection conn) throws SQLException {
        String sqlEndereco = "INSERT INTO TB_ENDERECO (NR_CEP, NO_LOCAL, NR_CASA, NO_BAIRRO, NO_CIDADE, SG_ESTADO, ID_USUARIO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtEndereco = conn.prepareStatement(sqlEndereco)) {
            stmtEndereco.setString(1, endereco.getCep());
            stmtEndereco.setString(2, endereco.getLocal());
            stmtEndereco.setInt(3, endereco.getNumeroCasa());
            stmtEndereco.setString(4, endereco.getBairro());
            stmtEndereco.setString(5, endereco.getCidade());
            stmtEndereco.setString(6, endereco.getEstado());
            stmtEndereco.setInt(7, idUsuario);
            stmtEndereco.executeUpdate();
        }
    }

    public static Funcionario buscarFuncionarioPorCodigo(String codigoFuncionario) {
        String sql = "SELECT * FROM TB_USUARIO u " +
                "JOIN TB_FUNCIONARIO f ON u.ID_USUARIO = f.ID_USUARIO " +
                "JOIN TB_ENDERECO e ON u.ID_USUARIO = e.ID_USUARIO " +
                "WHERE f.CD_FUNCIONARIO = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoFuncionario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Cria um novo objeto Funcionario e define seus atributos
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(rs.getInt("ID_USUARIO"));
                    funcionario.setNome(rs.getString("NO_USUARIO"));
                    funcionario.setCpf(rs.getString("NR_CPF_USUARIO"));
                    funcionario.setDataNascimento(rs.getDate("DT_NASCIMENTO").toLocalDate());
                    funcionario.setTelefone(rs.getString("NR_TELEFONE"));
                    funcionario.setSenha(rs.getString("SENHA"));
                    funcionario.setCodigoFuncionario(rs.getString("CD_FUNCIONARIO"));
                    funcionario.setCargo(rs.getString("NO_CARGO"));

                    // Cria um novo objeto Endereco e define seus atributos
                    Endereco endereco = new Endereco();
                    endereco.setCep(rs.getString("NR_CEP"));
                    endereco.setLocal(rs.getString("NO_LOCAL"));
                    endereco.setNumeroCasa(rs.getInt("NR_CASA"));
                    endereco.setBairro(rs.getString("NO_BAIRRO"));
                    endereco.setCidade(rs.getString("NO_CIDADE"));
                    endereco.setEstado(rs.getString("SG_ESTADO"));

                    // Associa o endereço ao funcionário
                    funcionario.setEndereco(endereco);

                    return funcionario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por código: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Funcionário não encontrado
    }
}