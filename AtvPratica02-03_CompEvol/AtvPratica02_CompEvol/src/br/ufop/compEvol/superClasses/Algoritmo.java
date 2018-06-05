/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.superClasses;

import br.ufop.compEvol.components.Execucao;
import br.ufop.compEvol.components.Individuo;
import br.ufop.compEvol.components.Populacao;
import br.ufop.compEvol.components.Problema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Algoritmo {

    ArrayList<Execucao> caso1 = new ArrayList<>();
    ArrayList<Execucao> caso2 = new ArrayList<>();
    
    Integer tamanhoPopulacao;
    // Taxa de crossover - operador principal
    Double taxaCrossover;
    // Taxa de mutação - operador secundário
    Double taxaMutacao;
    // Critério de parada - número de gerações
    Integer geracoes;

    // Dados do problema
    // Problema - Rastrigin - Minimização
    Problema problema;
    // Mínimo
    Double minimo;
    // Máximo
    Double maximo;
    // Variáveis
    Integer nVariaveis;
    //--------------------------------------------------------FIm do Genetico
    
    //Evolucionario -------------------------------------------------------------
    
        // Parametros - problema - DeJong
        //já presentes anteriormente
    // Parametros - ES
    private Integer mu; // Tamanho da populacao
    private Integer lambda; // numero de descendentes
    //--------------------------------------------------------Fim do Evolucionario
    
    //Differential
    
    // Criterio de parada
    private Integer gmax;
    // Numero de variaveis
    private Integer D;
    // Tamanho da populacao
    private Integer Np;
    // Coeficiente de mutacao
    private Double F;
    // Coeficiente de Crossover
    private Double Cr;
    //--------------------------------------------------FIm Differential
    
    //Genetico Constructor
    public Algoritmo(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema, Double minimo, Double maximo, Integer nVariaveis) {
        this.tamanhoPopulacao = tamanho;
        this.taxaCrossover = pCrossover;
        this.taxaMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;

    }
    
    //Evolucionário Constructor
    public Algoritmo(Double minimo, Double maximo, Integer nVariaveis, Problema problema, Integer mu, Integer lambda, Integer geracoes, Double pMutacao){
        
        this.taxaMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVariaveis = nVariaveis;
        this.mu = mu;
        this.lambda = lambda;
    }
    
    //Differential Constructor
    public Algoritmo(Double minimo, Double maximo, Problema problema, Integer gmax, Integer D, Integer Np, Double F, Double Cr){
        //inserir atribuições
        this.minimo = minimo;
        this.maximo = maximo;
        this.problema = problema;
        this.gmax = gmax;
        this.D = D;
        this.Np = Np;
        this.F = F;
        this.Cr = Cr;
    }

    Populacao populacao;
    Populacao novaPopulacao;
    Individuo melhorSolucao;

    public void rotinaTorneio() throws Exception {
        Random rnd = new Random();

        populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
        while (populacao.getIndividuos().size() > this.tamanhoPopulacao) {

            int ind1, ind2 = 0;

            ind1 = rnd.nextInt(populacao.getIndividuos().size());

            do {
                ind2 = rnd.nextInt(populacao.getIndividuos().size());
            } while (ind1 == ind2);

            if (populacao.getIndividuos().get(ind1).compareTo(populacao.getIndividuos().get(ind2)) >= 0) { //caso ind1 seja melhor que ind2
                populacao.getIndividuos().remove(ind2);
            }

        }

        novaPopulacao.getIndividuos().clear();
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    public void rotinaAleatoria() {
        populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

        Collections.shuffle(populacao.getIndividuos());
        //embaralha a ordem dos individuos

        Random rnd = new Random();

        while (populacao.getIndividuos().size() > this.tamanhoPopulacao) {
            populacao.getIndividuos().remove(rnd.nextInt(populacao.getIndividuos().size()));
        }

    }

    public void rotinaElitista() throws Exception {
        populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
        Collections.sort(populacao.getIndividuos());

        populacao.getIndividuos().subList(this.tamanhoPopulacao, populacao.getIndividuos().size()).clear();
        //eliminou os individuos excedentes com menor desempenho

        novaPopulacao.getIndividuos().clear();

    }
    
    public void mutacaoPorVariavel(Individuo individuo) {

        Random rnd = new Random();

        for (int i = 0; i < individuo.getVariaveis().size(); i++) {
            if (rnd.nextDouble() <= taxaMutacao) {

                // Mutacao aritmetica
                // Multiplicar rnd e inverter ou nao o sinal
                Double valor =  individuo.getVariaveis().get(i);
                // Multiplica por rnd
                valor *= rnd.nextDouble();

                // Inverter o sinal
                if (!rnd.nextBoolean()) {
                    valor = -valor;
                }

                if (valor >= this.minimo
                        && valor <= this.maximo) {
                    individuo.getVariaveis().set(i, valor);

                }

            }
        }
    }
    


    public int getTamPop() {
        return tamanhoPopulacao;
    }

    public Integer getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(Integer tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public Double getTaxaCrossover() {
        return taxaCrossover;
    }

    public void setTaxaCrossover(Double taxaCrossover) {
        this.taxaCrossover = taxaCrossover;
    }

    public Double getTaxaMutacao() {
        return taxaMutacao;
    }

    public void setTaxaMutacao(Double taxaMutacao) {
        this.taxaMutacao = taxaMutacao;
    }

    public Integer getGeracoes() {
        return geracoes;
    }

    public void setGeracoes(Integer geracoes) {
        this.geracoes = geracoes;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getnVariaveis() {
        return nVariaveis;
    }

    public void setnVariaveis(Integer nVariaveis) {
        this.nVariaveis = nVariaveis;
    }

    public Populacao getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Populacao populacao) {
        this.populacao = populacao;
    }

    public Populacao getNovaPopulacao() {
        return novaPopulacao;
    }

    public void setNovaPopulacao(Populacao novaPopulacao) {
        this.novaPopulacao = novaPopulacao;
    }

    public Integer getMu() {
        return mu;
    }

    public void setMu(Integer mu) {
        this.mu = mu;
    }

    public Integer getLambda() {
        return lambda;
    }

    public void setLambda(Integer lambda) {
        this.lambda = lambda;
    }
    
    public Integer getGmax() {
        return gmax;
    }

    public void setGmax(Integer gmax) {
        this.gmax = gmax;
    }

    public Integer getD() {
        return D;
    }

    public void setD(Integer D) {
        this.D = D;
    }

    public Integer getNp() {
        return Np;
    }

    public void setNp(Integer Np) {
        this.Np = Np;
    }

    public Double getF() {
        return F;
    }

    public void setF(Double F) {
        this.F = F;
    }

    public Double getCr() {
        return Cr;
    }

    public void setCr(Double Cr) {
        this.Cr = Cr;
    }

    public ArrayList<Execucao> getCaso1() {
        return caso1;
    }

    public void setCaso1(ArrayList<Execucao> caso1) {
        this.caso1 = caso1;
    }

    public ArrayList<Execucao> getCaso2() {
        return caso2;
    }

    public void setCaso2(ArrayList<Execucao> caso2) {
        this.caso2 = caso2;
    }
    
    
    
    
}

