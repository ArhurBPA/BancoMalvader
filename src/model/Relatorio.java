package model;

import java.time.LocalDateTime;
import java.util.List;

public class Relatorio {
    private String tipo;
    private LocalDateTime dataGeracao;
    private List<String> dados;

    public Relatorio(String tipo, LocalDateTime dataGeracao, List<String> dados) {
        this.tipo = tipo;
        this.dataGeracao = dataGeracao;
        this.dados = dados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public List<String> getDados() {
        return dados;
    }

    public void setDados(List<String> dados) {
        this.dados = dados;
    }

    public void gerarRelatorioGeral() {
        // TODO: Implementação da geração de relatório geral
    }

    public void exportarParaExcel() {
        // TODO: Implementação da exportação para Excel
    }
}