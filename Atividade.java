


/**
 * Write a description of class Atividade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Atividade
{
    // instance variables - replace the example below with your own
    private int id;
    private String nome;
    private double deducao;
    
    /**
     * Constructor for objects of class Atividade
     */
    public Atividade(int id,String nome,double d){
        this.id = id;
        this.nome = nome;
        this.deducao = d;
    }
    public Atividade(Atividade a){
        this.id = a.getId();
        this.nome = a.getNome();
        this.deducao = a.getDeducao();
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
}
