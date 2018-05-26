import java.util.ArrayList;
import java.util.HashMap;
/**
 * Classe Utilizada apenas para guardar o historico de classificaçoes das faturas quando esta sofre mudanças nas atividades
 *
 * @author Grupo 51
 */
public class Historico
{
    // var. instancia
    private HashMap<Long,ArrayList<Fatura>> hist;

    /**
     * Construtores
     */
    public Historico() {
        this.hist = new HashMap<Long,ArrayList<Fatura>>();
    }
    public Historico(HashMap<Long,ArrayList<Fatura>> m){
        HashMap map = new HashMap<Long,ArrayList<Fatura>>();
        m.forEach((k,v)->{

            ArrayList aux = new ArrayList<Fatura>();
            v.forEach(c->aux.add(c.clone()));
            map.put(k,aux);

        });
        this.hist = map;
    }
    public Historico(Historico h){
        this.hist = h.getHist();
    }
    //get
    public HashMap<Long,ArrayList<Fatura>> getHist(){
        HashMap map = new HashMap<Long,ArrayList<Fatura>>();
        this.hist.forEach((k,v)->{

            ArrayList aux = new ArrayList<Fatura>();
            v.forEach(c->aux.add(c.clone()));
            map.put(k,aux);
        });
        return map;
    }

    public void add(Fatura f){
        if(this.hist.containsKey(f.getId())){
            this.hist.get(f.getId()).add(f.clone());
        }else {
            ArrayList<Fatura> aux = new ArrayList<Fatura>();
            aux.add(f.clone());
            this.hist.put(f.getId(),aux);
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Historico: ").append(hist).append("\n");
        return sb.toString();
    }

}
