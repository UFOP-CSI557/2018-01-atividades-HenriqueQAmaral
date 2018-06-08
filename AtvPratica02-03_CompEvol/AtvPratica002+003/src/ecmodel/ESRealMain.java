/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import metodo.ESReal;
import problema.Problema;
import problema.ProblemaRastrigin;
import solucao.Execucao;
import solucao.Individuo;

/**
 *
 * @author fernando
 */
public class ESRealMain {

    /**
     * @param args the command line arguments
     * @return 
     */
    public static ArrayList<Execucao> run() {

        Double minimo = -5.12;
        Double maximo = 5.12;
        Integer nVariaveis = 100;
        Problema problema = new ProblemaRastrigin(nVariaveis);
        
        int [] lambdaValues = {100, 50, 90, 150, 120};
        Double [] mutacaoValues = {0.8, 0.9, 0.7, 0.89, 0.99};
        
        // Parametros - ES
        Integer mu = 20; // Tamanho da populacao
        Integer lambda = 100; // numero de descendentes
        Integer geracoes = 100; // criterio de parada
        Double pMutacao = 0.8; // mutacao - aplicacao ao descendente - variacao/perturbacao

        int EXECUCOES = 30;
        ArrayList<Execucao> caso1 = new ArrayList<>();
        ArrayList<Execucao> caso2 = new ArrayList<>();

        int execucoes1 = EXECUCOES, execucoes2 = EXECUCOES;
                
        Random rnd = new Random();
        boolean encerrar = false;

        do {
            
            pMutacao = mutacaoValues[rnd.nextInt(4)];

            lambda = lambdaValues[rnd.nextInt(4)];
            
            ESReal esReal = new ESReal(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
            
            if (rnd.nextBoolean() && execucoes1 > 0) { //Caso a execução seja definida para o 1 e ele não tenha atingido o limite
                //showMessageLoading(EnumCasoDeTeste.ONE);
                caso1.add(esReal.executar());
                execucoes1--;
            } else if (execucoes2 > 0) {
                //showMessageLoading(EnumCasoDeTeste.TWO);
                caso2.add(esReal.executar());
                execucoes2--;
            } else {
                if (execucoes1 > 0) {
                    //showMessageLoading(EnumCasoDeTeste.ONE);
                    caso1.add(esReal.executar());
                    execucoes1--;
                } else {
                    encerrar = true;
                }
            }

        } while (!encerrar);
        
        
        Collections.sort(caso1);
        Collections.sort(caso2);
        if(caso2.get(0).compareTo(caso1.get(0)) >= 0 )
            return caso1;
        else
            return caso2;
        //Individuo melhor = ;

        //System.out.println(melhor);

    }

}
