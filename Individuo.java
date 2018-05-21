 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Individuo extends Contribuinte implements Serializable{
    // Var. de instancia

    private int qDepAgre; //quantidade de depententes no Agregado Familiar
    private List<int[]> lContAgre;//lista de n contribuinte do agregado
    private double coeficiente;//coeficente fiscal
    private ArrayList<Atividade[]> descontos; //códigos das atividades economicas que este individuo tem possibilidade de deduzir

    //Construtor
    public Individuo(int nif){
        super(nif,"","","","");
        this.qDepAgre = 0;
        this.lContAgre = new ArrayList<int[]>();
        this.coeficiente = 0.0;
        this.descontos = new ArrayList<Atividade[]>();
    }
    public Individuo(int nif, String email, String nome, String morada,
                     String pwd, int qDepAgre, List<int[]> lContAgre,
                     double coeficiente, ArrayList<Atividade[]> descontos){
        super(nif, email, nome, morada, pwd);
        this.qDepAgre = qDepAgre;
        this.lContAgre = lContAgre;
        this.coeficiente = coeficiente;
        this.descontos = descontos;
    }
    public Individuo(Individuo i) {
        super(i);
        this.qDepAgre = i.getqDepAgre();
        this.lContAgre = i.getlContAgre();
        this.coeficiente = i.getCoeficiente();
        this.descontos = i.getDescontos();
    }

    //Getters
    public int getqDepAgre() {
        return qDepAgre;
    }
    public List<int[]> getlContAgre(){
        return this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public double getCoeficiente() {
        return coeficiente;
    }
    public ArrayList<Atividade[]> getDescontos() {
        return this.descontos.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Setters
    public void setqDepAgre(int qDepAgre) {
        this.qDepAgre = qDepAgre;
    }
    public void setlContAgre(List<int[]> lContAgre) {
        this.lContAgre.stream().collect(Collectors.toCollection(ArrayList::new));
    }
    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }
    public void setDescontos(List<Atividade> descontos) {
        this.descontos.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Metodos TODO


    public Individuo clone(){
        return new Individuo (this);
    }
    public boolean equals (Object obj) { // Todo porque é que nao se compara as listas?
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Individuo o = (Individuo) obj;
        return  o.getqDepAgre() == this.getqDepAgre() &&
                o.getCoeficiente() == this.getCoeficiente();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Quantidade de Dependentes no Agregado Familiar: ").append(qDepAgre).append("\n");
        sb.append("Coeficente Fiscal").append(coeficiente).append("\n");
        return sb.toString();
    }
}

