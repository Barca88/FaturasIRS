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
import java.util.List;

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
        return this.getAtividades().values().stream().map(c->c.clone()).collect(Collectors.toMap(c->c.getId(),c->c));
    }
    public Contribuinte getLogedIn() {
        return this.logedIn.clone();
    }
    public String getNomeAtividade(int i) throws SemAutorizacaoException {
        if(!this.getAtividades().containsKey(i)) throw new SemAutorizacaoException("Atividade Inexistente");
        return this.getAtividades().get(i).getNome();
    }

    //Setters
    public void setUsers(HashMap<Integer, Contribuinte> u) {
        this.users = u.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public void setFaturas(HashMap<Long, Fatura> fat) {
        this.faturas = fat.entrySet().stream().collect(Collectors.toMap(c->c.getKey(),c->c.getValue().clone()));
    }
    public void setLogedIn(Contribuinte c) {
        if(c == null) this.logedIn = null;
        else this.logedIn = c.clone();
    }
    //ToString
    public String toString(){
        StringBuilder st= new StringBuilder();
        st.append("Users:").append(this.users).append("\n");
        st.append("Faturas:").append(this.faturas).append("\n");
        st.append("Historico:").append(this.hist).append("\n");
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
     * Cirar atividades
     */
    public void criaAtividade(String nome, double deducao, int max){
        if(this.getLogedIn() instanceof Admin){
            this.getAtividades().size();
            Atividade a = new Atividade(this.getAtividades().size()+1,nome,deducao,max);
            this.atividades.put(a.getId(),a.clone());
        }
    }
    /**
     * Regista Contribuinte na aplicação
     */
    public void registarContribuinte(Contribuinte c) throws ContribuinteExistenteException{
        if(this.getUsers().containsKey(c.getNif())){
            throw new ContribuinteExistenteException ("Já existe este Contribuinte");
        }else ((Map)this.users).put(c.getNif(),c.clone());
    }

    /**
     * Inicia sessao com nif e password.
     */
    public void iniciaSessao(int nif,String password) throws SemAutorizacaoException {
        if(this.logedIn == null){
            if(users.containsKey(nif)){
                Contribuinte user = this.getUsers().get(nif);
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
    public void novaFactura(int nif_cliente, String descricao, double valorFact, double taxaImposto)
    throws SemAutorizacaoException{
        long idFatura;
        if(!(this.logedIn instanceof Coletivo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        if(this.faturas.equals(null)){ idFatura = 1;}
        else{
            idFatura = maxFatura(this.faturas.keySet()) + 1;
            Coletivo c = (Coletivo) this.getLogedIn();
            Contribuinte co = users.get(nif_cliente);
            Fatura f = new Fatura(idFatura,this.logedIn.getNif(),nif_cliente,c.getNome(),descricao,valorFact,c.getAtividades(),taxaImposto);
            this.faturas.put(idFatura,f.clone());
            co.setGastos(co.getGastos() + f.getValorPagar());
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
        for (Fatura f : this.getFaturas().values()){
            if (this.getLogedIn().getNif() == f.getNifCli())
                despesas.add(f.clone());
        }
        return despesas;
    }
    /**
     * Devolve o montante de deduçao fiscal do agregado familiar do individuo.
     * ==============================POR FAZER=============================== --TODO
     * Map<IdAtividade,total de deduçao daquele tipo de atividade> isto porque as atividades tem limite de deduçao
     * Falta talvez por uma variavel na atividade com o maximo dedutivel(sim isto existe)
     */
    public Map<Integer,Double> deducaoFiscalFamilia () throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Individuo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        int nif = this.getLogedIn().getNif();
        Individuo u = (Individuo)this.getUsers().get(nif);
        List<Integer> lista = u.getlContAgre();
        lista.add(logedIn.getNif());
        ArrayList<Fatura> list = this.getFaturas().values().stream()
                                                      .filter(f->lista.contains(f.getNifCli()))
                                                      .filter(f->f.dedutivel())
                                                      .map(c->c.clone())
                                                      .collect(Collectors.toCollection(ArrayList::new));
        double aux;
        Map<Integer,Double> map = new HashMap<Integer,Double>();
        for(Fatura f:list){
            int x = f.getListaAtividades().get(0);
            if(!map.containsKey(x)){
                map.put(x,deducaoFatura(f));
            } else {
                aux = map.get(x);
                aux += deducaoFatura(f);
                map.replace(x,aux);
            }
        }

        return map;
    }
    public double deducaoFatura(Fatura f){
        if(!((Individuo)this.users.get(f.getNifCli())).getDescontos().contains(f.getListaAtividades().get(0)) && !(f.dedutivel())) return 0;
        Atividade a = this.getAtividades().get(f.getListaAtividades().get(0));

        return a.getDeducao()*f.getValorPagar();
    }
    
    //public 
    /**
     * Associa uma classificaço de atividade economica a uma fatura
     * 1º preguntar qual e a fatura a editar
     * 2º buscar as possiveis actividades
     * 3º corrijir a classificaçao com o id da fatura e o id da atividade
     *  --TODO
     */
    public List<Fatura> porClassificar(int nif) throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Coletivo))throw new SemAutorizacaoException("Utilizador nao autorizado");
        return this.getFaturas().values().stream()
                                         .filter(f->f.getNifCli() == nif)
                                         .filter(f->f.getListaAtividades().size() != 1)
                                         .map(c->c.clone())
                                         .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Atividade> getActPossiveis(long idFatura){
        Fatura f = this.getFaturas().get(idFatura);
        Coletivo c = (Coletivo)this.getUsers().get(f.getNifEmi());
        ArrayList<Atividade> lista = new ArrayList<Atividade>();
        for(Integer a : c.getAtividades()){
            lista.add(this.getAtividades().get(a));
        }
        return lista;
    }
    public void corrigeClassificaFatura(long idFatura, int atividade) throws SemAutorizacaoException{
        Fatura f = this.getFaturas().get(idFatura);
        if(((Coletivo)this.getUsers().get(f.getNifEmi())).getAtividades().contains(atividade) &&
                this.getAtividades().containsKey(atividade)){
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
        for (Fatura f : this.getFaturas().values()){
            if (f.getNome().equals(nomeEmpresa) && f.getNifCli() == this.getLogedIn().getNif())
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
        for (Fatura f : this.getFaturas().values()){
            if (f.getNome().equals(nomeEmpresa) && f.getNifCli() == this.getLogedIn().getNif())
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
        for (Fatura f : this.getFaturas().values()){
            if(f.getData().isAfter(in) && f.getData().isBefore(fin)){
                if(f.getNome().equals(this.getLogedIn().getNome())) lista.add(f.clone());
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
            for (Fatura f : this.getFaturas().values()){
                if(f.getNome().equals(this.getLogedIn().getNome())){
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
    
    public double totalFaturadoColetivo(int nif, LocalDate in, LocalDate fim) throws SemAutorizacaoException{
        double cont = 0;
        List<Long> fat = new ArrayList<Long>();
        Coletivo co = null;
        if(users.get(nif) instanceof Coletivo){
            co = (Coletivo) this.getUsers().get(nif);
            fat = co.getFaturas();
            for(Long id : fat){
                if(this.getFaturas().get(id).getData().isAfter(in) && this.getFaturas().get(id).getData().isBefore(fim))
                    cont += this.getFaturas().get(id).getValorPagar();
            }
            return cont;
        }
        else throw new SemAutorizacaoException("O nif pretendido nao corresponde a nenhuma empresa");
    }
    
    public ArrayList<Contribuinte> top10Cont() throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Admin))throw new SemAutorizacaoException("Utilizador nao autorizado");
        return this.getUsers().values().stream()
                                       .sorted(new ComparadorGastos())
                                       .limit(10)
                                       .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public ArrayList<Contribuinte> topXEmpresas(int x) throws SemAutorizacaoException{
        if(!(this.logedIn instanceof Admin))throw new SemAutorizacaoException("Utilizador nao autorizado");
        return this.getUsers().values().stream()
                                       .filter(c->c instanceof Coletivo)
                                       .sorted(new ComparadorNumeroFaturas())
                                       .limit(x)
                                       .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public double deducaoEmpresa(int nif){
        return this.getFaturas().values().stream()
                                         .filter(f->f.getNifEmi() == nif)
                                         .mapToDouble(f->deducaoFatura(f))
                                         .sum();
    }
    
    /**
    * Devolve um int correspondente ao tipo de utilizador do "logedIn".
    */
    public int getTipoUtilizador(){
        if (this.logedIn instanceof Individuo)
            return 1;
        if (this.logedIn instanceof Coletivo)
            return 2;
        if (this.logedIn instanceof Admin)
            return 3;
    return 0;
    }
}
