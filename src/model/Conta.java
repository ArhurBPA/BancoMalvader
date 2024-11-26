package model;

public abstract class Conta {

    private final int numero;
    private final String agencia;
    private final double saldo;
    private final Cliente cliente;

    public Conta(int numero, String agencia, double saldo, Cliente cliente) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public int getNumero() {
        return numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Métodos abstratos para serem implementados nas subclasses
    public abstract void depositar(double valor);

    public abstract boolean sacar(double valor);

    public abstract double consultarSaldo();
}