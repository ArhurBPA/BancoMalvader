package model;

import java.time.LocalDate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String cpf;
    protected LocalDate dataNascimento;
    protected String telefone;
    protected Endereco endereco;
    protected String tipoUsuario; // Pode ser "FUNCIONARIO" ou "CLIENTE"
    protected String senhaHash; // Armazenamento seguro da senha (hash)

    // Construtor vazio
    public Usuario() {}

    // Construtor completo sem ID
    public Usuario(String nome, String cpf, LocalDate dataNascimento, String telefone,
                   Endereco endereco, String tipoUsuario, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
        setSenha(senha); // Armazena a senha como hash
    }

    // Construtor completo com ID
    public Usuario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                   Endereco endereco, String tipoUsuario, String senha) {
        this(nome, cpf, dataNascimento, telefone, endereco, tipoUsuario, senha);
        this.id = id;
    }

    // Métodos abstratos para serem implementados pelas subclasses
    public abstract boolean login(String senha);

    public void logout() {
        System.out.println("Usuário deslogado.");
    }

    // Método para verificar a senha (compara o hash da senha fornecida com o hash armazenado)
    protected boolean verificarSenha(String senha) {
        return gerarHash(senha).equals(senhaHash);
    }

    // Método para gerar o hash da senha (SHA-256)
    private String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha.", e);
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenha(String senha) {
        this.senhaHash = gerarHash(senha);
    }
}