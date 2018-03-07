package FaturasIRS;


import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.LocalDate.now;

public class Fatura {
    private int nif_emitente;
    private int nif_cliente;
    private String nomeEmpresa;
    private LocalDate datafatura;
    private String descricao;
    private double valorFact;
    private double valorAPagar;
    private ArrayList<int> listaAtividades;
    private static double taxaImposto; // Acho que não precisamos disto porque esta dentro da atividade
    
    
    // CLASS IMCOMPLETA

    public Fatura(int nif,String nomeEmpresa, double valorFact, double tx){
        this.nif = nif;
        this.nomeEmpresa = nomeEmpresa;
        this.datafatura = now();
        this.taxaImposto = tx;
        this.valorFact = valorFact;
        this.valorAPagar = this.valorFact * (1+this.getTaxaImposto());
    }
    public Fatura(int nif, String nome, LocalDate data, double valorfact, double valorAPagar, double tx){
        this.nif = nif;
        this.nomeEmpresa = nome;
        this.datafatura = data;
        this.valorFact = valorfact;
        this.valorAPagar = valorAPagar;
        this.taxaImposto = tx;
    }
    public Fatura(Fatura f){
        this.nif = f.getNif();
        this.nomeEmpresa = f.getNome();
        this.datafatura = f.getData();
        this.valorFact = f.getValorFact();
        this.valorAPagar = f.getValorPagar();
        this.taxaImposto = f.getTaxaImposto();
    }
    public static double getTaxaImposto(){
        return taxaImposto;
    }
    public static void setTaxaImposto(double tx){
        taxaImposto = tx;
    }
}

