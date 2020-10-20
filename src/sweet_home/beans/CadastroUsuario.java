package sweet_home.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import sweet_home.Imovel;
import sweet_home.Telefone;
import sweet_home.Usuario;
import sweet_home.servico.TelefoneServico;
import sweet_home.servico.UsuarioServico;


@ManagedBean
@javax.faces.bean.SessionScoped
public class CadastroUsuario implements Serializable {
        
    @EJB
    private UsuarioServico usuarioServico; 
    @EJB
    private TelefoneServico telefoneServico; 
    private static String email = null;
    private static String senha = null;
    private static String nome = null;
    private static String sobrenome = null;      
    private static String habilitado = "1";
    private List<Telefone> fonesRemovidos = null;
    private List<Telefone> fonesInseridos = null;
    private List<Telefone> telefones = null;
    private String fone = null;
    private List<Imovel> imoveis = null;
    private Usuario usuario = null;
    private Usuario usuarioEditado = null;
    private UIComponent mybutton;
    private UIComponent campoInvalido;
    private HttpSession sessao;
   
        
    @PostConstruct
    public void init() {    	            
    	
       	sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	usuario = (Usuario) sessao.getAttribute("logado");
    }
    
    public static boolean validarEmail(String email) {
				
	boolean isValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isValid = true;
            }
        }
        return isValid;
    }
    
    
    public String cadastrar() {
                
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(!validarEmail(email)) {
            context.addMessage(campoInvalido.getClientId(context), 
                    new FacesMessage("","Informe um email válido!"));
            return "invalido";
        }
                
        Usuario user = usuarioServico.consultarPorEmail(email);
                                     
        if(user != null) {
            context.addMessage(campoInvalido.getClientId(context), 
                    new FacesMessage("","O email informado já existe!"));
            return "invalido";            
        }   
        
        else {
        	
        	Usuario usuario = new Usuario();
        	usuario.setEmail(email);
        	usuario.setSenha(senha);
        	usuario.setPrimeiroNome(nome);
        	usuario.setUltimoNome(sobrenome);
        	usuario.setHabilitado(true);        	
        	usuarioServico.persistir(usuario);
        	
        	resetarVariaveis();
            
            return "encontrado";            
        } 
    }
    
    
    public String editar() {
    	
    	if(!validarEmail(email)) {
            
    		PrimeFaces.current().executeScript("alert('Email inválido!')");
    		return "invalido";
        }
    	
    	usuarioEditado.setEmail(email);
    	usuarioEditado.setPrimeiroNome(nome);
    	usuarioEditado.setUltimoNome(sobrenome);
    	usuarioEditado.setSenha(senha);
    	usuarioEditado.setHabilitado(habilitado.equals("1"));
    	
    	fonesRemovidos.forEach(x -> {
    		telefoneServico.remover(x);
    	});
    	
    	fonesInseridos.forEach(x -> {
    		telefoneServico.persistir(x);
    	});
    	
    	usuarioEditado.setTelefones(telefones);
    	
    	usuarioServico.atualizar(usuarioEditado);
            	   	
    	resetarVariaveis();
    	
        return "encontrado";    
    }
    
    public void dadosUsuario(Usuario u) {
    	
    	resetarVariaveis();
    	this.usuarioEditado = u;
    	this.email = u.getEmail();
    	this.nome= u.getPrimeiroNome();
    	this.sobrenome = u.getUltimoNome();
    	this.senha = u.getSenha();
    	this.habilitado = u.isHabilitado() ? "1" : "0";    	    	
    	List<Telefone> list = telefoneServico.recuperarPorIdUsuario(u.getId());
    	this.telefones = new ArrayList<>();
    	this.fonesRemovidos = new ArrayList<>();
    	this.fonesInseridos = new ArrayList<>();
    	if(list != null) {
    		list.forEach(x -> {
    			this.telefones.add(x);
    		});
    	}    	
    }
    
    public void dadosPessoais() {
    	
    	sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	usuario = (Usuario) sessao.getAttribute("logado");
    	dadosUsuario(usuario);
    }
    
    public void resetarVariaveis() {
    	
    	this.usuarioEditado = null;
    	this.email = null;
    	this.nome= null;
    	this.sobrenome = null;
    	this.senha = null;
    	this.habilitado = null;
    	this.telefones = null;
    	this.fonesInseridos = null;
    	this.fonesRemovidos = null;
    	this.fone = null;
    	this.imoveis = null;
    }
          
   
    public List<Usuario> usuarios() {
    	    	    	
    	return usuarioServico.usuariosOrdenadosPorEmail();
    }
    
    public void excluir(String s) {              
    	
        Usuario u = usuarioServico.consultarPorId(new Long(s));
        usuarioServico.remover(u);      
    }
    
    public void excluirTelefone(String s) {                  	
    	    	
    	Long l = new Long(s);    	
    	Collection<Telefone> col = new ArrayList<>(this.telefones);
    	for(int i = 0; i < col.size(); i++) {    		
    		if(this.telefones.get(i).getId().equals(l)) {    			     			
    			Telefone t = null;
    			if(i < this.telefones.size()) t = this.telefones.remove(i);
    			if(t != null) {
    				if(!fonesRemovidos.contains(t))
    				fonesRemovidos.add(t);
    			}
    		}
    	}
    }
       
    public void inserirFone() {
    	
    	String tel = fone.replace("(", "").replace(")", "").replace("-", "");
    	if(tel.length() < 10) PrimeFaces.current().executeScript("alert('O formato do telefone é inválido!')");
    	else {    		
    		
    		fonesInseridos.add(new Telefone(null, usuarioEditado, tel.substring(2), tel.substring(0,2)));
    		PrimeFaces.current().executeScript("alert('O telefone foi adicionado!')");
    		fone = "";
    	}
    }
    
        
        
    public String getFone() {
    	return fone;
    }
    
    public void setFone(String fone) {
    	this.fone = fone;
    }
    
    public List<Telefone> getFonesRemovidos() {
    	return fonesRemovidos;
    }
    
    public void setFonesRemovidos(List<Telefone> fonesRemovidos) {
    	this.fonesRemovidos = fonesRemovidos;
    }
    
    public List<Telefone> getFonesInseridos() {
    	return fonesInseridos;
    }
    
    public void setFonesInseridos(List<Telefone> fonesInseridos) {
    	this.fonesInseridos = fonesInseridos;
    }
    
    public Usuario getUsuario() {
    	return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
    	this.usuario = usuario;
    }
    
    public Usuario getUsuarioEditado() {
    	return usuarioEditado;
    }
    
    public void setUsuarioEditado(Usuario usuarioEditado) {
    	this.usuarioEditado = usuarioEditado;
    }
    
    public List<Telefone> getTelefones() {
    	    	
    	return telefones;
    }
    
    public void setTelefones(List<Telefone> telefones) {
    	this.telefones = telefones;
    }
          
    public String getEmail() {
        return email;
    }    
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public String getsobrenome() {
        return sobrenome;
    }
    
    public String getHabilitado() {
    	return habilitado;
    }

    public void setHabilitado(String habilitado) {    	
    	this.habilitado = habilitado;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }
    
    public void setCampoInvalido(UIComponent campoInvalido) {
        this.campoInvalido = campoInvalido;
    }

    public UIComponent getCampoInvalido() {
        return campoInvalido;
    }
    
}
