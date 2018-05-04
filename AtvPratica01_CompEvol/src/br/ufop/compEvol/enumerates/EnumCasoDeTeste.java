/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.enumerates;

/**
 *
 * @author Amaral, Henrique Q.
 */
public enum EnumCasoDeTeste {
    ONE, TWO;
    
    public String toString(){
        if(this.equals(ONE)){
           return "Um";
        }else{
            return "Dois";
        }
    }
}
