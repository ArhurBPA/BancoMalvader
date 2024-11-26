//package controller;
//
//import com.opencsv.CSVWriter;
//import models.GerarRelatorio;
//import models.Transacao;
//import models.UsuarioConta;
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class ControllerRelatorio {
//
//    // metodo para exportar o relatorio do usuário em formato CSV
//    public void exportarRelatorioParaCSV(GerarRelatorio relatorio) {
//        String arquivoCSV = "relatorio_usuario.csv";
//
//        try (CSVWriter writer = new CSVWriter(new FileWriter(arquivoCSV))) {
//            // Cabeçalho do CSV
//            String[] cabecalho = {"Número da Conta", "Agência", "Saldo", "Tipo da Conta", "Tipo de Transação", "Valor da Transação", "Data"};
//            writer.writeNext(cabecalho);
//            for (UsuarioConta.Conta conta : relatorio.getContas()) {
//                String[] linhaConta = {
//                        conta.getNumeroConta(),
//                        conta.getAgencia(),
//                        String.valueOf(conta.getSaldo()),
//                        conta.getTipoConta(),
//                        "", // colunas vazias para alinhamento de dados
//                        "",
//                        ""
//                };
//                writer.writeNext(linhaConta);
//                for (Transacao transacao : relatorio.getTransacoes()) {
//                    if (transacao.getIdConta() == conta.getIdConta()) {
//                        String[] linhaTransacao = {
//                                "",
//                                "",
//                                "",
//                                "",
//                                transacao.getTipoTransacao(),
//                                String.valueOf(transacao.getValor()),
//                                transacao.getDataTransacao()
//                        };
//                        writer.writeNext(linhaTransacao);
//                    }
//                }
//            }
//
//            System.out.println("Relatório exportado com sucesso para " + arquivoCSV);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Erro ao exportar relatório.");
//        }
//    }
//}