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
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.primefaces.event.SelectEvent;

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
		
	private boolean filtrar;
    private int banheiros = 0;    
    private int quartos = 0;
    private int salas = 0;
    private static Usuario usuario = null;
    private int tipo = 2;
    private String descricao = null;
    private double valor = 0.0;
    private UIComponent mybutton;
    protected static String resp = "";
        
    private String cidade = null;
    private String bairro = null;
    private String rua = null;
    private String numero = null;    
    private String CEP = null;
    private String estado = null;
    private static List<Imovel> lista = null;
    private static Endereco selected = null; 
    
    private boolean filtrarGaragem = false;
    private boolean filtrarPiscina = false;
    private boolean filtrarBeiraMar = false;
    private boolean filtrarSalaReuniao = false;
    private String filtrarQuantidadeQuartos = "0";
    private String filtrarMetrosQuadradosMinimo = "0";
    private String filtrarMetrosQuadradosMaximo = "0";

    private UIComponent metrosMessage;   
    private String metrosMessageError = ""; 
        
    private Part imageFile; 
    
    
    public String cadastrar() {
                
        lista = imovelServico.recuperarImoveis();
                        
        Imovel imovel = new Imovel();
        
        Endereco endereco = new Endereco();
        endereco.setNumero(numero);        
        endereco.setRua(rua);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);      
        endereco.setEstado(estado);
        endereco.setCEP(CEP);
                
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
                
        imovel.setQuartos(quartos);
        imovel.setBanheiros(banheiros);
        imovel.setSalas(salas);
        imovel.setTipo(tipo);
        imovel.setDescricao(descricao);
        imovel.setValor(valor);
        imovel.setEndereco(endereco);
        imovel.setUsuario(usuarioLogado);
        
        imovelServico.persistir(imovel);    
        resp = "Imï¿½vel cadastrado com sucesso!";
        
        return "cadastro";
    }
    
	public List<Imovel> carregarImoveis() {
		
		List<Imovel> imoveis = new ArrayList<>();
		
		if(filtrarQuantidadeQuartos == "") {
			filtrarQuantidadeQuartos = "0";
		}
		
		if(filtrarMetrosQuadradosMinimo == "") {
			filtrarMetrosQuadradosMinimo = "0";
		}
		
		if(filtrarMetrosQuadradosMaximo == "") {
			filtrarMetrosQuadradosMaximo = "0";
		}
		
		if((Integer.parseInt(filtrarMetrosQuadradosMinimo) > Integer.parseInt(filtrarMetrosQuadradosMaximo))) {	 
            metrosMessageError = "Filtro de metros quadrados estão incorretos";
            return imoveis;
		} else {
			metrosMessageError = "";
		}
				
		if(!filtrar) return imovelServico.recuperarImoveis();
	
		
		else {
			filtrar = false;
			return imovelServico.recuperarImoveisComFiltro(filtrarGaragem, filtrarPiscina, filtrarBeiraMar, filtrarSalaReuniao , Integer.parseInt(filtrarQuantidadeQuartos) ,  Integer.parseInt(filtrarMetrosQuadradosMinimo) , Integer.parseInt(filtrarMetrosQuadradosMaximo));        
		}
		
			
//		lista = imoveis;		
//		setLista(imoveis);
//				
//        return imoveis;    
	}
      
     
    public void excluir(Imovel imovel) {               
       imovelServico.remover(imovel);      
    }

    
    public String salvarImagem() {
    	
    	try {
	    	InputStream is = imageFile.getInputStream();
	    	byte[] bytes = IOUtils.toByteArray(is);
	    	
	    	return "";
		    	
    	}catch(IOException e) {}
    	
    	return "";
    }
    
    public Part getImageFile() {
    	return imageFile;
    }
    
    public void setImageFile(Part imageFile) {
    	this.imageFile = imageFile;
    }
       
    
    
    public List<Imovel> meusImoveis() {
    	
    	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
    	return imovelServico.recuperarPorUsuario(usuarioLogado);
    }
      
    public boolean getFiltrar() {
    	return filtrar;
    }
    
    public void setFiltrar(boolean filtrar) {
    	this.filtrar = filtrar;
    			
		if(filtrarQuantidadeQuartos == "") {
			filtrarQuantidadeQuartos = "0";
		}
		
		if(filtrarMetrosQuadradosMinimo == "") {
			filtrarMetrosQuadradosMinimo = "0";
		}
		
		if(filtrarMetrosQuadradosMaximo == "") {
			filtrarMetrosQuadradosMaximo = "0";
		}
		
		if((Integer.parseInt(filtrarMetrosQuadradosMinimo) > Integer.parseInt(filtrarMetrosQuadradosMaximo))) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(metrosMessage.getClientId(context), 
                    new FacesMessage("", "Filtro de metros quadrados estão incorretos"));	    
            metrosMessageError = "Filtro de metros quadrados estão incorretos";
		} else {
            metrosMessageError = "";
		}
    }
    
    public Endereco getSelected(){
        return selected;
    }
    
    public void setSelected(Endereco selected) {
        this.selected = selected;
    }
    
    public int getBanheiros(){
        return banheiros;
    }
    
    public void setBanheiros(int banheiros) {
        this.banheiros = banheiros;
    }
    
    public int getQuartos(){
        return quartos;
    }
    
    public void setQuartos(int quartos) {
        this.quartos = quartos;
    }

    public int getSalas(){
        return salas;
    }
    
    public void setSalas(int salas) {
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
        
    public void setLista() {
        lista = imovelServico.recuperarImoveis();
    }
    

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
          
    public int getTipo() {        
        return tipo;
    }
        
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }    
    
    public UIComponent getMybutton() {
        return mybutton;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }
    
    public String getResp() {
        return resp;
    }
    
    public void setResp(String resp) {
        this.resp = resp;
    }

	public boolean isFiltrarGaragem() {
		return filtrarGaragem;
	}

	public void setFiltrarGaragem(boolean filtrarGaragem) {
		this.filtrarGaragem = filtrarGaragem;
	}

	public boolean isFiltrarPiscina() {
		return filtrarPiscina;
	}

	public void setFiltrarPiscina(boolean filtrarPiscina) {
		this.filtrarPiscina = filtrarPiscina;
	}

	public boolean isFiltrarBeiraMar() {
		return filtrarBeiraMar;
	}

	public void setFiltrarBeiraMar(boolean filtrarBeiraMar) {
		this.filtrarBeiraMar = filtrarBeiraMar;
	}

	public boolean isFiltrarSalaReuniao() {
		return filtrarSalaReuniao;
	}

	public void setFiltrarSalaReuniao(boolean filtrarSalaReuniao) {
		this.filtrarSalaReuniao = filtrarSalaReuniao;
	}

	
	public String getFiltrarQuantidadeQuartos() {
		return filtrarQuantidadeQuartos;
	}

	public void setFiltrarQuantidadeQuartos(String filtrarQuantidadeQuartos) {
		this.filtrarQuantidadeQuartos = filtrarQuantidadeQuartos;
	}
	

	public String getFiltrarMetrosQuadradosMinimo() {
		return filtrarMetrosQuadradosMinimo;
	}

	public void setFiltrarMetrosQuadradosMinimo(String filtrarMetrosQuadradosMinimo) {
		this.filtrarMetrosQuadradosMinimo = filtrarMetrosQuadradosMinimo;
	}

	public String getFiltrarMetrosQuadradosMaximo() {
		return filtrarMetrosQuadradosMaximo;
	}

	public void setFiltrarMetrosQuadradosMaximo(String filtrarMetrosQuadradosMaximo) {
		this.filtrarMetrosQuadradosMaximo = filtrarMetrosQuadradosMaximo;
	}

	public static void setLista(List<Imovel> lista) {
		CadastroImovel.lista = lista;
	}

	public UIComponent getMetrosMessage() {
		return metrosMessage;
	}

	public void setMetrosMessage(UIComponent metrosMessage) {
		this.metrosMessage = metrosMessage;
	}

	public String getMetrosMessageError() {
		return metrosMessageError;
	}

	public void setMetrosMessageError(String metrosMessageError) {
		this.metrosMessageError = metrosMessageError;
	}

    
}
