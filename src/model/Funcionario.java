package model;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String codigoFuncionario;
    private String cargo;
    private String senha;
    private String nome;
    private LocalDate dataNascimento;
    private double salario;

    public Funcionario(String codigoFuncionario, String cargo, String senha, String nome, LocalDate dataNascimento, double salario) {
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.senha = senha;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }

    public Funcionario() {
        
    }

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public void logout() {
        // Implementar o código de logout conforme as necessidades do esforço
    }

    @Override
    public String consultarDados() {
        return "Código: " + codigoFuncionario + ", Nome: " + nome + ", Data de Nascimento: " + dataNascimento + ", Cargo: " + cargo + ", Salário: " + salario;
    }

    public void abrirConta() {
    }

    public void encerrarConta() {
    }

    public void consultarDadosConta() {
    }

    public void consultarDadosCliente() {
    }

    public void alterarDadosConta() {
    }

    public void alterarDadosCliente() {

    }

    public void cadastrarFuncionario() {
    }

    public void gerarRelatorioMovimentacao() {
    }
}