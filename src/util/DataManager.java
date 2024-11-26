package util;

import models.Conta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    // Método para gravar dados em um arquivo de texto
    public static boolean gravarDadosEmArquivo(String caminho, String dados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, true))) {
            writer.write(dados);
            writer.newLine(); // Adiciona uma nova linha após os dados
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para ler dados de um arquivo de texto
    public static List<String> lerDadosDeArquivo(String caminho) {
        List<String> dados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                dados.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dados;
    }

    // Método para sobrescrever dados em um arquivo de texto
    public static boolean sobrescreverDadosEmArquivo(String caminho, String dados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write(dados);
            writer.newLine(); // Adiciona uma nova linha após os dados
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar se um arquivo existe
    public static boolean verificarArquivoExiste(String caminho) {
        File file = new File(caminho);
        return file.exists();
    }

    // Método para excluir um arquivo
    public static boolean excluirArquivo(String caminho) {
        File file = new File(caminho);
        return file.delete();
    }

    // Método para salvar a lista de contas em um arquivo binário
    public static void salvarContas(List<Conta> contas, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(contas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar a lista de contas a partir de um arquivo binário
    @SuppressWarnings("unchecked")
    public static List<Conta> carregarContas(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Conta>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Salvar funcionários em arquivo
//    public static void salvarFuncionarios(List<Funcionario> funcionarios, String filename) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
//            oos.writeObject(funcionarios);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Carregar funcionários do arquivo
//    @SuppressWarnings("unchecked")
//    public static List<Funcionario> carregarFuncionarios(String filename) {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
//            return (List<Funcionario>) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}