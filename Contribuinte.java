
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Contribuinte implements Serializable{
    // variaveis de instancia
    private int nif;
    private String email;
    private String nome;
    private String morada;
    private String pwd;
    private double totalGasto;
    private ArrayList<Integer> descontos; //códigos das atividades economicas que este individuo tem possibilidade de deduzir

    //Construtores
    public Contribuinte(){ // help no criar nif nao pode ser 0 vou ter mais do que 1 iguais
        this.nif = 0; // TODO tenho que ir buscar o ultimo e acrescentar 1
        this.email = "null";
        this.nome = "null";
        this.morada = "null";
        this.pwd = "null";
        this.totalGasto = 0;
        this.descontos = null;
    }
    public Contribuinte(int nif,String email,String nome,String morada,String pwd,double gastos, ArrayList<Integer> descontos){
        this.nif = nif;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.pwd = pwd;
        this.totalGasto = gastos;
        this.descontos = descontos;
    }
    public Contribuinte(Contribuinte c){
        this.nif = c.getNif();
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.morada = c.getMorada();
        this.pwd = c.getPwd();
        this.totalGasto = c.getGastos();
        this.descontos = c.getDescontos();
    }

    //Getters
    public int getNif() {
        return this.nif;
    }
    public String getEmail() {
        return this.email;
    }
    public String getNome() {
        return this.nome;
    }
    public String getMorada() {
        return this.morada;
    }
    public String getPwd() {
        return this.pwd;
    }
    public double getGastos() {
        return this.totalGasto;
    }
    public ArrayList<Integer> getDescontos() {
        return this.descontos.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public void setGastos(double gastos) {
        this.totalGasto = gastos;
    }
    public void setDescontos(List<Integer> descontos) {
        this.descontos = descontos.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    //Metodos
    
    public Contribuinte clone(){
        Contribuinte c = new Contribuinte(this);
        return c;
    };
    public boolean equals(Object obj){
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Contribuinte c = (Contribuinte) obj;
        return c.getNif() == nif            &&
               c.getEmail().equals(email)   &&
               c.getNome().equals(nome)     &&
               c.getMorada().equals(morada) &&
               c.getPwd().equals(pwd)       &&
               c.getGastos() == (totalGasto)&&
               c.getDescontos().equals(descontos);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NIF: ").append(nif).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Morada: ").append(morada).append("\n");
        sb.append("Pwd: ").append(pwd).append("\n"); // squê era fixe encriptar
        sb.append("Despesa total: ").append(totalGasto).append("\n");
        sb.append("Descontos: ").append(descontos).append("\n");
        return sb.toString();
    }
}
