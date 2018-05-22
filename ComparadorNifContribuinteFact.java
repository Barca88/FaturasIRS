import java.io.Serializable;
import java.util.Comparator;


public class ComparadorNifContribuinteFact implements Comparator<Fatura>, Serializable{
    public int compare(Fatura a, Fatura b){
        if(b.getNifCli() < a.getNifCli())
          return -1;
        else if(a.getNifCli() < b.getNifCli())
          return 1;
    return 0;
    }
}
