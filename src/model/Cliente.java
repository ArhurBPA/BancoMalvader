package model;
import java.time.LocalDate;

public class Cliente extends Usuario implements OperacoesConta {
    private String senha;

    public Cliente() {
    }

    public Cliente(int id, String nome, String cpf, LocalDate dataNascimento,
                   String telefone, Endereco endereco, String senha) {
        super(id, nome, cpf, dataNascimento, telefone, endereco);
        this.senha = senha;
    }

    public Cliente(String noUsuario, String nrCpfUsuario, LocalDate dtNascimento, String nrTelefone, Object o, String senha) {

    }

    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public void logout() {
    }

    @Override
    public String consultarDados() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public double obterSaldo() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void realizarDeposito(double valor) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean realizarSaque(double valor) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String obterExtrato() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public double obterLimite() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getSenha() {
        return null;
    }
}