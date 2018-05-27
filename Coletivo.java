

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Coletivo extends Contribuinte implements Serializable{
    private double deducao;
    private List<Long> faturas;//ids de faturas passadas por este objeto

    public Coletivo (int nif){
        super(nif,"","","","",0,null);
        this.deducao = 0;
        this.faturas = new ArrayList<Long>();
    }
    public Coletivo (int nif, String email, String nome, String morada, String pwd, double gastos, ArrayList<Integer> descontos){
        super(nif, email, nome, morada, pwd, gastos,  descontos);
        this.deducao = 0;
        this.faturas = new ArrayList<Long>();
    }
    public Coletivo ( Coletivo c){
        super(c);
        this.deducao = c.getDeducao();
        this.faturas = c.getFaturas();
    }

    //Getters
    public double getDeducao() {
        return deducao;
    }
    public List<Long> getFaturas() {
        return this.faturas.stream()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    //Setters
    public void setDeducao(double deducao) {
        this.deducao = deducao;
    }
    public void setFaturas(List<Long> faturas) {
        this.faturas = faturas.stream()
            .collect(Collectors.toCollection(ArrayList::new));
    }

    //Metodos
    public Coletivo clone(){
        return new Coletivo(this);
    }

    public boolean equals (Object obj){
        if(this == obj)
            return true;
        if(obj == null || this.getClass() != obj.getClass())
            return false;
        Coletivo c = (Coletivo) obj;
        return  super.equals(c) &&
                c.getDeducao() == this.getDeducao() &&
                c.getFaturas().equals(this.getFaturas());
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Deduçao: ").append(deducao).append("\n");
        sb.append("Faturas: ").append(faturas).append("\n");
        return sb.toString();
    }
}

