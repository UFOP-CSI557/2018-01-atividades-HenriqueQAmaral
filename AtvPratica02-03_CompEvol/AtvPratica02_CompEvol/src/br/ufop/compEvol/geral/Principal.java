/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.geral;

import br.ufop.compEvol.algorithms.AlgoritmoEvolucionario;
import br.ufop.compEvol.algorithms.AlgoritmoGenetico;
import br.ufop.compEvol.algorithms.DifferentialEvolution;
import br.ufop.compEvol.components.Problema;
import br.ufop.compEvol.components.Execucao;
import br.ufop.compEvol.components.GeradorSaida;
import br.ufop.compEvol.enumerates.EnumCasoDeTeste;
import br.ufop.compEvol.superClasses.Algoritmo;
import br.ufop.compEvol.superClasses.AlgoritmoIF;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final Integer geracoes = 300;      // previamente definidio
        final Double minimo = -5.12;       // previamente definidio
        final Double maximo = 5.12;        // previamente definidio
        final Integer nVariaveis = 100;    // previamente definidio

        Problema problema = new Problema(nVariaveis);

        int EXECUCOES = 2;

        Integer tamanho = aleatorizarPopulacao();              //a ser definido (populacao)
        Double taxaCrossover = aleatorizarCrossover();        //a ser definido
        Double taxaMutacao = aleatorizarMutacao();         //a ser definido

        //Evolucionário
        Integer mu = 20;
        Integer lambda = 100;
        //Diferencial
        Integer gmax = 100;
        // Numero de variaveis
        Integer D = 100;
        // Tamanho da populacao
        Integer Np = 30;
        // Coeficiente de mutacao
        Double F = 0.001;
        // Coeficiente de Crossover
        Double Cr = 0.9;

        try {
            

            for (int i = 1; i <= 3; i++) {
                AlgoritmoIF alg = null;

                switch (i) {
                    case 1: //executar o genético
                        alg = new AlgoritmoGenetico(tamanho, taxaCrossover, taxaMutacao, geracoes, problema, minimo, maximo, nVariaveis);
                        break;
                    case 2: //executar o Evolucionário
                        alg = new AlgoritmoEvolucionario(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, taxaMutacao);
                        break;

                    case 3: //executar o Diferencial
                        alg = new DifferentialEvolution(minimo, maximo, problema, gmax, D, Np, F, Cr);
                        break;
                    default:
                        //alg = new AlgoritmoGenetico(tamanho, taxaCrossover, taxaMutacao, geracoes, problema, minimo, maximo, nVariaveis);
                        break;
                }

                int execucoes1 = EXECUCOES, execucoes2 = EXECUCOES;

                Random rnd = new Random();
                boolean encerrar = false;

                do {
                    if (rnd.nextBoolean() && execucoes1 > 0) { //Caso a execução seja definida para o 1 e ele não tenha atingido o limite
                        showMessageLoading(EnumCasoDeTeste.ONE);
                        alg.executar( alg.getCaso1());
                        execucoes1--;
                    } else if (execucoes2 > 0) {
                        showMessageLoading(EnumCasoDeTeste.TWO);
                        alg.executar( alg.getCaso2());
                        execucoes2--;
                    } else {
                        if (execucoes1 > 0) {
                            showMessageLoading(EnumCasoDeTeste.ONE);
                            alg.executar( alg.getCaso1());
                            execucoes1--;
                        } else {
                            encerrar = true;
                        }
                    }

                } while (!encerrar);

                System.out.println("\n\n\tLog do caso1: ");
                showLogCaso(alg.getCaso1());

                System.out.println("\n\n\tLog do caso2: ");
                showLogCaso(alg.getCaso2());

                System.out.println("----------------------------------------------------");
                System.out.println("Avaliação Caso1:");
                System.out.println(avaliarCaso(alg.getCaso1()));
                System.out.println("----------------------------------------------------");
                System.out.println("Avaliação Caso2:");
                System.out.println(avaliarCaso(alg.getCaso2()));

                GeradorSaida.escreverArquivo(avaliarCaso(alg.getCaso1()) + avaliarCaso(alg.getCaso2()), Instant.now().getNano() + ".txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Integer aleatorizarPopulacao() {
        Random rnd = new Random();
        Integer[] pop = {20, 40, 90, 70, 55, 100, 200};

        return pop[rnd.nextInt(7)];
    }

    public static Double aleatorizarMutacao() {
        Random rnd = new Random();
        Double txMutacao;

        txMutacao = rnd.nextDouble();

        return txMutacao / 10;
    }

    public static Double aleatorizarCrossover() {
        Random rnd = new Random();
        Double txCrossover;

        txCrossover = rnd.nextDouble();
        if (txCrossover + 0.5 < 1.0) {
            txCrossover += 0.5;
        }
        return txCrossover;

    }

    public static void aleatorizarParametros(Integer tamPop, Double txCrossover, Double txMutacao) {
        Random rnd = new Random();
        Integer[] pop = {20, 40, 90, 70, 55, 100, 200};

        tamPop = pop[rnd.nextInt(7)];

        txMutacao = rnd.nextDouble() / 10;

        txCrossover = rnd.nextDouble();
        if (txCrossover + 0.5 < 1.0) {
            txCrossover += 0.5;
        }

    }

    public static void showMessageLoading(EnumCasoDeTeste caso) {
        System.out.println("Carregando Execução do caso de Testes " + caso);
    }

    public static void showLogCaso(ArrayList<Execucao> exec) {
        for (int i = 0; i < exec.size(); i++) {
            System.out.println(exec.get(i).toString());
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

}
