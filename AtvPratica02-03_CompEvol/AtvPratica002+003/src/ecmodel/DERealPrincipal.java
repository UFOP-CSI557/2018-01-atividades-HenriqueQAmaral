/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import metodo.DEReal;
import problema.Problema;
import problema.ProblemaRastrigin;
import solucao.Execucao;

/**
 *
 * @author fernando
 */
public class DERealPrincipal {

    /**
     * @return
     */
    public static ArrayList<Execucao> run() {
        
        Double [] FValues = {0.001, 0.2, 0.09, 0.1};
        Double [] CrValues = {0.9, 0.7, 0.8, 0.99};
        
        Double minimo = -5.12;
        Double maximo = 5.12;

        int D = 100;
        Problema problema = new ProblemaRastrigin(D);

        int gmax = 100;
        int Np = 30;

        double F = 0.001;
        double Cr = 0.9;

        int EXECUCOES = 30;
        ArrayList<Execucao> caso1 = new ArrayList<>();
        ArrayList<Execucao> caso2 = new ArrayList<>();

        int execucoes1 = EXECUCOES, execucoes2 = EXECUCOES;

        Random rnd = new Random();
        boolean encerrar = false;

        do {
            //F = FValues[rnd.nextInt(3)];

            //Cr = CrValues[rnd.nextInt(3)];

           
            DEReal deReal = new DEReal(minimo, maximo, problema, gmax, D, Np, F, Cr);
            
            if (rnd.nextBoolean() && execucoes1 > 0) { //Caso a execução seja definida para o 1 e ele não tenha atingido o limite
                //showMessageLoading(EnumCasoDeTeste.ONE);
                caso1.add(deReal.executar());
                execucoes1--;
            } else if (execucoes2 > 0) {
                //showMessageLoading(EnumCasoDeTeste.TWO);
                caso2.add(deReal.executar());
                execucoes2--;
            } else {
                if (execucoes1 > 0) {
                    //showMessageLoading(EnumCasoDeTeste.ONE);
                    caso1.add(deReal.executar());
                    execucoes1--;
                } else {
                    encerrar = true;
                }
            }

        } while (!encerrar);

        Collections.sort(caso1);
        Collections.sort(caso2);
        if (caso2.get(0).compareTo(caso1.get(0)) >= 0) {
            return caso1;
        } else {
            return caso2;
        }

        //Individuo resultado = deReal.executar();
        //System.out.println(resultado);
    }

}
