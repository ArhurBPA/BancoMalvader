package model;

public class Endereco {

    private final String cep;
    private final String logradouro;
    private final int numero;
    private final String bairro;
    private final String cidade;
    private final String estado;

    public Endereco(String cep, String logradouro, int numero, String bairro, String cidade, String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public int getNumeroCasa() {
        return numero;
    }

    public String getLocal() {
        return logradouro + ", " + numero + " " + bairro + ", " + cidade + " - " + estado;
    }
}