 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Individuo extends Contribuinte implements Serializable{
    // Var. de instancia
    private List<Integer> lContAgre;//lista de n contribuinte do agregado
    private double coeficiente;//coeficente fiscal

    //Construtor
    public Individuo(int nif){
        super(nif,"","","","",0,null);
        this.lContAgre = new ArrayList<Integer>();
        this.coeficiente = 0.0;
    }
    public Individuo(int nif, String email, String nome, String morada,
                     String pwd, double gastos, ArrayList<Integer> descontos, List<Integer> lContAgre,
                     double coeficiente){
        super(nif, email, nome, morada, pwd, gastos, descontos);
        this.lContAgre = lContAgre;
        this.coeficiente = coeficiente;
    }
    public Individuo(Individuo i) {
        super(i);
        this.lContAgre = i.getlContAgre();
        this.coeficiente = i.getCoeficiente();
    }

    //Getters
    public List<Integer> getlContAgre(){
        return this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public double getCoeficiente() {
        return coeficiente;
    }

    //Setters
    public void setlContAgre(List<Integer> lContAgre) {
        this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    //Metodos TODO


    public Individuo clone(){
        return new Individuo (this);
    }
    
    public boolean equals (Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Individuo o = (Individuo) obj;
        return  o.getlContAgre().equals(this.getlContAgre()) &&
                o.getCoeficiente() == this.getCoeficiente() &&
                o.getDescontos().equals(this.getDescontos());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Lista do Agregado: ").append(lContAgre).append("\n");
        sb.append("Coeficente Fiscal: ").append(coeficiente).append("\n");
        return sb.toString();
    }
}

