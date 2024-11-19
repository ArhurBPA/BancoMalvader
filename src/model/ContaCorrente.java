package model;

import java.time.LocalDate;

public class ContaCorrente extends Conta {
    private double limite;          // Limite da conta corrente
    private LocalDate dataVencimento; // Data de vencimento do limite

    // Construtor vazio
    public ContaCorrente() {}

    // Construtor completo
    public ContaCorrente(int numero, String agencia, double saldo, Cliente cliente,
                         double limite, LocalDate dataVencimento) {
        super(numero, agencia, saldo, cliente, "CORRENTE");
        this.limite = limite;
        this.dataVencimento = dataVencimento;
    }

    public double consultarLimite() {
        return limite;
    }

    // Getters e setters
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Conta Corrente - Número: " + getNumero());
        System.out.println("Agência: " + getAgencia());
        System.out.println("Saldo: R$ " + getSaldo());
        System.out.println("Cliente: " + getCliente().getNome());
        System.out.println("Limite: R$ " + limite);
        System.out.println("Data de Vencimento do Limite: " + dataVencimento);
    }
}