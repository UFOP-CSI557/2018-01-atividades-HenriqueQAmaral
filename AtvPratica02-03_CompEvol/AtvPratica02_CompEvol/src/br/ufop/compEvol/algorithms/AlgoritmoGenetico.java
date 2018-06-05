/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.algorithms;

import br.ufop.compEvol.components.Execucao;
import br.ufop.compEvol.components.Individuo;
import br.ufop.compEvol.components.Operadores;
import br.ufop.compEvol.components.Populacao;
import br.ufop.compEvol.components.Problema;
import br.ufop.compEvol.enumerates.EnumCasoDeTeste;
import br.ufop.compEvol.enumerates.EnumRotinaAdotada;
import br.ufop.compEvol.geral.Principal;
import br.ufop.compEvol.superClasses.Algoritmo;
import br.ufop.compEvol.superClasses.AlgoritmoIF;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class AlgoritmoGenetico extends Algoritmo implements AlgoritmoIF{ //Classes derivadas de Algoritmo só precisam implementar o método de executar
 

    public AlgoritmoGenetico(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema, Double minimo, Double maximo, Integer nVariaveis) {
        super(tamanho, pCrossover, pMutacao, geracoes, problema, minimo, maximo, nVariaveis);      
    }



    public Double executar(ArrayList<Execucao> execucoesCaso) throws Exception {

                
        //setTamanhoPopulacao(Principal.aleatorizarPopulacao());
        //setTaxaCrossover(Principal.aleatorizarCrossover());
        //setTaxaMutacao(Principal.aleatorizarMutacao());
        
        Execucao exec = new Execucao(getTamPop(), getTaxaCrossover(), getTaxaMutacao());
        Instant inicio = Instant.now();

        setPopulacao(new Populacao(getMinimo(), getMaximo(), getnVariaveis(), getTamPop(), getProblema()));
        setNovaPopulacao(new Populacao(getMinimo(), getMaximo(), getnVariaveis(), getTamPop(), getProblema()));
        
        Populacao populacao = getPopulacao();
        Populacao novaPopulacao = getNovaPopulacao();

        // Criar a população
        populacao.criar();
        // Avaliar
        populacao.avaliar();

        // Recuperar o melhor indivíduo
        // Enquanto o critério de parada não for satisfeito - Gerações
        Random rnd = new Random();
        int ind1, ind2;

        for (int g = 1; g <= getGeracoes(); g++) {
            System.err.println("Geração: " + g);

            for (int i = 0; i < getTamanhoPopulacao(); i++) { //para cada individuo da populacao
                //System.err.println("Individuo: "+i);
                if (rnd.nextDouble() <= getTaxaCrossover()) {
                    // Realizar a operação de crossover

                    ind1 = rnd.nextInt(getTamanhoPopulacao());

                    do {
                        ind2 = rnd.nextInt(getTamanhoPopulacao());
                    } while (ind1 == ind2); //garantir que são individuos diferentes

                    Individuo desc1 = new Individuo(getMinimo(), getMaximo(), getnVariaveis());
                    Individuo desc2 = new Individuo(getMinimo(), getMaximo(), getnVariaveis());

                    // Progenitores -  Selecionados aleatoriamente
                    Individuo p1 = populacao.getIndividuos().get(ind1);
                    Individuo p2 = populacao.getIndividuos().get(ind2);

                    //definir o método de operação a ser realizado
                    // Ponto de corte
                    int corte = rnd.nextInt(p1.getVariaveis().size());
                    /*
                    // Descendente 1 -> Ind1_1 + Ind2_2;
                    Operadores.crossoverAritmetico_corte(p1, p2, desc1, corte);

                    // Descendente 2 -> Ind2_1 + Ind1_2;
                    Operadores.crossoverAritmetico_corte(p2, p1, desc2, corte);

                    // Mutação*/
                    //switch (casoT) {

                    //case ONE:
                    //Crossover desc1
                    Operadores.crossoverAritmetico(p1, p2, desc1, corte);
                    //Crossover desc2
                    Operadores.crossoverAritmetico(p2, p1, desc2, corte);

                    //mutacao desc1
                    Operadores.mutacao(desc1, this);
                    //mutacao desc2
                    Operadores.mutacao(desc2, this);

                    /*     break;

                        case TWO:
                            //Crossover desc1
                            Operadores.crossoverAritmetico_multipontos(p1, p2, desc1);
                            //Crossover desc2
                            Operadores.crossoverAritmetico_multipontos(p2, p1, desc2);

                            //mutacao desc1
                            Operadores.mutacao(desc1, this);
                            //mutacao desc2
                            Operadores.mutacao(desc2, this);

                            break;

                    }*/
                    // Avaliar as novas soluções
                    
                    Problema problema = getProblema();
                    
                    problema.calcularFuncObjetivo(desc1);
                    problema.calcularFuncObjetivo(desc2);

                    // Inserir na nova população
                    novaPopulacao.getIndividuos().add(desc1);
                    novaPopulacao.getIndividuos().add(desc2);

                }//if
            } //for-população

            //definir a rotina
            double decisor = rnd.nextFloat();

            if (decisor >= 0 && decisor < 0.45) {          //45% torneio
                rotinaTorneio();
                exec.setRotina(EnumRotinaAdotada.TORNEIO);
            } else if (decisor >= 0.45 && decisor < 0.90) { //45% Elitista
                rotinaElitista();
                exec.setRotina(EnumRotinaAdotada.ELITISTA);
            } else {                                      //10% aleatorio
                rotinaAleatoria();
                exec.setRotina(EnumRotinaAdotada.ALEATORIO);
            }
            // Imprimir a situacao atual
            Collections.sort(populacao.getIndividuos());
            //System.out.println("Gen = " + g + "\tCusto = " + populacao.getIndividuos().get(0).getFuncaoObjetivo());
            System.out.println("Best: " + populacao.getIndividuos().get(0).getFuncaoObjetivo() + "| Worse: " + populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).getFuncaoObjetivo());
            if (populacao.getIndividuos().get(0).getFuncaoObjetivo() == 0.0 && populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).getFuncaoObjetivo() != 0) {
                exec.setGeracaoDecisiva(g);
                exec.setPior(populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).clone());
                exec.getPior().setID(populacao.getIndividuos().size() - 1);
            }
        } //proxima geracao

        System.out.println("Melhor resultado: ");
        System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        Instant fim = Instant.now();

        exec.setMelhor(populacao.getIndividuos().get(0).clone());
        exec.setDuracao(Duration.between(inicio, fim));
        exec.setDesvioPadrao(populacao.calcularDesvioPadrao());
        //System.err.println("---------------------DEsvio: "+exec.getDesvioPadrao());
        exec.setID(execucoesCaso.size() + 1);

        execucoesCaso.add(exec);

        return populacao.getIndividuos().get(0).getFuncaoObjetivo();

    }

}
