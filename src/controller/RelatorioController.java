package controller;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;
import model.GerarRelatorio;
import model.Transacao;
import model.UsuarioConta;

import java.util.logging.Logger;
import java.util.logging.Level;


public class RelatorioController {
    private static final Logger LOGGER = Logger.getLogger(RelatorioController.class.getName());

    public void exportarRelatorioParaCSV(GerarRelatorio relatorio) {
        String arquivoCSV = "relatorio_usuario.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(arquivoCSV))) {
            String[] cabecalho = {"Número da Conta", "Agência", "Saldo", "Tipo da Conta", "Tipo de Transação", "Valor da Transação", "Data"};
            writer.writeNext(cabecalho);

            for (UsuarioConta.Conta conta : relatorio.getContas()) {
                String[] linhaConta = {
                        conta.getNumeroConta(),
                        conta.getAgencia(),
                        String.valueOf(conta.getSaldo()),
                        conta.getTipoConta(),
                        "",
                        "",
                        ""
                };
                writer.writeNext(linhaConta);
                for (Transacao transacao : relatorio.getTransacoes()) {
                    if (transacao.getIdConta() == conta.getIdConta()) {
                        String[] linhaTransacao = {
                                "",
                                "",
                                "",
                                "",
                                transacao.getTipoTransacao(),
                                String.valueOf(transacao.getValor()),
                                transacao.getDataTransacao()
                        };
                        writer.writeNext(linhaTransacao);
                    }
                }
            }

            System.out.println("Relatório exportado com sucesso para " + arquivoCSV);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao exportar relatório.", e);
            System.out.println("Erro ao exportar relatório.");
        }
    }
}