package util;

import java.time.LocalDate;

public abstract class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private boolean validado;

    public Usuario(int id, String nome, String cpf, String telefone, boolean validado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.validado = validado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isValidado() {
        return validado;
    }

    // Métodos abstratos para login, logout e consulta de dados
    public abstract boolean login(String senha);

    public abstract void logout();

    public abstract String consultarDados();
}