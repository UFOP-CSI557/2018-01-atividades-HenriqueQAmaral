/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.componentes;

import java.util.Random;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Operadores {

    public static void crossoverAritmetico_corte(Individuo ind1, Individuo ind2, Individuo descendente, int corte) {
        // Crossover aritmetico - 1 ponto de corte
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();

        // Ind1_1
        // alpha * P1
        for (int i = 0; i < corte; i++) {
            //System.err.println("Corte: "+i+ " NVAR: "+ind1.getnVar());
            Double valor = alpha * ind1.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }

        // Ind2_2
        // (1 - alpha) * P2
        for (int i = corte; i < ind1.getnVar(); i++) {
            Double valor = (1.0 - alpha) * ind2.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }
    }//end - CrossOverCorte
    
    public static void crossoverAritmetico(Individuo ind1, Individuo ind2, Individuo descendente, int corte){
        // Crossover aritmetico - 1 ponto de corte
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();

        // Ind1_1
        // alpha * P1
        for (int i = 0; i < corte; i++) {
            Double valor = alpha * ind1.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }

        // Ind2_2
        // (1 - alpha) * P2
        for (int i = corte; i < ind1.getnVar(); i++) {
            Double valor = (1.0 - alpha) * ind2.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);
        }
    }
    
    public static void crossoverAritmetico_multipontos(Individuo ind1, Individuo ind2, Individuo descendente) {
        Random rnd = new Random();
        Double alpha = rnd.nextDouble();
        Individuo escolhido;

        // Ind1_1
        // alpha * P1
        for (int i = 0; i < descendente.getnVar(); i++) {

            if (rnd.nextBoolean()) { //alterna aleatoriamente entre os genitores 1 e 2
                escolhido = ind1;
            } else {
                escolhido = ind2;
            }

            Double valor = alpha * escolhido.getVariaveis().get(i);
            descendente.getVariaveis().add(valor);

        }

    }// end - crossoverMultipontos

    public static void mutacao(Individuo individuo, AlgoritmoGenetico ag) {
        Random rnd = new Random();

        for (int i = 0; i < individuo.getVariaveis().size(); i++) {

            if (rnd.nextDouble() <= ag.taxaMutacao) {

               
                Double valor = individuo.getVariaveis().get(i);
                // Multiplica por rnd
                valor *= rnd.nextDouble();

                // Inverter o sinal
                if (!rnd.nextBoolean()) {
                    valor = -valor;
                }

                if (valor >= ag.minimo && valor <= ag.maximo) {
                    individuo.getVariaveis().set(i, valor);
                    //System.out.println("Mutação realizada com sucesso! Indivíduo: "+individuo.getID());
                }

            }
        }

    }//end - Mutacao
    
    
    public static void mutacaoControlada(Individuo individuo, AlgoritmoGenetico ag, int geracaoAtual) {
        Random rnd = new Random();

        for (int i = 0; i < individuo.getVariaveis().size(); i++) {

            if (rnd.nextDouble() <= ag.taxaMutacao) {

                // Mutacao aritmetica
                // Multiplicar rnd e inverter ou nao o sinal
                Double valor = individuo.getVariaveis().get(i);
                // Multiplica por rnd
                valor *= (rnd.nextDouble()*0.5)/geracaoAtual;

                if (valor >= ag.minimo && valor <= ag.maximo) {
                    individuo.getVariaveis().set(i, valor);
                    
                }

            }
        }

    }//end - Mutacao
    

}
