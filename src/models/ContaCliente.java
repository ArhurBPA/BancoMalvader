package models;

public class ContaCliente {
    private String agencia;

    // getters e setters
    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    // atributos
    private String numeroConta;
    private String nomeCliente;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    private String endereco;
    private String senha;
    private String tipoConta;
    private double limite;
    private String dataVencimento;

    // construtor para Conta Poupança
    public ContaCliente(String agencia, String numeroConta, String nomeCliente, String cpf,
                        String dataNascimento, String telefone, String endereco, String senha, String tipoConta) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.senha = senha;
        this.tipoConta = tipoConta;
    }

    // construtor para Conta Corrente
    public ContaCliente(String agencia, String numeroConta, String nomeCliente, String cpf,
                        String dataNascimento, String telefone, String endereco, String senha,
                        String tipoConta, double limite, String dataVencimento) {
        this(agencia, numeroConta, nomeCliente, cpf, dataNascimento, telefone, endereco, senha, tipoConta);
        this.limite = limite;
        this.dataVencimento = dataVencimento;
    }

}