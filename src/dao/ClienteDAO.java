package dao;

import model.Cliente;
import model.Endereco;

import java.sql.*;
import java.time.LocalDate;

public class ClienteDAO {

    public static void criarCliente(Cliente cliente) {
        String sql = "INSERT INTO TB_USUARIO (NO_USUARIO, NR_CPF_USUARIO, DT_NASCIMENTO, NR_TELEFONE, SENHA, TP_USUARIO) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getSenha()); // Assuming Cliente class has getSenha() method for hashed password
            stmt.setString(6, "CLIENTE"); // Define o tipo de usuário como "CLIENTE"

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar o cliente, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);
                    cliente.setId(idUsuario);

                    // Agora você pode usar o idUsuario para inserir na tabela TB_CLIENTE
                    inserirNaTabelaCliente(cliente, conn);

                    // E também inserir o endereço na tabela TB_ENDERECO
                    inserirEndereco(cliente.getEndereco(), idUsuario, conn);
                } else {
                    throw new SQLException("Falha ao criar o cliente, não foi possível obter o ID.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void inserirNaTabelaCliente(Cliente cliente, Connection conn) throws SQLException {
        String sqlCliente = "INSERT INTO TB_CLIENTE (ID_USUARIO) VALUES (?)";
        try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
            stmtCliente.setInt(1, cliente.getId());
            stmtCliente.executeUpdate();
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

    public static Cliente buscarClientePorCPF(String cpf) {
        String sql = "SELECT * FROM TB_USUARIO u " +
                "JOIN TB_CLIENTE c ON u.ID_USUARIO = c.ID_USUARIO " +
                "JOIN TB_ENDERECO e ON u.ID_USUARIO = e.ID_USUARIO " +
                "WHERE u.NR_CPF_USUARIO = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Cria um novo objeto Cliente e define seus atributos
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("ID_USUARIO"));
                    cliente.setNome(rs.getString("NO_USUARIO"));
                    cliente.setCpf(rs.getString("NR_CPF_USUARIO"));
                    cliente.setDataNascimento(rs.getDate("DT_NASCIMENTO").toLocalDate());
                    cliente.setTelefone(rs.getString("NR_TELEFONE"));
                    cliente.setSenha(rs.getString("SENHA"));

                    // Cria um novo objeto Endereco e define seus atributos
                    Endereco endereco = new Endereco();
                    endereco.setCep(rs.getString("NR_CEP"));
                    endereco.setLocal(rs.getString("NO_LOCAL"));
                    endereco.setNumeroCasa(rs.getInt("NR_CASA"));
                    endereco.setBairro(rs.getString("NO_BAIRRO"));
                    endereco.setCidade(rs.getString("NO_CIDADE"));
                    endereco.setEstado(rs.getString("SG_ESTADO"));

                    // Associa o endereço ao cliente
                    cliente.setEndereco(endereco);

                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Cliente não encontrado
    }

    public static Cliente buscarClientePorId(int idCliente) {
        String sql = "SELECT * FROM TB_USUARIO u " +
                "JOIN TB_CLIENTE c ON u.ID_USUARIO = c.ID_USUARIO " +
                "JOIN TB_ENDERECO e ON u.ID_USUARIO = e.ID_USUARIO " +
                "WHERE u.ID_USUARIO = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Cria um novo objeto Cliente e define seus atributos
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("ID_USUARIO"));
                    cliente.setNome(rs.getString("NO_USUARIO"));
                    cliente.setCpf(rs.getString("NR_CPF_USUARIO"));
                    cliente.setDataNascimento(rs.getDate("DT_NASCIMENTO").toLocalDate());
                    cliente.setTelefone(rs.getString("NR_TELEFONE"));
                    cliente.setSenha(rs.getString("SENHA"));

                    // Cria um novo objeto Endereco e define seus atributos
                    Endereco endereco = new Endereco();
                    endereco.setCep(rs.getString("NR_CEP"));
                    endereco.setLocal(rs.getString("NO_LOCAL"));
                    endereco.setNumeroCasa(rs.getInt("NR_CASA"));
                    endereco.setBairro(rs.getString("NO_BAIRRO"));
                    endereco.setCidade(rs.getString("NO_CIDADE"));
                    endereco.setEstado(rs.getString("SG_ESTADO"));

                    // Associa o endereço ao cliente
                    cliente.setEndereco(endereco);

                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Cliente não encontrado
    }
}