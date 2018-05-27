import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Contribuinte implements Serializable{

    public Admin(int nif){
        super(nif,"","","","",0,null);
    }
    
    public Admin(int nif, String email, String nome, String morada, String pwd, double gastos, ArrayList<Integer> descontos){
        super(nif, email, nome, morada, pwd, gastos, descontos);
    }
    
    public Admin(Admin a){
        super(a);
    }
    
    public Admin clone(){
        return new Admin(this);
    }
}
