package sweet_home.beans;

import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces.Ajax;

import sweet_home.Endereco;
import sweet_home.Imovel;
import sweet_home.Telefone;
import sweet_home.Usuario;
import sweet_home.servico.ImovelServico;
import sweet_home.servico.TelefoneServico;
import sweet_home.servico.UsuarioServico;


@ManagedBean
@SessionScoped
public class Filtro implements Serializable {

private static final long serialVersionUID = 1L;
	
	@EJB
    private ImovelServico imovelServico; 
	@EJB
    private TelefoneServico telefoneServico; 
	@EJB
    private UsuarioServico usuarioServico; 
	
    
    private static Usuario usuario = null;
    private static String banheiros = "";    
    private static String quartos = "";
    private static String salas = "";    
    private static String tipo = null;    
    private static String valor = "0";
    private static String piscina = "0";
    private static String garagem = "0";
    private static String salaReuniao = "0";
    private static String beiraMar = "0";
    private int filtrar = 0;    
            
    private static String cidade = "";    
    private static String estado = "";    
    private static UIComponent mybutton;
    private String cidadejson = "";
    private HtmlInputHidden inputValue;
    
    
    @PostConstruct
    public void init() {    	            
    	    	
    	inputValue = new HtmlInputHidden();
       	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	usuario = (Usuario) sessao.getAttribute("logado");
    }
        
       
    public List<Telefone> telefones() {
    	
        return telefoneServico.consultarTelefones();
    }
    
    public List<Telefone> meustelefones(Usuario usuario) {
    	
    	return telefoneServico.recuperarPorUsuario(usuario);
    }

    
    public List<Imovel> meusimoveis() {
             	    	   	
    	return imovelServico.recuperarPorUsuario(usuario);   	    	
    }
     
    
    public List<Imovel> imoveis() {   
    	
    	cidade = cidade == null ? "" : cidade;
    	estado = estado == null ? "" : estado;
    	
    	if(tipo != null) {    		
    		
    		double menor = valor.equals("1") ? 0 : valor.equals("2") ? 500 : valor.equals("3") ? 1000 : valor.equals("4") ?
    				1500 : valor.equals("5") ? 2500 : valor.equals("6") ? 4000 : 5000;
    		double maior = valor.equals("1") ? 500 : valor.equals("2") ? 1000 : valor.equals("3") ? 1500 : valor.equals("4") ?
    				2500 : valor.equals("5") ? 4000 : valor.equals("6") ? 5000 : 1000000;
    		
    		List<Imovel> list = imovelServico.filtrar(tipo, quartos, banheiros, salas, piscina, garagem, salaReuniao, 
    				beiraMar, menor, maior, estado, cidade);
    		
    		tipo = null;    		
    		estado = "";
    		cidade = "";
    		cidadejson = "";
    		
    		return list;    		
    	}
    	    	    	    	
    	return imovelServico.recuperarImoveis();    	
    }
    
    
    public String getCidadejson() {
       	
    	return cidadejson;
    }
    
    public void setCidadejson(String cidadejson) {
    	
    	this.cidadejson = cidadejson;
    }
     
    
	public String[] cidades() {        
		
		if(cidadejson.length() == 0) return new String[0];
		
        return cidadejson.split(",");
    }
	   
    
    public String getTipo() {        
        return tipo;
    }
        
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public UIComponent getMybutton() {
        return mybutton;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }
    
    public int getFiltrar() {
    	return filtrar;
    }
    
    public void setFiltrar(int filtrar) {
    	this.filtrar = filtrar;
    }   
    
    public String getBanheiros(){
        return banheiros;
    }
    
    public void setBanheiros(String banheiros) {
        this.banheiros = banheiros;
    }
    
    public String getQuartos(){
        return quartos;
    }
    
    public void setQuartos(String quartos) {
        this.quartos = quartos;
    }

    public String getSalas(){
        return salas;
    }
    
    public void setSalas(String salas) {
        this.salas = salas;
    }
   
    public String getPiscina() {
    	return piscina;
    }
    
    public void setPiscina(String piscina) {
    	this.piscina = piscina;
    }
    
    public String getGaragem() {
    	return garagem;
    }
    
    public void setGaragem(String garagem) {
    	this.garagem = garagem;
    }
    
    public String getSalaReuniao() {
    	return salaReuniao;
    }
    
    public void setSalaReuniao(String salaReuniao) {
    	this.salaReuniao = salaReuniao;
    }
    
    public String getBeiraMar() {
    	return beiraMar;
    }
    
    public void setBeiraMar(String beiraMar) {
    	this.beiraMar = beiraMar;
    }
    
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }  
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
       
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    private class Cidade {
    	
    	public int id;
    	public String nome;
    	public String microrregiao;
    	
    	public Cidade() {
    		
    	}
    	
    	public int getId() {
    		return id;
    	}
    	
    	public void setId(int id) {
    		this.id = id;
    	}
    	
    	public String getNome() {
    		return nome;
    	}
    	
    	public void setNome(String nome) {
    		this.nome = nome;
    	}
    	
    	public String getMicrorregiao() {
    		return microrregiao;
    	}
    	
    	public void setMicrorregiao(String microrregiao) {
    		this.microrregiao = microrregiao;
    	}
    }
    
}
