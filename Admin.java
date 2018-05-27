import java.io.Serializable;

public class Admin extends Contribuinte implements Serializable{

    public Admin(int nif){
        super(nif,"","","","");
    }
    
    public Admin(int nif, String email, String nome, String morada, String pwd){
        super(nif, email, nome, morada, pwd);
    }
    
    public Admin(Admin a){
        super(a);
    }
    
    public Admin clone(){
        return new Admin(this);
    }
}
