import java.io.Serializable;


/**
 * Write a description of class Atividade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Atividade implements Serializable{
    // instance variables - replace the example below with your own
    private int id;
    private String nome;
    private double deducao;
    private int max;
    /**
     * Constructor for objects of class Atividade
     */
    public Atividade(int id,String nome,double d,int max){
        this.id = id;
        this.nome = nome;
        this.deducao = d;
        this.max = max;
    }
    public Atividade(Atividade a){
        this.id = a.getId();
        this.nome = a.getNome();
        this.deducao = a.getDeducao();
        this.max = a.getMax();
    }
    public int getId(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public double getDeducao(){
        return this.deducao;
    }
    public int getMax(){
        return this.max;
    }
    
    
    public Atividade clone(){
        return new Atividade(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(id).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Deducao: ").append(deducao).append("\n");
        sb.append("Maximo dedutivel: ").append(max).append("\n");
        return sb.toString();
    }
}
