 

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.LocalDate.now;

public class Fatura implements Serializable{
    private int nif_emitente;
    private int nif_cliente;
    private String nomeEmpresa;
    private LocalDate datafatura;
    private String descricao;
    private double valorFact;
    private double valorAPagar;
    private ArrayList<int[]> listaAtividades;
    private static double taxaImposto; // Acho que não precisamos disto porque esta dentro da atividade
    
    
    // CLASS IMCOMPLETA

    public Fatura(int nifEmi,int nifCli,String nomeEmpresa, double valorFact, double tx){
        this.nif_emitente = nifEmi;
        this.nif_cliente = nifCli;
        this.nomeEmpresa = nomeEmpresa;
        this.datafatura = now();
        this.taxaImposto = tx;
        this.valorFact = valorFact;
        this.valorAPagar = this.valorFact * (1+this.getTaxaImposto());
    }
    
    public Fatura(int nifEmi, int nifCli, String nome, LocalDate data, double valorfact, double valorAPagar, double tx){
        this.nif_emitente = nifEmi;
        this.nif_cliente = nifCli;
        this.nomeEmpresa = nome;
        this.datafatura = data;
        this.valorFact = valorFact;
        this.valorAPagar = valorAPagar;
        this.taxaImposto = tx;
    }
    
    public Fatura(Fatura f){
        this.nif_emitente = f.getNifEmi();
        this.nif_cliente = f.getNifCli();
        this.nomeEmpresa = f.getNome();
        this.datafatura = f.getData();
        this.valorFact = f.getValorFact();
        this.valorAPagar = f.getValorPagar();
        this.taxaImposto = f.getTaxaImposto();
    }
    
    //Get
    public static double getTaxaImposto(){
        return taxaImposto;
    }
    public  int getNifEmi(){
        return nif_emitente;
    }
    public  int getNifCli(){
        return nif_cliente;
    }
    public  String getNome(){
        return nomeEmpresa;
    }
    public  LocalDate getData(){
        return datafatura;
    }
    public double getValorFact(){
        return valorFact;
    }
    public  double getValorPagar(){
        return valorAPagar;
    }
    //Set
    public static void setTaxaImposto(double tx){
        taxaImposto = tx;
    }
    public void setNifEmi(int nifEmi){
        nif_emitente = nifEmi;
    }
    public void setNifCli(int nifCli){
        nif_cliente = nifCli;
    }
    public void setNomeEmpresa(String nomeEmpresa){
        nomeEmpresa = nomeEmpresa;
    }
    public void setDataFatura(LocalDate dataFatura){
        dataFatura= now();
    }
    public void setValorFatura(double valorFact){
        valorFact = valorFact;
    }
    public void setValorAPagar(double valorAPagar){
        valorAPagar= valorAPagar;
    }
    
    //Metodos
    public Fatura clone(){
        return new Fatura(this);
    }
    
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (( obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Fatura o = (Fatura) obj;
        return o.getNifEmi() == this.getNifEmi() && o.getNifCli() == this.getNifCli() && 
        o.getNome() == this.getNome() &&
        o.getData() == this.getData() && o.getValorFact() == this.getValorFact() && 
        o.getValorPagar() == this.getValorPagar() &&
        o.getTaxaImposto() == this.getTaxaImposto();
        
    }
    public String ToString(){
        StringBuilder st= new StringBuilder();
        st.append("Nif de Emitente: ").append(this.nif_emitente).append("\n");
        st.append("Nif de Cliente: ").append(this.nif_cliente).append("\n");
        st.append("Nome de Empresa: ").append(this.nomeEmpresa).append("\n");
        st.append("Data Fatura: ").append(this.datafatura).append("\n");
        st.append("Discriçao: ").append(this.descricao).append("\n");
        st.append("Valor Factura: ").append(this.valorFact).append("\n");
        st.append("Valor a Pagar: ").append(this.valorAPagar).append("\n");
        st.append("Taxa de Imposto: ").append(this.taxaImposto).append("\n");
        return st.toString();
    }
}



