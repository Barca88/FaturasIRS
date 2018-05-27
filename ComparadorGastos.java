import java.io.Serializable;
import java.util.Comparator;

public class ComparadorGastos implements Comparator<Contribuinte>, Serializable{
    public int compare(Contribuinte a, Contribuinte b){
        if(b.getGastos() < a.getGastos())
          return 1;
        else if(a.getGastos() < b.getGastos())
          return -1;
    return 0;
    }
}
