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
import java.util.Random;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class DifferentialEvolution extends Algoritmo implements AlgoritmoIF {

    public DifferentialEvolution(Double minimo, Double maximo, Problema problema, Integer gmax, Integer D, Integer Np, Double F, Double Cr) {
        super(minimo, maximo, problema, gmax, D, Np, F, Cr);
    }

    @Override
    public Double executar(ArrayList<Execucao> execucoesCaso) throws Exception {
        Execucao exec = new Execucao(getTamPop(), getTaxaCrossover(), getTaxaMutacao());
        Instant inicio = Instant.now();
        // Criacao da populacao inicial - X
        Populacao populacao = new Populacao(getProblema(), getMinimo(), getMaximo(), getD(), getNp());
        populacao.criar();
        populacao.avaliar();

        // Nova populacao
        Populacao novaPopulacao = new Populacao();

        Individuo melhorSolucao = populacao.getMelhorIndividuo().clone();

        // Enquanto o criterio de parada nao for atingido
        for (int g = 1; g <= getGmax(); g++) {

            // Para cada vetor da populacao
            for (int i = 0; i < getNp(); i++) {

                // Selecionar r0, r1, r2
                Random rnd = new Random();
                int r0, r1, r2;

                do {
                    r0 = rnd.nextInt(getNp());
                } while (r0 == i);

                do {
                    r1 = rnd.nextInt(getNp());
                } while (r1 == r0);

                do {
                    r2 = rnd.nextInt(getNp());
                } while (r2 == r1 || r2 == r0);

                Individuo trial = new Individuo(getMinimo(), getMaximo(), getD());

                Individuo xr0 = populacao.getIndividuos().get(r0);
                Individuo xr1 = populacao.getIndividuos().get(r1);
                Individuo xr2 = populacao.getIndividuos().get(r2);

                // Gerar perturbacao - diferenca
                gerarPerturbacao(trial, xr1, xr2);
                // Mutacao - r0 + F * perturbacao
                mutacao(trial, xr0);

                // Target
                // DE/rand/1/bin
                Individuo target = populacao.getIndividuos().get(i);
                // Crossover
                crossover(trial, target);

                // Selecao
                getProblema().calcularFuncObjetivo(trial);

                if (trial.getFuncaoObjetivo() <= target.getFuncaoObjetivo()) {
                    novaPopulacao.getIndividuos().add(trial);
                } else {
                    novaPopulacao.getIndividuos().add(target.clone());
                }

            }

            if (populacao.getIndividuos().get(0).getFuncaoObjetivo() == 0.0 && populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).getFuncaoObjetivo() != 0) {
                exec.setGeracaoDecisiva(g);
                exec.setPior(populacao.getIndividuos().get(populacao.getIndividuos().size() - 1).clone());
                exec.getPior().setID(populacao.getIndividuos().size() - 1);
            }

            // Populacao para a geracao seguinte
            populacao.getIndividuos().clear();
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            Individuo melhorDaPopulacao = populacao.getMelhorIndividuo();

            if (melhorDaPopulacao.getFuncaoObjetivo() <= melhorSolucao.getFuncaoObjetivo()) {
                melhorSolucao = melhorDaPopulacao.clone();
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

        return melhorSolucao.getFuncaoObjetivo();

    }

    private void gerarPerturbacao(Individuo trial, Individuo xr1, Individuo xr2) {

        // trial <- Diferenca entre r1 e r2
        for (int i = 0; i < getD(); i++) {
            Double diferenca = xr1.getVariaveis().get(i)
                    - xr2.getVariaveis().get(i);

            trial.getVariaveis().add(reparaValor(diferenca));
        }

    }

    private void mutacao(Individuo trial, Individuo xr0) {

        // trial <- r0 + F * perturbacao (trial)
        for (int i = 0; i < getD(); i++) {

            Double valor = getF() * xr0.getVariaveis().get(i)
                    + getF() * (trial.getVariaveis().get(i));

            trial.getVariaveis().set(i, reparaValor(valor));
        }

    }

    private void crossover(Individuo trial, Individuo target) {

        Random rnd = new Random();
        int j = rnd.nextInt(getD());

        for (int i = 0; i < getD(); i++) {

            if (!(rnd.nextDouble() <= getCr() || i == j)) {
                // Target
                trial.getVariaveis().set(i, target.getVariaveis().get(i));
            }

        }

    }

    private Double reparaValor(Double valor) {
        if (valor < getMinimo()) {
            valor = getMinimo();
        } else if (valor > getMaximo()) {
            valor = getMaximo();
        }

        return valor;
    }

}
