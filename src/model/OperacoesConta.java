package model;


public interface OperacoesConta {


    double obterSaldo();


    void realizarDeposito(double valor);


    boolean realizarSaque(double valor);


    String obterExtrato();


    double obterLimite();
}
