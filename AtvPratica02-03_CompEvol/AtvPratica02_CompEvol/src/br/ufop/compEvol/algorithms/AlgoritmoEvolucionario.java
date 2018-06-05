/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.algorithms;

import br.ufop.compEvol.components.Execucao;
import br.ufop.compEvol.components.Individuo;
import br.ufop.compEvol.components.Populacao;
import br.ufop.compEvol.components.Problema;
import br.ufop.compEvol.superClasses.Algoritmo;
import br.ufop.compEvol.superClasses.AlgoritmoIF;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class AlgoritmoEvolucionario extends Algoritmo implements AlgoritmoIF {

    public AlgoritmoEvolucionario(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao) {
        super(minimo, maximo, nVariaveis, problema, mu, lambda, geracoes, pMutacao);
    }

    @Override
    public Double executar(ArrayList<Execucao> execucoesCaso) throws Exception {

        // Geracao da populacao inicial - aleatoria - tamanho mu
        Execucao exec = new Execucao(getTamPop(), getTaxaCrossover(), getTaxaMutacao());
        Instant inicio = Instant.now();
        
        Populacao populacao = new Populacao(getMinimo(), getMaximo(), getnVariaveis(), getProblema(), getMu());
        populacao.criar();

        // Avaliar
        populacao.avaliar();

        // Populacao - descendentes
        Populacao descendentes = new Populacao();

        // Criterio de parada - numero de geracoes
        for (int g = 1; g <= getGeracoes(); g++) {

            // Para cada individuo, gerar lambda/mu descendentes
            for (int i = 0; i < populacao.getIndividuos().size(); i++) {

                // Gerar lambda/mu descendentes
                for (int d = 0; d < getLambda() / getMu(); d++) {

                    // Clonar individuo
                    Individuo filho = populacao.getIndividuos().get(i).clone();

                    // Aplicar mutacao
                    mutacaoPorVariavel(filho);

                    // Avaliar
                    getProblema().calcularFuncObjetivo(filho);

                    // Inserir na populacao de descendentes
                    descendentes.getIndividuos().add(filho);

                }

            }

            // ES(mu, lambda)?
            // Eliminar a populacao corrente
            //populacao.getIndividuos().clear();
            // ES(mu + lambda)?
            // Mu + Lambda
            populacao.getIndividuos().addAll(descendentes.getIndividuos());
            // Ordenar Mu+Lambda
            Collections.sort(populacao.getIndividuos());
            // Definir sobreviventes
            populacao.getIndividuos()
                    .subList(getMu(), populacao.getIndividuos().size()).clear();
            // Limpar descendentes
            descendentes.getIndividuos().clear();

            System.out.println("G = " + g
                    + "\t"
                    + populacao.getMelhorIndividuo().getFuncaoObjetivo());

            if (populacao.getIndividuos().get(0).getFuncaoObjetivo() == 0.0 && populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).getFuncaoObjetivo() != 0) {
                exec.setGeracaoDecisiva(g);
                exec.setPior(populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).clone());
                exec.getPior().setID(populacao.getIndividuos().size() - 1);
            }

        }

        System.out.println("Melhor resultado: ");
        System.out.println(populacao.getIndividuos().get(0).getVariaveis());
        Instant fim = Instant.now();

        exec.setMelhor(populacao.getIndividuos().get(0).clone());
        exec.setDuracao(Duration.between(inicio, fim));
        exec.setDesvioPadrao(populacao.calcularDesvioPadrao());
        //System.err.println("---------------------DEsvio: "+exec.getDesvioPadrao());
        exec.setID(execucoesCaso.size() + 1);

        execucoesCaso.add(exec);

        // Retornar o melhor individuo
        return populacao.getMelhorIndividuo().getFuncaoObjetivo();

    }

}
