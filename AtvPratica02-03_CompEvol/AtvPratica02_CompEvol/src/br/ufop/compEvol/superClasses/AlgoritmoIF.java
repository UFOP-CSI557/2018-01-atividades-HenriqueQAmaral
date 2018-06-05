/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.superClasses;

import br.ufop.compEvol.components.Execucao;
import java.util.ArrayList;

/**
 *
 * @author Amaral, Henrique Q.
 */
public interface AlgoritmoIF {
    
    public Double executar(ArrayList<Execucao> execucoesCaso) throws Exception;
    
    public ArrayList<Execucao> getCaso1();
    public ArrayList<Execucao> getCaso2();
}
