

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Coletivo extends Contribuinte implements Serializable{
    private List<int[]> atividades;
    private double deducao;
    private List<long[]> faturas;//ids de faturas passadas por este objeto

    public Coletivo (int nif){
        super(nif,"","","","");
        this.atividades = new ArrayList<int[]>();
        this.deducao = 0;
        this.faturas = new ArrayList<long[]>();
    }
    public Coletivo (int nif, String email, String nome, String morada, String pwd){
        super(nif, email, nome, morada, pwd);
        this.atividades = new ArrayList<int[]>();
        this.deducao = 0;
        this.faturas = new ArrayList<long[]>();
    }
    public Coletivo ( Coletivo c){
        super(c);
        this.atividades = c.getAtividades();
        this.deducao = c.getDeducao();
        this.faturas = c.getFaturas();
    }

    //Getters
    public List<int[]> getAtividades() {
        return this.atividades.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public double getDeducao() {
        return deducao;
    }
    public List<long[]> getFaturas() {
        return this.faturas.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Setters
    public void setAtividades(List<int[]> atividades) {
        this.atividades = atividades.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public void setDeducao(double deducao) {
        this.deducao = deducao;
    }

    public void setFaturas(List<long[]> faturas) {
        this.faturas = faturas.stream().collect(Collectors.toCollection(ArrayList::new));
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
        return  super.equals(c);
    }
}

