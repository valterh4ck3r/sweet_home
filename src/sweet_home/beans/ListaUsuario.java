package sweet_home.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

import sweet_home.Usuario;
import sweet_home.servico.UsuarioServico;


@FacesConfig
@Any @Default
@Named
@SessionScoped
public class ListaUsuario implements Serializable {
        
    @Inject
    private UsuarioServico usuarioServico; 
    private static Usuario selected = null;
    private static List<Usuario> lista = null;
    private static Usuario usuario = null;;
    private String primeiroNome = null;
    private String ultimoNome = null;
    private String email = null;    
    private int habilitado;
    private UIComponent mybutton;
      
     
    public String excluir() {
                       
        FacesContext context = FacesContext.getCurrentInstance();
        if(selected == null) {
        context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","Selecione um usuário!"));
                                  return "usuario";
        }
              
        usuarioServico.remover(selected);       
        
                
        return "retornoservico";
    }
    
    public String alterar() {
                       
        FacesContext context = FacesContext.getCurrentInstance();
        if(selected == null) {
        context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","Selecione um usuário!"));
                                  return "usuario";
        }
                       
        return "alterausuario";
    }
    
    public String alteraUsuario() {
                       
        FacesContext context = FacesContext.getCurrentInstance();
        if(!CadastroUsuario.validarEmail(selected.getEmail())) {
            context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","Informe um email válido!"));
                                  return "alterausuario";
        }
        
        Usuario alterado = usuarioServico.consultarPorId(selected.getId());
        Usuario user = usuarioServico.consultarPorEmail(selected.getEmail());
        if(user != null) {
            if(user.getEmail() != alterado.getEmail()) {
                context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","O email informado já existe!"));
                                  return "alterausuario";
            }
        }
        
        usuarioServico.atualizar(selected);  
        
                       
        return "retornoservico";
    }
    
    
    public String alterarDadosPessoais() {
                       
        FacesContext context = FacesContext.getCurrentInstance();
        if(!CadastroUsuario.validarEmail(usuario.getEmail())) {
            context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","Informe um email válido!"));
                                  return "alteradados";
        }
        
        Usuario alterado = usuarioServico.consultarPorId(usuario.getId());
        Usuario user = usuarioServico.consultarPorEmail(usuario.getEmail());
        if(user != null) {
            if(user.getEmail() != alterado.getEmail()) {
                context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","O email informado já existe!"));
                                  return "alteradados";
            }
        }
        
        usuarioServico.atualizar(usuario);  
        
                       
        return "retornoservico";
    }     
       
    public Usuario getSelected(){
        return selected;
    }
    
    public void setSelected(Usuario selected) {
        this.selected = selected;
    }
       
    public List<Usuario> getLista() {
        lista = usuarioServico.recuperarUsuarios().stream()
                .filter(x -> x.getClass().getSimpleName().equals("Usuario"))
                .collect(Collectors.toList());        
        return lista;
    }
        
    public void setLista() {
        lista = usuarioServico.recuperarUsuarios().stream()
                .filter(x -> x.getClass().getSimpleName().equals("Usuario"))
                .collect(Collectors.toList());
    }
    
    public Usuario getUsuario() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (Usuario) sessao.getAttribute("logado");
        return usuario;
    }
    
    public void setUsuario() {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario = (Usuario) sessao.getAttribute("logado");
    }
          
    public String getPrimeiroNome() {
        return primeiroNome;
    }
    
    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }
    
    public String getUltimoNome() {
        return ultimoNome;
    }
    
    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getHabilitado() {
        return habilitado;
    }
    
    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }
    
    public UIComponent getMybutton() {
        return mybutton;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }
        
}
