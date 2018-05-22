

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayList;
import java.lang.Object;
import java.time.LocalDate;

public class Faturacao implements Serializable {
    private Map<Integer,Contribuinte> users;
    private Map<Long, Fatura> faturas;
    private Map<Long, ArrayList<Fatura>> fatRegisto;
    private Contribuinte logedIn;

    public Faturacao(){
        this.users = new HashMap<Integer, Contribuinte>();
        this.faturas = new HashMap<Long, Fatura>();
        this.fatRegisto = new HashMap<Long, ArrayList<Fatura>>();
        this.logedIn = null;
    }
    public Faturacao(Map<Integer,Contribuinte> users, Map<Long,Fatura> faturas,
                     Contribuinte logedIn){
        this.users = users;
        this.faturas = faturas;
        this.fatRegisto = new HashMap<Long, ArrayList<Fatura>>();
        this.logedIn = logedIn;
    }
    public Faturacao(Faturacao f){
        this.users = f.getUsers();
        this.faturas = f.getFaturas();
        this.fatRegisto = f.getFatRegisto();
        this.logedIn = f.getLogedIn();
    }

    //Getters
    public Map<Integer, Contribuinte> getUsers() {
        return this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public Map<Long, Fatura> getFaturas() {
        return this.faturas.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public Contribuinte getLogedIn() {
        return this.logedIn.clone();
    }
    /**
     * ==============================POR FAZER=============================== --TODO
     */
    public Map<Long, ArrayList<Fatura>> getFatRegisto() {
        return this.fatRegisto; //FAZER CLONE DISTO!!!!!!!
    }

    //Setters
    public void setUsers(HashMap<Long, Contribuinte> users) {
        this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }
    public void setFaturas(HashMap<Long, Fatura> faturas) {
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
    
    /**====================Metodos========================*/
    
    /**
     * Regista Contribuinte na aplicação --TODO criar a exeption
     */
    public void registarContribuinte(Contribuinte c) throws ContribuinteExistenteException{

        if(this.users.containsKey(c.getNif())){
            throw new ContribuinteExistenteException ("Já existe este Contribuinte");
        }else ((Map)this.users).put(c.getNif(),c);
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
    /**
     * Cria uma factura.
     */
    public void novaFactura(int nif_cliente, String descricao, double valorFact, ArrayList<Integer> atividades, double taxaImposto)
    throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Coletivo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        long idFatura = Collections.max(this.faturas.keySet(),null) + 1;
        Fatura f = new Fatura(idFatura,this.logedIn.getNif(),nif_cliente,this.logedIn.getNome(),descricao,valorFact,atividades,taxaImposto);
        this.faturas.put(idFatura,f);
    }
    /**
     * Devolve a lista das despesas emitidas em nome do individuo.
     */
    public ArrayList<Fatura> despesasEmitidas() throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Individuo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        ArrayList<Fatura> despesas = new ArrayList<Fatura>();
        for (Fatura f : this.faturas.values()){
            if (this.logedIn.getNif() == f.getNifCli())
                despesas.add(f.clone());
        }
        return despesas;
    }
    /**
     * Devolve o montante de deduçao fiscal do agregado familiar do individuo.
     * ==============================POR FAZER=============================== --TODO
     */
    public long deducaoFiscal () throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Individuo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        return 1;
    }
    /**
     * Associa uma classificaço de atividade economica a uma fatura
     * ==============================POR FAZER=============================== --TODO
     */
    public void classificaFatura(long idFatura){
    }
    /**
     * Corrige a classificaço de atividade economica de uma fatura
     * ==============================POR FAZER=============================== --TODO
     */
    public void corrigeClassificaFatura(long idFatura){
    }
    /**
     * Devolve a lista das facturas correspondentes a uma determinada empresa por data de emissao.
     */
    public ArrayList<Fatura> facturasEmpresaData(String nomeEmpresa){
        ArrayList<Fatura> lista = new ArrayList<Fatura>();
        for (Fatura f : this.faturas.values()){
            if (f.getNome().equals(nomeEmpresa))
                lista.add(f.clone());
        }
        Collections.sort(lista, new ComparadorDataFatura());
        return lista;
    }
    /**
     * Devolve a lista das facturas correspondentes a uma determinada empresa por valor.
     */
    public ArrayList<Fatura> facturasEmpresaValor(String nomeEmpresa){
        ArrayList<Fatura> lista = new ArrayList<Fatura>();
        for (Fatura f : this.faturas.values()){
            if (f.getNome().equals(nomeEmpresa))
                lista.add(f.clone());
        }
        Collections.sort(lista, new ComparadorValorFatura());
        return lista;
    }
    /**
     * Devolve a lista de facturas por contribuintes num determinado intervalo de tempo 
     * (feito de forma a puder ser imprimido simplesmente atraves de um ciclo).
     */
    public ArrayList<Fatura> facturasContribuinteData(LocalDate in, LocalDate fin) throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Coletivo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        ArrayList<Fatura> lista = new ArrayList<Fatura>();
        for (Fatura f : this.faturas.values()){
            if(f.getData().isAfter(in) && f.getData().isBefore(fin)){
                if(f.getNome().equals(this.logedIn.getNome())) lista.add(f.clone());
            }
        }
        Collections.sort(lista, new ComparadorNifContribuinteFact());
        return lista;
    }
    /**
     * Devolve a lista de facturas por contribuintes por valor decrescente de despesa.
     */
    public Map<Integer,ArrayList<Fatura>> facturasContribuinteValor() throws SemAutorizacaoException{
        if(this.logedIn instanceof Coletivo){
            ArrayList<Fatura> lista =  new ArrayList<Fatura>();
            Map<Integer,ArrayList<Fatura>> map = new HashMap<Integer, ArrayList<Fatura>>();
            for (Fatura f : this.faturas.values()){
                if(f.getNome().equals(this.logedIn.getNome())){
                    if(map.containsKey(f.getNifCli())){
                        lista = map.get(f.getNifCli());
                        lista.add(f.clone());
                        map.replace(f.getNifCli(),lista);
                    }
                }
            }
        return map;
        }
        else throw new SemAutorizacaoException("Utilizador nao autorizado");
    }
}
