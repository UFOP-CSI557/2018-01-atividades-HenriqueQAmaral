/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solucao;

/**
 *
 * @author Amaral, Henrique Q.
 */
import java.time.Duration;

/**
 *
 * @author Amaral, Henrique Q.
 */
public class Execucao implements Comparable<Execucao>{
    
    private Individuo melhor;
    private Individuo pior;
    private int ID;
    private int geracaoDecisiva;
    private Integer tamanhoPopulacao;
    private Double taxaCrossover = 0.0;
    private Double taxaMutacao = 0.0;
    private Double desvioPadrao;
    
    Duration duracao = Duration.ZERO;

    public Execucao(Integer tamanhoPop, Double txCrossover, Double txMutacao){
        this.tamanhoPopulacao = tamanhoPop;
        this.taxaCrossover = txCrossover;
        this.taxaMutacao = txMutacao;
    }
    
    public Execucao(Integer tamanhoPop){
        this.tamanhoPopulacao = tamanhoPopulacao;
    }
    
    public Individuo getMelhor() {
        return melhor;
    }

    public void setMelhor(Individuo melhor) {
        this.melhor = melhor;
    }

    public int getGeracaoDecisiva() {
        return geracaoDecisiva;
    }

    public void setGeracaoDecisiva(int geracaoDecisiva) {
        this.geracaoDecisiva = geracaoDecisiva;
    }

    public Individuo getPior() {
        return pior;
    }

    public Double getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(Double desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
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

    public void setPior(Individuo pior) {
        this.pior = pior;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }
    
    @Override
    public String toString(){
        
        String saida = "";
        saida += "Execução nº "+ID;
        saida += "\nGeração Decisiva: "+geracaoDecisiva+" (última a possuir um indivíduo não ótimo)";
        saida += "\nMelhor indivíduo: Func. Objetivo= "+melhor.getFuncaoObjetivo();
//        saida += "\nPior individuo: Func. Objetivo= "+pior.getFuncaoObjetivo();
        saida += "\nParâmetros utilizados:\n- "+tamanhoPopulacao+" indivíduos;\n- Crossover: "+taxaCrossover*100+"%;";
        saida += "\n- Mutação: "+taxaMutacao*100+"%;";
        saida += "\n-Desvio Padrão: "+desvioPadrao;
        saida += "\nTempo de execução: "+duracao.toNanos()+" nanosegundos | "+duracao.toMillis()+" milissegundos\n";
        
        return saida; 
    }
    
    
    @Override
    public int compareTo(Execucao o) {
        if(this.geracaoDecisiva > o.geracaoDecisiva)
            return 1;
        else if(this.geracaoDecisiva < o.geracaoDecisiva)
            return -1;
        else 
            if(this.melhor.getFuncaoObjetivo() < o.melhor.getFuncaoObjetivo()){
                return -1;
            }else{
                return 1;
            }
            
            
    }
}
