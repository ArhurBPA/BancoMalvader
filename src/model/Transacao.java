package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Integer idTransacao;
    private TipoTransacao tipoTransacao;
    private BigDecimal valorTransacao;
    private LocalDateTime dataHoraTransacao;
    private Integer idConta;

    public Transacao(int idConta, String tipo, double valor) {
        this.idConta = idConta;
        this.tipoTransacao = TipoTransacao.valueOf(tipo.toUpperCase());
        this.valorTransacao = BigDecimal.valueOf(valor);
        this.dataHoraTransacao = LocalDateTime.now();
    }

    // enumeração para o tipo de transação
    public enum TipoTransacao {
        DEPOSITO, SAQUE, TRANSFERENCIA
    }

    // Construtor
    public Transacao() {}

    // Getters e Setters
    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public LocalDateTime getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(LocalDateTime dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }
}