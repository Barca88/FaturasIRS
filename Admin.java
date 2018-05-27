import java.io.Serializable;

public class Admin extends Contribuinte implements Serializable{

    public Admin(int nif){
        super(nif,"","","","",0);
    }
    
    public Admin(int nif, String email, String nome, String morada, String pwd, double gastos){
        super(nif, email, nome, morada, pwd, gastos);
    }
    
    public Admin(Admin a){
        super(a);
    }
    
    public Admin clone(){
        return new Admin(this);
    }
}
