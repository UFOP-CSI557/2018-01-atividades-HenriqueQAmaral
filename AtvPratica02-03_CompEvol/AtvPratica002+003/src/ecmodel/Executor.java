/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import solucao.Execucao;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Executor {

    public static void main(String[] args) {

        ArrayList<Execucao> DE = null;
        ArrayList<Execucao> ES = null;

        //para cada um dos metodos, 
        for (int i = 0; i < 2; i++) {

            if (i == 0) { //caso seja DEReal
                System.out.println("Incializando DEReal");
                DE = DERealPrincipal.run();

            } else if (i == 1) {//caso seja ESReal
                System.out.println("Inicializando ESReal");
                ES = ESRealMain.run();
            }
        }

        System.out.println(avaliarCaso(DE));
        System.out.println(avaliarCaso(ES));

        showRelatorio(DE, ES);
        try {
            escreverArquivo(extrairValores(ES), "ES_" + Instant.now().getNano());
            escreverArquivo(extrairValores(DE), "DE_" + Instant.now().getNano());
        } catch (Exception e) {
            System.err.println("Erro ao salvar saida");
        }
    }

    public static String avaliarCaso(ArrayList<Execucao> exec) {
        Collections.sort(exec);

        String result = "";
        Double mediaTempo;
        Double mediaGeracoes;

        long somaTempo = 0;
        int somaGeracoes = 0;

        for (int i = 0; i < exec.size(); i++) {
            somaTempo += exec.get(i).getDuracao().toMillis();
            somaGeracoes += exec.get(i).getGeracaoDecisiva();

        }

        mediaTempo = (double) somaTempo / exec.size();
        mediaGeracoes = (double) somaGeracoes / exec.size();

        result += "\nExecução com maior eficiência evolucionária: \n" + exec.get(0).toString();
        result += "\nExecução com pior eficiência evolucionária: \n" + exec.get(exec.size() - 1);

        result += "\nTempo médio de execução: " + mediaTempo;
        result += "\nGeração definitiva média: " + mediaGeracoes;

        return result;

    }

    public static void showRelatorio(ArrayList<Execucao> DE, ArrayList<Execucao> ES) {
        System.out.println("\n\nMelhor resultado: ");
        if (DE.get(0).compareTo(ES.get(0)) < 0) {
            System.out.println("DE:\n" + DE.get(0).getMelhor().toString());
        } else {
            System.out.println("ES:\n" + ES.get(0).getMelhor().toString());
        }
    }

    public static void escreverArquivo(String texto, String nomeArq) throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArq+".txt"));

        bw.write(texto);
        bw.close();

        System.out.println("Arquivo de LOG criado com sucesso!");
    }

    public static String extrairValores(ArrayList<Execucao> casoT) {
        String result = "";

        for (int i = 0; i < casoT.size(); i++) {
            result += casoT.get(i).getMelhor().getFuncaoObjetivo();
            result += " \n";
        }

        return result;
    }
}
