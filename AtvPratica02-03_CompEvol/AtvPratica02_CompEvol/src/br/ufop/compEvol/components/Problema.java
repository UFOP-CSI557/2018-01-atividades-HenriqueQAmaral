/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.components;

import br.ufop.compEvol.components.Individuo;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Problema {
    
    private Integer nVariaveis;
    
    public Problema(Integer nVariaveis){
        this.nVariaveis = nVariaveis;
    }
    
    public void calcularFuncObjetivo(Individuo ind) throws Exception{
        //Rastrigin function
        
        double sum = 0;
        
        for(int i = 0; i < ind.getnVar(); i++){
            sum += (Math.pow(ind.getVariaveis().get(i), 2) - 10 * Math.cos(2 * Math.PI*ind.getVariaveis().get(i)));
        }
        ind.setFuncaoObjetivo(10 * ind.getnVar() + sum);
    }
    
    public int getDimensao(){
        return this.nVariaveis;
    }
}
