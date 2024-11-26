package models;

public class Endereco {

    private String cep;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;

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