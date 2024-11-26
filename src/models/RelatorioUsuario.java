package models;

import java.util.ArrayList;
import java.util.List;

public class RelatorioUsuario {

    private final List<UsuarioConta.Conta> contas;
    private final List<Transacao> transacoes;

    public RelatorioUsuario() {
        this.contas = new ArrayList<>();
        this.transacoes = new ArrayList<>();
    }

    public void adicionarConta(UsuarioConta.Conta conta) {
        contas.add(conta);
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<UsuarioConta.Conta> getContas() {
        return contas;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}