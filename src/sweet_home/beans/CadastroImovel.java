package sweet_home.beans;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import sweet_home.Endereco;
import sweet_home.Imovel;
import sweet_home.Tipo;
import sweet_home.Usuario;
import sweet_home.servico.EnderecoServico;
import sweet_home.servico.ImovelServico;
import sweet_home.servico.UsuarioServico;

import org.apache.commons.io.IOUtils;



@ManagedBean
@javax.faces.bean.SessionScoped
public class CadastroImovel implements Serializable {
      
	private static final long serialVersionUID = 1L;
	
	@EJB
    private ImovelServico imovelServico; 
	@EJB
    private UsuarioServico usuarioServico; 
		
    private String banheiros;    
    private String quartos;
    private String salas;
    private static Usuario usuario = null;
    private String tipo = "1";
    private String descricao = null;
    private String valor;
    private String piscina;
    private String garagem;
    private String salaReuniao;
    private String beiraMar;
    private int filtrar = 0;
    private UIComponent mybutton;
    protected static String resp = "";
        
    private String cidade = null;
    private String bairro = null;
    private String rua = null;
    private String numero = null;    
    private String CEP = null;
    private String estado = null;
    private static List<Imovel> lista = null;
    private String operacao;
    private boolean editar;
    private Imovel imovel;   
    private List<byte[]> imagens = null;
        
    
    
    public String cadastrar() {
               
    	FacesContext context = FacesContext.getCurrentInstance();
        if(rua == null || rua.length() == 0 || numero == null || numero.length() == 0 || 
        	bairro == null || bairro.length() == 0 || cidade == null || cidade.length() == 0 ||
        	estado == null || estado.length() == 0  || CEP == null || CEP.length() < 9) {
        	
        	if(CEP.length() < 9)
        	PrimeFaces.current().executeScript("alert('Preencha o CEP completo!')");
        	else 
        		PrimeFaces.current().executeScript("alert('Preencha todos os dados do endereço!')");

	                                  return "editar";
        }
    	
    	
		Endereco endereco = new Endereco(null, rua, numero, bairro, cidade, estado, CEP, null);
                                
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
        
        if(imovel == null) {
        	imovel = new Imovel();
        	imovel.setUsuario(usuarioLogado);
        }        
        
        imovel.setQuartos(quartos == null || quartos.length() == 0 ? 0 : Integer.parseInt(quartos));
        imovel.setBanheiros(banheiros == null || banheiros.length() == 0 ? 0 : Integer.parseInt(banheiros));
        imovel.setSalas(salas == null || salas.length() == 0 ? 0 : Integer.parseInt(salas));
        imovel.setTipo(Integer.parseInt(tipo));
        imovel.setDescricao(descricao == null ? "" : descricao);
        imovel.setValor(valor == null || valor.length() == 0 ? 0 : Double.parseDouble(valor));      
        imovel.setPiscina(piscina.equals("1"));
        imovel.setBeiraMar(beiraMar.equals("1"));
        imovel.setGaragem(garagem.equals("1"));
        imovel.setSalaReuniao(salaReuniao.equals("1"));
        imovel.setImagens(imagens);
        imovel.setEndereco(endereco);
        
        if(imovel.getId() == null) imovelServico.persistir(imovel);    
        else imovelServico.atualizar(imovel);
                
        resp = "Imóvel cadastrado com sucesso!";        
        
        return "sucesso";
    }
              
     
    public void excluir(String s) {              
    	
       Imovel i = imovelServico.consultarPorId(new Long(s));    	
       imovelServico.remover(i);      
    }

    
    public void salvarImagem(FileUploadEvent event) {
    	    		
    	try {
	    	InputStream is = event.getFile().getInputstream();
	    	if(imagens == null) imagens = new ArrayList<>();
	    	
	    	imagens.add(IOUtils.toByteArray(is));	    	    		    	
	    	
    	}catch(IOException e) {e.printStackTrace();}    	
    }
          

    public List<byte[]> getImagens() {
    	return imagens;
    }
    
    public void setImagens(List<byte[]> imagens) {
    	this.imagens = imagens;
    }

    public Imovel getImovel() {
    	return imovel;
    }
    
    public void setImovel(Imovel imovel) {
    	this.imovel = imovel;
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
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getRua() {
        return rua;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getCEP() {
        return CEP;
    }
    
    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<Imovel> getLista() {
    	
        lista = imovelServico.recuperarImoveis();                
                
        return lista;
    }
    
    public List<Imovel> meusImoveis() {
    	
    	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
    	
    	return imovelServico.recuperarPorUsuario(usuarioLogado);
    }
        
    
    
    public void dadosImovel(Imovel imovel) {
    	
    	this.operacao = "Editar imóvel";
    	this.editar = true;
    	this.imovel = imovel;
    	this.imagens = imovel.getImagens();
    	this.tipo = Integer.toString(imovel.getTipo());
    	this.descricao = imovel.getDescricao();
    	this.banheiros = Integer.toString(imovel.getBanheiros());
    	this.quartos = Integer.toString(imovel.getQuartos());
    	this.salas = Integer.toString(imovel.getSalas());
    	this.valor = Double.toString(imovel.getValor());
    	this.piscina = imovel.getPiscina() ? "1" : "0";
    	this.beiraMar = imovel.getBeiraMar() ? "1" : "0";
    	this.garagem = imovel.getGaragem() ? "1" : "0";
    	this.salaReuniao = imovel.getSalaReuniao() ? "1" : "0";
    	this.bairro = imovel.getEndereco().getBairro();
    	this.cidade = imovel.getEndereco().getCidade();
    	this.estado = imovel.getEndereco().getEstado();
    	this.numero = imovel.getEndereco().getNumero();
    	this.CEP = imovel.getEndereco().getCEP();
    	this.rua = imovel.getEndereco().getRua();
    }
    
    public void cadastro() {
    	this.operacao = "Cadastrar imóvel";
    	this.editar = false;
    
    	imagens = new ArrayList<>();
    	imovel = null;
        banheiros = null;    
        quartos = null;
        salas = null;
        descricao = null;
        piscina = null;
        garagem = null;
        salaReuniao = null;
        beiraMar = null;
        valor = null;
        cidade = null;
        bairro = null;
        rua = null;
        numero = null;    
        CEP = null;
        estado = null;        
    }
    
    public void excluirImagem(byte[] img) {
    	
    	imagens.remove(img);
    }
    
    
    public boolean getEditar() {
    	return editar;
    }
    
    public void setEditar(boolean editar) {
    	this.editar = editar;
    }
    
    public String getOperacao() {
    	return operacao;
    }
    
    public void setOperacao(String operacao) {
    	this.operacao = operacao;
    }
    
    public void setLista() {
        lista = imovelServico.recuperarImoveis();
    }
    

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
          
    public String getTipo() {        
        return tipo;
    }
        
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
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
    
    public String getResp() {
        return resp;
    }
    
    public void setResp(String resp) {
        this.resp = resp;
    }
    
}
