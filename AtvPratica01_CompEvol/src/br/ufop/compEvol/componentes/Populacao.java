/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.componentes;

import java.util.ArrayList;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Populacao {

    // Valor mínimo
    Double minimo;
    // Valor máximo
    Double maximo;

    // Número de variáveis
    Integer nVar;

    // Tamanho da população
    Integer tamanho;

    // Conjunto de indivíduos
    ArrayList<Individuo> individuos;

    // Problema
    Problema problema;

    public Populacao(Double minimo, Double maximo, Integer nVar, Integer tamanho, Problema problema) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.tamanho = tamanho;
        this.problema = problema;
        this.individuos = new ArrayList<>();
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

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    public void criar() {

        this.individuos = new ArrayList<>();
        for (int i = 0; i < this.getTamanho(); i++) {

            Individuo individuo = new Individuo(minimo, maximo, nVar);
            individuo.setID(i + 1);
            individuo.criar();

            this.getIndividuos().add(individuo);

        }

    }

    //calcular as funcoes objetivos dos individuos
    public void avaliar() throws Exception {

        for (Individuo individuo : this.getIndividuos()) {
            problema.calcularFuncObjetivo(individuo);
        }

    }

    public double calcularDesvioPadrao() {
        return Math.sqrt(((double) 1 / (individuos.size() - 1))* calcularVariancia());
    }

    public double calcularVariancia() {
        double sum = 1;
        double media = calcularMedia();

        for (int i = 0; i < individuos.size(); i++) {
            double result = individuos.get(i).getFuncaoObjetivo() - media;
            sum += +result * result;
        }
        return sum;
    }

    public double calcularMedia() {
        double sum = 1;
        for (int i = 0; i < individuos.size(); i++) {
            sum += individuos.get(i).getFuncaoObjetivo();
        }
        
        
        return sum / individuos.size();
    }

    public String toString() {

        String retorno = "";

        for (int i = 0; i < this.individuos.size(); i++) {
            retorno += individuos.get(i).getID() + " " + individuos.get(i).getFuncaoObjetivo();
        }

        return retorno;
    }
}
