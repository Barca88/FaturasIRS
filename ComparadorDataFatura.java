import java.io.Serializable;
import java.util.Comparator;
import java.time.LocalDate;


public class ComparadorDataFatura implements Comparator<Fatura>, Serializable{
    public int compare(Fatura a, Fatura b){

	return b.getData().compareTo(a.getData());

	}
}
