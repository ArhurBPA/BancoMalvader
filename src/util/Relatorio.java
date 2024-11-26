package util;

import java.time.LocalDateTime;
import java.util.List;

public class Relatorio { // classe Relatorio

    // atributos
    private String tipo;
    private LocalDateTime data_geracao;
    private List<String> dados;

    // construtor
    public Relatorio(String tipo, LocalDateTime data_geracao, List<String> dados) {
        this.tipo = tipo;
        this.data_geracao = data_geracao;
        this.dados = dados;
    }

    public void gerarRelatorioGeral(){
        // ...
    }


    public void exportarParaExcel(){
        // ...
    }
}