import java.io.*;
import java.util.*;

public class FaturacaoApp
{
    // instance variables - replace the example below with your own
    private FaturacaoApp () {}
    private static Faturacao fat;
    private static Menu menuprincipal, menucontribuinte, menuempresa, menuregistar;
    
    public static void main(String [] args){
        carregarDados();
        carregarMenus();
        imprimeMenuPrincipal();
        try{
            fat.gravaObj();
        }
        catch(IOException e){
            System.out.println("Não foi possivel gravar os dados");
        }
    }
    
    private static void carregarDados(){
        try{
            fat = Faturacao.leObj();
        }
        catch (IOException e){
            fat = new Faturacao();
            System.out.println("No foi possivel ler os dados \n Erro de leitura");
        }
        catch (ClassNotFoundException e){
            fat = new Faturacao();
            System.out.println("No foi possivel ler os dados \n Ficheiro com formato desconhecido");
        }
        catch(ClassCastException e){
            fat = new Faturacao();
            System.out.println("Não foi possivel ler os dados!\nErro de formato!");
        }
    }
    
    private static void carregarMenus(){
        String [] principal = {"Iniciar Sessao",
                               "Registar"
                               };
                               
        String [] contribuinte = {"Despesas emitidas em meu nome",
                                  "Deduçao acumulada por mim",
                                  "Deduçao acumulada pelo meu agregado familiar",
                                  "Corrigir classificaçao de atividade economica de uma fatura",
                                  "Lista das faturas de uma determinada empresa",
                                  "Total faturado por uma dada empresa num determinado periodo"
                                  };
        
        String [] empresa = {"Criar Fatura",
                             "Facturas por contribuinte num intervalo de datas",
                             "Facturas por contribuinte e por valor decrescente de despesa",
                             "Total faturado por uma dada empresa num determinado periodo"
                             };
        
        String [] registar = {"Registar Contribuinte",
                              "Registar Empresa"
                              };
        
        menuprincipal = new Menu(principal);
        menucontribuinte = new Menu(contribuinte);
        menuempresa = new Menu(empresa);
        menuregistar = new Menu(registar);
    }
    
    private static void imprimeMenuPrincipal(){
        do{
            menuprincipal.executa();
            switch(menuprincipal.getOpcao()){
                case 1: logIn();
                        break;
                case 2: regiUti();
                        break;
            }
        } while (menuprincipal.getOpcao()!= 0);
    }
    
    private static void imprimeMenuContribuinte(){
        menucontribuinte.executa();
        switch(menuempresa.getOpcao()){
            case 1: minhasDespesas();
                    break;
            case 2: minhaDeducao();
                    break;
            case 3: deducaoFamiliar();
                    break;
            case 4: corrigirClassFat();
                    break;
            case 5: faturasEmpresa();
                    break;
            case 6: faturadoEmpresa();
                    break;
        }
    }
    
    private static void imprimeMenuEmpresa(){
        menuempresa.executa();
        switch(menuempresa.getOpcao()){
            case 1: novaFatura();
                    break;
            case 2: faturasTempo();
                    break;
            case 3: faturasValor();
                    break;
            case 4: faturadoEmpresa();
                    break;
        }
    }
    
    private static void logIn(){
        Scanner input = new Scanner(System.in);
        int nif;
        String password;
        
        System.out.println("NIF: ");
        nif = input.nextInt();

        System.out.println("Password: ");
        password = input.nextLine();
        
        try{
            fat.iniciaSessao(nif,password);
        }
        catch (SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        finally{
            input.close();
        }
        System.out.println("Login");
        switch(fat.getTipoUtilizador()){
            case 1: imprimeMenuContribuinte(); break;
            case 2: imprimeMenuEmpresa(); break;
        }
    }
    
    private static void regiUti(){
        int nif;
        String email, nome, morada, password;
        Scanner input = new Scanner(System.in);
        Contribuinte c = null;
        menuregistar.executa();
        
        if(menuregistar.getOpcao()==0){
            System.out.println("Registo Cancelado");
            return;
        }
        
        System.out.println("Insira o seu nif: ");
        nif = input.nextInt();
        
        System.out.println("Insira o seu email: ");
        email = input.nextLine();
        
        System.out.println("Insira o seu nome: ");
        nome = input.nextLine();
        
        System.out.println("Insira a sua morada: ");
        morada = input.nextLine();
        
        System.out.println("Insira a sua password: ");
        password = input.nextLine();
        
        switch(menuregistar.getOpcao()){
            case 0: input.close(); return;
            case 1: c = new Individuo(nif); break;
            case 2: c = new Coletivo(nif); break;
        }
        
        c.setEmail(email);
        c.setNome(nome);
        c.setMorada(morada);
        c.setPwd(password);
        
        try{
            fat.registarContribuinte(c);
        }
        catch(ContribuinteExistenteException e){
            System.out.println(e.getMessage());
        }
        finally{
            input.close();
        }
    }
    
    private static void minhasDespesas(){
        try{
            ArrayList<Fatura> lista = fat.despesasEmitidas();
            for(Fatura f : lista){
                System.out.println("Id : " + f.getId() + "\n" +
                                   "Empresa : " + f.getNome() + "\n" +
                                   "Custo : " + f.getValorPagar() + "\n" +
                                   "Atividades : " + "\n");
                imprimeAtividades(f);
            }
            System.out.println("=====================================" + "\n");
        }
        catch(SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
    }
    
        private static void imprimeAtividades(Fatura f){
            for(Integer a : f.getListaAtividades()){
                System.out.println("->" + fat.getNomeAtividade(a) + ";" + "\n");
            }
        }
        
    private static void minhaDeducao(){
    }
    
    private static void deducaoFamiliar(){
    }
    
    private static void corrigirClassFat(){
    }
    
    private static void faturasEmpresa(){
        ArrayList<Fatura> lista = new ArrayList<Fatura>();
        String nomeEmpresa;
        Scanner input = new Scanner(System.in);
        String [] menu = {"Por Data",
                          "Por Valor decrescente"
                          };
        Menu menufaturasempresa = new Menu(menu);
        menufaturasempresa.executa();
        if(menufaturasempresa.getOpcao()==0){
            System.out.println("Processo Cancelado");
            return;
        }
        
        System.out.println("Insira o nome da empresa da qual pretende obter as faturas: ");
        nomeEmpresa = input.nextLine();
        switch(menuregistar.getOpcao()){
            case 0: input.close(); return;
            case 1: lista = fat.facturasEmpresaData(nomeEmpresa); break;
            case 2: lista = fat.facturasEmpresaValor(nomeEmpresa); break;
        }
        
        System.out.println("As faturas correspondentes a empresa " + nomeEmpresa + " sao:" + "\n");
        for(Fatura f : lista){
                System.out.println("Id : " + f.getId() + "\n" +
                                   "Empresa : " + f.getNome() + "\n" +
                                   "Custo : " + f.getValorPagar() + "\n" +
                                   "Atividades : " + "\n");
                imprimeAtividades(f);
        }
        
        input.close();
    }
    
    private static void faturadoEmpresa(){
        String nomeEmpresa;
    }
    
    private static void novaFatura(){
        int nif_cliente;
        double valorFact, taxaImposto;
        String descricao;
        ArrayList<Integer> atividades = new ArrayList<Integer>();
        Scanner input = new Scanner(System.in);
        
    }
}
