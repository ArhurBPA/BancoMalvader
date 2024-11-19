package dao;

import model.Funcionario;

import java.sql.*;

import static dao.ConnectionFactory.connection;
import dao.CustomDatabaseException;  // Assuming the exception class is in 'exception' package

public class FuncionarioDAO {

    // Inserir funcionário
    public void inserirFuncionario(Funcionario funcionario) throws CustomDatabaseException {
        String sqlUsuario = "INSERT INTO TB_USUARIO (NO_USUARIO, NR_CPF_USUARIO, DT_NASCIMENTO, NR_TELEFONE, SENHA, TP_USUARIO) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO TB_FUNCIONARIO (CD_FUNCIONARIO, NO_CARGO, ID_USUARIO) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.conectar()) {
            Connection connect = ConnectionFactory.conectar();
            connect.setAutoCommit(false); // Transação para garantir consistência

            try (PreparedStatement stmtUsuario = connect.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, funcionario.getNome());
                stmtUsuario.setString(2, funcionario.getCpf());
                stmtUsuario.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
                stmtUsuario.setString(4, funcionario.getTelefone());
                stmtUsuario.setString(5, funcionario.getSenha());
                stmtUsuario.setString(6, "FUNCIONARIO");
                stmtUsuario.executeUpdate();

                // Obter o ID gerado para o usuário
                ResultSet generatedKeys = stmtUsuario.getGeneratedKeys();
                if (generatedKeys.next()) {
                    funcionario.setId(generatedKeys.getInt(1));
                }
            }

            try (PreparedStatement stmtFuncionario = connect.prepareStatement(sqlFuncionario)) {
                stmtFuncionario.setString(1, funcionario.getCodigoFuncionario());
                stmtFuncionario.setString(2, funcionario.getCargo());
                stmtFuncionario.setInt(3, funcionario.getId());
                stmtFuncionario.executeUpdate();
            }

            connect.commit(); // Confirmar transação
            // Log success message
            // Logger.log("Funcionário inserido com sucesso!");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback em caso de erro
            } catch (SQLException rollbackEx) {
                throw new dao.CustomDatabaseException("Erro ao realizar rollback após falha na inserção de funcionário", rollbackEx);
            }
            throw new dao.CustomDatabaseException("Erro ao inserir funcionário no banco de dados", e);
        }
    }

    // Consultar funcionário pelo código
    public Funcionario consultarFuncionario(String codigoFuncionario) throws CustomDatabaseException {
        String sql = "SELECT u.ID_USUARIO, u.NO_USUARIO, u.NR_CPF_USUARIO, u.DT_NASCIMENTO, u.NR_TELEFONE, " +
                "f.CD_FUNCIONARIO, f.NO_CARGO, f.ID_FUNCIONARIO " +
                "FROM TB_FUNCIONARIO f " +
                "INNER JOIN TB_USUARIO u ON f.ID_USUARIO = u.ID_USUARIO " +
                "WHERE f.CD_FUNCIONARIO = ?";

        try (Connection connection = ConnectionFactory.conectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, codigoFuncionario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("ID_USUARIO"));
                funcionario.setNome(resultSet.getString("NO_USUARIO"));
                funcionario.setCpf(resultSet.getString("NR_CPF_USUARIO"));
                funcionario.setDataNascimento(resultSet.getDate("DT_NASCIMENTO").toLocalDate());
                funcionario.setTelefone(resultSet.getString("NR_TELEFONE"));
                funcionario.setCodigoFuncionario(resultSet.getString("CD_FUNCIONARIO"));
                funcionario.setCargo(resultSet.getString("NO_CARGO"));
                return funcionario;
            }
        } catch (SQLException e) {
            throw new dao.CustomDatabaseException("Erro ao consultar funcionário", e);
        }
        return null;
    }

    // Atualizar dados de um funcionário
    public void atualizarFuncionario(Funcionario funcionario) throws CustomDatabaseException {
        String sqlUsuario = "UPDATE TB_USUARIO SET NO_USUARIO = ?, NR_CPF_USUARIO = ?, DT_NASCIMENTO = ?, NR_TELEFONE = ? WHERE ID_USUARIO = ?";
        String sqlFuncionario = "UPDATE TB_FUNCIONARIO SET NO_CARGO = ? WHERE CD_FUNCIONARIO = ?";

        try (Connection connection = ConnectionFactory.conectar()) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, funcionario.getNome());
                stmtUsuario.setString(2, funcionario.getCpf());
                stmtUsuario.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
                stmtUsuario.setString(4, funcionario.getTelefone());
                stmtUsuario.setInt(5, funcionario.getId());
                stmtUsuario.executeUpdate();
            }

            try (PreparedStatement stmtFuncionario = connection.prepareStatement(sqlFuncionario)) {
                stmtFuncionario.setString(1, funcionario.getCargo());
                stmtFuncionario.setString(2, funcionario.getCodigoFuncionario());
                stmtFuncionario.executeUpdate();
            }

            connection.commit();
            // Log success message
            // Logger.log("Funcionário atualizado com sucesso!");
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback em caso de erro
            } catch (SQLException rollbackEx) {
                throw new dao.CustomDatabaseException("Erro ao realizar rollback após falha na atualização de funcionário", rollbackEx);
            }
            throw new dao.CustomDatabaseException("Erro ao atualizar funcionário no banco de dados", e);
        }
    }

    // Excluir funcionário
    public void excluirFuncionario(String codigoFuncionario) throws CustomDatabaseException {
        String sqlFuncionario = "DELETE FROM TB_FUNCIONARIO WHERE CD_FUNCIONARIO = ?";
        String sqlUsuario = "DELETE FROM TB_USUARIO WHERE ID_USUARIO = ?";

        try (Connection connection = ConnectionFactory.conectar()) {
            connection.setAutoCommit(false);

            Funcionario funcionario = consultarFuncionario(codigoFuncionario);
            if (funcionario != null) {
                int idUsuario = funcionario.getId();

                try (PreparedStatement stmtFuncionario = connection.prepareStatement(sqlFuncionario)) {
                    stmtFuncionario.setString(1, codigoFuncionario);
                    stmtFuncionario.executeUpdate();
                }

                try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario)) {
                    stmtUsuario.setInt(1, idUsuario);
                    stmtUsuario.executeUpdate();
                }

                connection.commit();
                // Log success message
                // Logger.log("Funcionário excluído com sucesso!");
            }
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback em caso de erro
            } catch (SQLException rollbackEx) {
                throw new dao.CustomDatabaseException("Erro ao realizar rollback após falha na exclusão de funcionário", rollbackEx);
            }
            throw new dao.CustomDatabaseException("Erro ao excluir funcionário no banco de dados", e);
        }
    }
}