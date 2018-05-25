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
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.lang.Object;
import java.time.LocalDate;
import java.util.Set;

public class Faturacao implements Serializable {
    private Map<Integer,Contribuinte> users;
    private Map<Long, Fatura> faturas;
    private Historico hist;
    private Map<Integer, Atividade> atividades;
    private Contribuinte logedIn;

    public Faturacao(){
        this.users = new HashMap<Integer, Contribuinte>();
        this.faturas = new HashMap<Long, Fatura>();
        this.hist = new Historico();
        this.atividades = new HashMap<Integer, Atividade>();        
        this.logedIn = null;
    }
    public Faturacao(Map<Integer,Contribuinte> users, Map<Long,Fatura> faturas, Historico hist,
                     Map<Integer, Atividade> atividades, Contribuinte logedIn){
        this.users = users;
        this.faturas = faturas;
        this.hist = hist;
        this.atividades = atividades;
        this.logedIn = logedIn;
    }
    public Faturacao(Faturacao f){
        this.users = f.getUsers();
        this.faturas = f.getFaturas();
        this.hist = f.getHist();
        this.atividades = f.getAtividades();
        this.logedIn = f.getLogedIn();
    }

    //Getters
    public Map<Integer, Contribuinte> getUsers() {
        return this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public Map<Long, Fatura> getFaturas() {
        return this.faturas.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public Historico getHist() {
        return new Historico(this.hist);
    }
    /*
    public Map<Long, ArrayList<Fatura>> getFatRegisto() {
        return this.hist.getHist().entrySet()
                              .stream()
                              .collect(Collectors
                                .toMap(e->e.getKey(),
                                    e->e.getValue().stream().map(c->c.clone()).collect(Collectors.toCollection(ArrayList::new))));
    }*/
    
    public Map<Integer, Atividade> getAtividades(){
        return this.atividades.values().stream().map(c->c.clone()).collect(Collectors.toMap(c->c.getId(),c->c));
    }
    public Contribuinte getLogedIn() {
        return this.logedIn.clone();
    }
    public String getNomeAtividade(int i) throws SemAutorizacaoException {
        if(!this.atividades.containsKey(i)) throw new SemAutorizacaoException("Atividade Inexistente");  
        return this.atividades.get(i).getNome();
    }

    //Setters
    public void setUsers(HashMap<Long, Contribuinte> users) {
        this.users.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }
    public void setFaturas(HashMap<Long, Fatura> faturas) {
        this.faturas.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue()));
    }
    public void setLogedIn(Contribuinte logedIn) {
        this.logedIn = logedIn;
    }
    //ToString
    
    public String toString(){
        StringBuilder st= new StringBuilder();
        st.append("Users:").append(this.users).append("\n");
        st.append("Faturas:").append(this.faturas).append("\n");
        st.append("Registo de Faturas:").append(this.hist).append("\n");
        st.append("Atividades:").append(this.atividades).append("\n");
        return st.toString();
        
    }
    
    //
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
     * Regista Contribuinte na aplicação
     */
    public void registarContribuinte(Contribuinte c) throws ContribuinteExistenteException{
        if(this.users.containsKey(c.getNif())){
            throw new ContribuinteExistenteException ("Já existe este Contribuinte");
        }else ((Map)this.users).put(c.getNif(),c);
    }

    /**
     * Inicia sessao com nif e password. 
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
        long idFatura;
        if(!(this.logedIn instanceof Coletivo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        if(this.faturas.equals(null)){ idFatura = 1;}
        else{
            idFatura = maxFatura(this.faturas.keySet()) + 1;
            Fatura f = new Fatura(idFatura,this.logedIn.getNif(),nif_cliente,this.logedIn.getNome(),descricao,valorFact,atividades,taxaImposto);
            this.faturas.put(idFatura,f);
        }
    }
    private static long maxFatura(Set<Long> lista){
        long max = 0;
        for (long value : lista) {
            if (value > max) {
                max = value;
            }  
        }
        return max;
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
    public long deducaoFiscalFamilia () throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Individuo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        int nif = this.logedIn.getNif();
        Individuo u = (Individuo)this.users.get(nif);
        List<Integer> lista = u.getlContAgre();
        lista.add(logedIn.getNif());
        ArrayList<Fatura> list = this.faturas.values().stream()
                                                      .filter(f->lista.contains(f.getNifCli()))
                                                      .filter(f->f.dedutivel())
                                                      .map(c->c.clone())
                                                      .collect(Collectors.toCollection(ArrayList::new));
        
        return 1;
    }
    public double deducaoFatura(Fatura f){
        return 0;
    }
        
    /**
     * Associa uma classificaço de atividade economica a uma fatura
     * 1º preguntar qual e a fatura a editar
     * 2º buscar as possiveis actividades
     * 3º corrijir a classificaçao com o id da fatura e o id da atividade
     *  --TODO
     */
    public List<Integer> getActPossiveis(long idFatura){
        Fatura f = this.faturas.get(idFatura);
        Coletivo c = (Coletivo)this.users.get(f.getNifEmi());
        return c.getAtividades();
    }
    public void corrigeClassificaFatura(long idFatura, int atividade) throws SemAutorizacaoException{
        Fatura f = this.faturas.get(idFatura);
        if(((Coletivo)this.users.get(f.getNifEmi())).getAtividades().contains(atividade) && 
                this.atividades.containsKey(atividade)){
            this.hist.add(f);
            ArrayList<Integer> aux = new ArrayList<Integer>();
            aux.add(atividade);
            f.setListaAtividades(aux);
            this.faturas.replace(idFatura,f);
        }else throw new SemAutorizacaoException("Atividade Proibida!");                 
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
    /**
    * Devolve um int correspondente ao tipo de utilizador do "logedIn".
    */
    public int getTipoUtilizador(){
    if (this.logedIn instanceof Individuo)
      return 1;
    if (this.logedIn instanceof Coletivo)
      return 2;
    return 0;
    }
}
