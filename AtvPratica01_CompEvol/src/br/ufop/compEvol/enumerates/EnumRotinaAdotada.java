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
public enum EnumRotinaAdotada {

    TORNEIO, ELITISTA, ALEATORIO;

    public String toString() {
        if (this.equals(TORNEIO)) {
            return "Torneio";
        }

        if (this.equals(ELITISTA)) {
            return "Elitista";
        }
        
        if(this.equals(ALEATORIO))
            return "Aleatorio";
        
        return null;

    }
}
