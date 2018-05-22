import java.io.Serializable;
import java.util.Comparator;


public class ComparadorValorFatura implements Comparator<Fatura>, Serializable{
    public int compare(Fatura a, Fatura b){
        if(b.getValorPagar() < a.getValorPagar())
          return -1;
        else if(a.getValorPagar() < b.getValorPagar())
          return 1;
    return 0;
    }
}
