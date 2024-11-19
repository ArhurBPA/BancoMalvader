package model;

public class ContaPoupanca extends Conta {
    private double taxaRendimento; // taxa de rendimento

    // Construtor vazio
    public ContaPoupanca() {}

    // Construtor completo
    public ContaPoupanca(int numero, String agencia, double saldo, Cliente cliente, double taxaRendimento) {
        super(numero, agencia, saldo, cliente, "POUPANCA");
        this.taxaRendimento = taxaRendimento;
    }

    public double calcularRendimento() {
        return getSaldo() * (taxaRendimento / 100);
    }

    // Getters e setters
    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Conta Poupança - Número: " + getNumero());
        System.out.println("Agência: " + getAgencia());
        System.out.println("Saldo: R$ " + getSaldo());
        System.out.println("Cliente: " + getCliente().getNome());
        System.out.println("Taxa de Rendimento: " + taxaRendimento + "%");
    }
}