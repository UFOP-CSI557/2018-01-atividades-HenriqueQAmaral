/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufop.compEvol.components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;


/**
 *
 * @author Amaral, Henrique Q.
 */
public class GeradorSaida {
    
    public static void escreverArquivo(String texto, String nomeArq) throws Exception{
        
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArq));
        
        bw.write(texto);
        bw.close();
        
        System.out.println("Arquivo de LOG criado com sucesso!");
    }
}
