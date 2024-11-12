package model;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String codigoFuncionario;
    private String cargo;
    private String senha;

    public Funcionario(String codigoFuncionario, String cargo, String senha) {
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.senha = senha;
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
        return "Código: " + codigoFuncionario + ", Cargo: " + cargo;
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