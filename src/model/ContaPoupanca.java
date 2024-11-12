package model;

public class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public ContaPoupanca() {
        super();
    }

    public ContaPoupanca(int saldo, double taxaRendimento) {
        super(saldo);
        this.taxaRendimento = taxaRendimento;
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    public double calcularRendimento() {
        return getSaldo() * (taxaRendimento / 100);
    }

    public double getSaldo() {
        return saldo;
    }
}