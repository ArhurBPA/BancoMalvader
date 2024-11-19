package model;

import java.time.LocalDate;

public class Cliente extends Usuario {

    // Construtor padrão
    public Cliente() {
        super();
    }

    // Construtor completo com ID
    public Cliente(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                   Endereco endereco, String senha) {
        super(id, nome, cpf, dataNascimento, telefone, endereco, "CLIENTE", senha);
    }

    // Construtor completo sem ID
    public Cliente(String nome, String cpf, LocalDate dataNascimento, String telefone,
                   Endereco endereco, String senha) {
        super(nome, cpf, dataNascimento, telefone, endereco, "CLIENTE", senha);
    }

    public Cliente(String noUsuario, String nrCpfUsuario, LocalDate dtNascimento, String nrTelefone, Object o, String senha) {

    }

    @Override
    public boolean login(String senha) {
        return verificarSenha(senha);
    }

    public String getSenha() {
        return null;
    }
}