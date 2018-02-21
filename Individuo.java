package FaturasIRS;

import java.io.Serializable;
import java.util.List;

public class Individuo extends Contribuinte implements Serializable{
    // Var. de instancia

    private int qDepAgre; //quantidade de depententes
    private List<int> lContAgre;//lista de n contribuinte do agregado
    private double coeficiente;//todo coeficente fiscal
    private List<Atividade> descontos; //todo códigos das atividades economicas que este individuo tem possibilidade de deduzir

    //Construtor
    public Individuo(int nif){
        super(nif,"","","","");
        this.qDepAgre = 0;
        this.lContAgre = new ArrayList<int>();
        this.coeficiente = 0.0;
        this.descontos = new ArrayList<Atividade>();
        }
    }
    public Individuo(int nif, String email, String nome, String morada,
                     String pwd, int qDepAgre, List<int> lContAgre,
                     double coeficiente, List<Atividade> descontos ) {
        super(nif, email, nome, morada, pwd);
        this.qDepAgre = qDepAgre;
        this.lContAgre = lContAgre;
        this.coeficiente = coeficiente;
        this.descontos = descontos;
    }
    public Individuo(Individuo i) {
        super(i);
        this.qDepAgre = // TODO sdbfiasdfbgp
    }

    //Getters
    //TODO

}