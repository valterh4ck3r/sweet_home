package sweet_home.beans;

import javax.faces.bean.RequestScoped;
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
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.bean.ManagedBean;

import org.apache.commons.io.IOUtils;


@Named("utils")
@ViewScoped
public class Utils implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Inject
    private ImovelServico imovelServico; 
	@Inject
    private TelefoneServico telefoneServico; 
	@Inject
    private UsuarioServico usuarioServico; 
	
    private static List<Imovel> listaImoveis = null;
    private static List<Telefone> listaTelefones = null;
    
    
    @PostConstruct
    public void init() {
        listaImoveis = imovelServico.recuperarImoveis();
        listaTelefones = telefoneServico.consultarTelefones();
    }
        
   
    
    public List<Telefone> telefones(String email) {
    	
    	return listaTelefones.stream().filter(x -> x.getUsuario().getEmail().equals(email))
    			.collect(Collectors.toList());
    }
    
    public List<Imovel> meusImoveis() {
    	
    	HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
    	
    	return listaImoveis.stream().filter(x -> x.getUsuario().getEmail().equals(usuarioLogado.getEmail()))
    			.collect(Collectors.toList());
    }
    
    
    public String direcionarMeusImoveis() {
    	
    	return "meusImoveis";
    }
    
    
    
    
    
    public List<Imovel> getListaImoveis() {
    	listaImoveis = imovelServico.recuperarImoveis();
        return listaImoveis;
    }
        
    public void setListaImoveis() {
    	listaImoveis = imovelServico.recuperarImoveis();
    }
    
    public List<Imovel> getListaTelefones() {
    	listaImoveis = imovelServico.recuperarImoveis();
        return listaImoveis;
    }
        
    public void setListaTelefones() {
    	listaImoveis = imovelServico.recuperarImoveis();
    }
    
    
    
    
}
