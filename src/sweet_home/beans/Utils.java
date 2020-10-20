package sweet_home.beans;

import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import sweet_home.Imovel;
import sweet_home.Telefone;
import sweet_home.Usuario;
import sweet_home.servico.ImovelServico;
import sweet_home.servico.TelefoneServico;
import sweet_home.servico.UsuarioServico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import org.apache.commons.io.IOUtils;


@ManagedBean
@RequestScoped
public class Utils implements Serializable {

private static final long serialVersionUID = 1L;
	
	@EJB
    private ImovelServico imovelServico; 
	@EJB
    private TelefoneServico telefoneServico; 
	@EJB
    private UsuarioServico usuarioServico; 
	
    private static List<Imovel> listaImoveis = null;
    private static List<Telefone> listaTelefones = null;
    private static Usuario usuario = null;
            
    
    @PostConstruct
    public void init() {    	            
    	
       	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	usuario = (Usuario) sessao.getAttribute("logado");
    }
        
   
    
    public List<Telefone> telefones(Usuario usuario) {
    	
    	return telefoneServico.recuperarPorUsuario(usuario);
    }
    
            
    public List<Imovel> meusimoveis() {
             	    	   	
    	return imovelServico.recuperarPorUsuario(usuario);   	    	
    }
     
    
    public List<Telefone> getListaTelefones() {
    	listaTelefones = telefoneServico.consultarTelefones();
        return listaTelefones;
    }
        
    public void setListaTelefones() {
    	listaTelefones = telefoneServico.consultarTelefones();
    }
    
    public List<Imovel> getListaImoveis() {
    	listaImoveis = imovelServico.recuperarImoveis();
        return listaImoveis;
    }
        
    public void setListaImoveis() {
    	listaImoveis = imovelServico.recuperarImoveis();
    }
    
       
}
