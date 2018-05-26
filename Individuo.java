 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Individuo extends Contribuinte implements Serializable{
    // Var. de instancia
    private List<Integer> lContAgre;//lista de n contribuinte do agregado
    private double coeficiente;//coeficente fiscal
    private ArrayList<Integer> descontos; //códigos das atividades economicas que este individuo tem possibilidade de deduzir

    //Construtor
    public Individuo(int nif){
        super(nif,"","","","");
        this.lContAgre = new ArrayList<Integer>();
        this.coeficiente = 0.0;
        this.descontos = new ArrayList<Integer>();
    }
    public Individuo(int nif, String email, String nome, String morada,
                     String pwd, List<Integer> lContAgre,
                     double coeficiente, ArrayList<Integer> descontos){
        super(nif, email, nome, morada, pwd);
        this.lContAgre = lContAgre;
        this.coeficiente = coeficiente;
        this.descontos = descontos;
    }
    public Individuo(Individuo i) {
        super(i);
        this.lContAgre = i.getlContAgre();
        this.coeficiente = i.getCoeficiente();
        this.descontos = i.getDescontos();
    }

    //Getters
    public List<Integer> getlContAgre(){
        return this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public double getCoeficiente() {
        return coeficiente;
    }
    public ArrayList<Integer> getDescontos() {
        return this.descontos.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Setters
    public void setlContAgre(List<Integer> lContAgre) {
        this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }
    public void setDescontos(List<Integer> descontos) {
        this.descontos.stream().collect(Collectors.toCollection(ArrayList::new));
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
        sb.append("Descontos: ").append(descontos).append("\n");
        return sb.toString();
    }
}

