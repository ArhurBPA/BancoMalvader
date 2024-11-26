package model;

import util.Usuario;

import java.util.Date;

public class Cliente extends Usuario {

    private String senha;
    private double saldo;
    private double limite;

    public Cliente(int id, String nome, String cpf, String telefone, String senha, String senhaQuery, boolean validado) {
        super(id, nome, cpf, telefone, validado);
        this.senha = senha;
        this.saldo = 0.0; // Inicializa o saldo com 0
        this.limite = 0.0; // Inicializa o limite com 0
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public int calcularIdade(Date dataNascimento) {
        if (dataNascimento == null) return 0;

        Date dataAtual = new Date();

        return dataAtual.getYear() - dataNascimento.getYear();
    }

    @Override
    public String toString() {
        return "Cliente: " + getNome() + ", CPF: " + getCpf() + ", Telefone: " + getTelefone() +
                ", Saldo: " + saldo + ", Limite: " + limite;
    }
}