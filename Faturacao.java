package FaturasIRS;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Faturacao implements Serializable {
    private HashMap<int,Contribuinte> users;
    private HashMap<long, Fatura> faturas;
    private Contribuinte logedIn;

    public Faturacao(){
        this.users = new HashMap<int, Contribuinte>();
        this.faturas = new HashMap<long, Fatura>();
        this.logedIn = null;
    }
    public Faturacao(HashMap<int,Contribuinte> users, HashMap<long,Fatura> faturas,
                     Contribuinte logedIn){
        this.users = users;
        this.faturas = faturas;
        this.logedIn = logedIn;
    }
    public Faturacao(Faturacao f){
        this.users = f.getUsers();
        this.faturas = f.getFaturas();
        this.logedIn = f.getLog();
    }

    //Getters
    public Map<long, Contribuinte> getUsers() {
        return this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(), c->c.getValue()));
    }
    public Map<long, Fatura> getFaturas() {
        return this.faturas.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }
    public Contribuinte getLogedIn() {
        return logedIn.clone();
    }

    //Setters
    public void setUsers(HashMap<long, Contribuinte> users) {
        this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }

    public void setFaturas(HashMap<long, Fatura> faturas) {
        this.faturas.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }
    public void setLogedIn(Contribuinte logedIn) {
        this.logedIn = logedIn.clone();
    }

    /**
     * Gravar o estado da aplicação num determinado ficheiro.
     */
    public void gravaObj() throws IOException {
        ObjectOutputStream sv = new ObjectOutputStream(new
                FileOutputStream("faturacao_estado"));
        sv.writeObject(this);
        sv.flush();
        sv.close();
    }
    /**
     * Iniciar a aplicação com o estado guardado num determinado ficheiro.
     */
    public static Faturacao leObj() throws IOException, ClassNotFoundException{
        ObjectInputStream oi = new ObjectInputStream(new
                FileInputStream("faturacao_estado"));

        Faturacao f = (Faturacao) oi.readObject();

        oi.close();
        return f;
    }
    /**
     * Fazer um ficheiro de texto log com toda a informação na aplicacao no momento em que é fechada.
     */
    public void log(String f, boolean ap) throws IOException {
        FileWriter fw = new FileWriter(f, ap);
        fw.write("\n========================= LOG ==========================\n");
        fw.write(this.toString());
        fw.write("\n========================= LOG ==========================\n");
        fw.flush();
        fw.close();
    }
    /**
     * Regista Contribuinte na aplicação --TODO criar a exeption
     */
    public void registarContribuinte(Contribuinte c) throws ContribuinteExistenteException{

        if(this.users.containsKey(c.getNif())){
            throw new ContribuinteExistenteException ("Já existe este Contribuinte");
        }else this.users.put(Contribuinte.getNif(),c);
    }

    /**
     * Inicia sessao com nif e password. --TODO Exeption
     */
    public void iniciaSessao(int nif,String password) throws SemAutorizacaoException {

        if(this.logedIn == null){

            if(users.containsKey(nif)){
                Contribuinte user = users.get(nif);
                if (password.equals(user.getPwd())){
                    logedIn = user;
                }else throw new SemAutorizacaoException("Credenciais Erradas");
            }else throw new SemAutorizacaoException("Credenciais Erradas");
        }else throw new SemAutorizacaoException("Ja tem sessão iniciada");
    }
    /**
     * Funcao que termina a sessao do contribuinte logado.
     */
    public void terminaSessao(){
        this.logedIn = null;
    }
}
