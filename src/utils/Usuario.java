package utils;

import java.time.LocalDate;

public abstract class Usuario {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate data_nascimento;
    private String telefone;
    private boolean validado;

    // construtor
    public Usuario(int id, String nome, String cpf, String telefone, boolean validado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.validado = validado;
    }

    // public boolean login(String senha) {
    //     // ...
    // }
    // public void logout() {
    //     // ...
    // }
    // public String consultarDados() {
    //     // ...
    // }

    // getters e setters
    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    // public void setEndereco(Endereco endereco) {
    //     this.endereco = endereco;
    // }
    // public Endereco getEndereco() {
    //     return endereco;
    // }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

}