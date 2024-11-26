package util;

import com.opencsv.CSVWriter;
import model.Transacao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreatorCSV {

    // metodo para exportar dados para o CSV
    public void exportarParaCSV(List<Transacao> transacoes, String caminhoArquivo) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            // cabecalho
            String[] cabecalho = {"ID da Conta", "Tipo", "Valor"};
            writer.writeNext(cabecalho);

            // dados
            for (Transacao transacao : transacoes) {
                String[] linha = {
                        String.valueOf(transacao.getIdConta()),
                        transacao.getTipoTransacao(),
                        String.valueOf(transacao.getValor())
                };
                writer.writeNext(linha);
            }
            System.out.println("Arquivo CSV criado com sucesso: " + caminhoArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}