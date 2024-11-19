package model;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String codigoFuncionario;
    private String cargo;

    public Funcionario() {
        super();
    }

    // Construtor completo com ID
    public Funcionario(int id, String nome, String cpf, LocalDate dataNascimento, String telefone,
                       Endereco endereco, String senha, String codigoFuncionario, String cargo) {
        super(id, nome, cpf, dataNascimento, telefone, endereco, "FUNCIONARIO", senha);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
    }

    // Construtor completo sem ID
    public Funcionario(String nome, String cpf, LocalDate dataNascimento, String telefone,
                       Endereco endereco, String senha, String codigoFuncionario, String cargo) {
        super(nome, cpf, dataNascimento, telefone, endereco, "FUNCIONARIO", senha);
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
    }

    @Override
    public boolean login(String senha) {
        return verificarSenha(senha);
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}