import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class ComparadorNumeroFaturas implements Comparator<Contribuinte>, Serializable{
    public int compare(Contribuinte a, Contribuinte b){
        Coletivo c = (Coletivo) a;
        Coletivo d = (Coletivo) b;
        if(d.getFaturas().size() < c.getFaturas().size())
          return 1;
        else if(c.getFaturas().size() < d.getFaturas().size())
          return -1;
    return 0;
    }
}
