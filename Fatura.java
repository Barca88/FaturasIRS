

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.LocalDate.now;
import java.util.List;
public class Fatura implements Serializable{
    private long id;
    private int nif_emitente;
    private int nif_cliente;
    private String nomeEmpresa;
    private LocalDate datafatura;
    private String descricao;
    private double valorFact;
    private ArrayList<Integer> listaAtividades;
    private static double taxaImposto; 


    // CLASS IMCOMPLETA
    public Fatura(long id, int nifEmi, int nifCli, String nomeEmpresa, String descricao,
            double valorFact, List<Integer> li, double tx){
        this.id = id;
        this.nif_emitente = nifEmi;
        this.nif_cliente = nifCli;
        this.nomeEmpresa = nomeEmpresa;
        this.datafatura = now();
        this.descricao = descricao;
        this.valorFact = valorFact;
        this.listaAtividades = (ArrayList<Integer>)li;
        this.taxaImposto = tx;
    }
    public Fatura(Fatura f){
        this.id = f.getId();
        this.nif_emitente = f.getNifEmi();
        this.nif_cliente = f.getNifCli();
        this.nomeEmpresa = f.getNome();
        this.datafatura = f.getData();
        this.descricao = f.getDescricao();
        this.valorFact = f.getValorFact();
        this.taxaImposto = f.getTaxaImposto();
        this.listaAtividades = f.getListaAtividades();
    }

    //Get

    public long getId() {
        return id;
    }
    public static double getTaxaImposto(){
        return taxaImposto;
    }
    public int getNifEmi(){
        return nif_emitente;
    }
    public int getNifCli(){
        return nif_cliente;
    }
    public String getNome(){
        return nomeEmpresa;
    }
    public LocalDate getData(){
        return LocalDate.of(this.datafatura.getYear(), this.datafatura.getMonthValue(),this.datafatura.getDayOfMonth());
    }
    public String getDescricao(){
        return descricao;
    }
    public double getValorFact(){
        return valorFact;
    }
    public double getValorPagar(){
        return this.valorFact+(1+(100/this.taxaImposto));
    }
    public ArrayList<Integer> getListaAtividades() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        this.listaAtividades.forEach(e->lista.add(e));
        return lista;
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
        dataFatura = now();
    }
    public void setDescricao(String descricao){
        descricao = descricao;
    }
    public void setValorFatura(double valorFact){
        valorFact = valorFact;
    }
    public void setListaAtividades(ArrayList<Integer> listaAtividades) {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        listaAtividades.forEach(e->lista.add(e));
        this.listaAtividades = lista;
    }

    //Metodos
    public boolean dedutivel(){
        if(this.listaAtividades.size() == 1){
            return true;
        }
        return false;
    }
    public Fatura clone(){
        return new Fatura(this);
    }

    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (( obj == null) || (obj.getClass() != this.getClass()))
            return false;
        Fatura o = (Fatura) obj;
        return o.getId() == this.getId() &&
               o.getNifEmi() == this.getNifEmi() &&
               o.getNifCli() == this.getNifCli() &&
               o.getNome() == this.getNome() &&
               o.getData() == this.getData() &&
               o.getDescricao() == this.getDescricao() &&
               o.getValorFact() == this.getValorFact() &&
               o.getListaAtividades().equals(this.getListaAtividades()) &&
               o.getTaxaImposto() == this.getTaxaImposto();

    }

    public String toString(){
        StringBuilder st= new StringBuilder();
        st.append("Id: ").append(this.id).append("\n");
        st.append("Nif de Emitente: ").append(this.nif_emitente).append("\n");
        st.append("Nif de Cliente: ").append(this.nif_cliente).append("\n");
        st.append("Nome de Empresa: ").append(this.nomeEmpresa).append("\n");
        st.append("Data Fatura: ").append(this.datafatura).append("\n");
        st.append("Discriçao: ").append(this.descricao).append("\n");
        st.append("Valor Factura: ").append(this.valorFact).append("\n");
        st.append("Atividades: ").append(this.listaAtividades).append("\n");
        st.append("Taxa de Imposto: ").append(this.taxaImposto).append("\n");
        return st.toString();
    }
}
