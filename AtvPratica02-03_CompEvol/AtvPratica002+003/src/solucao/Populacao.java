/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solucao;

import java.util.ArrayList;
import java.util.Collections;
import problema.Problema;

/**
 *
 * @author fernando
 */
public abstract class Populacao<T> {

    ArrayList<Individuo<T>> individuos = new ArrayList<>();
    int tamanho;
    Problema problema;

    public ArrayList<Individuo<T>> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo<T>> individuos) {
        this.individuos = individuos;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Problema getProblema() {
        return problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }
    
    public void avaliar() {
        for(Individuo individuo : this.individuos) {
            this.problema.calcularFuncaoObjetivo(individuo);
        }
    }
    
    public Individuo getMelhorIndividuo() {
        return Collections.min(this.individuos);
    }    
    
    public Individuo getPiorIndividuo(){
        return Collections.max(this.individuos);
    }

    abstract void criar();
    
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
    
}
