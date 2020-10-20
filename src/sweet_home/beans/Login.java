package sweet_home.beans;


import sweet_home.Telefone;
import sweet_home.Usuario;
import sweet_home.servico.UsuarioServico;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "login")
@RequestScoped
public class Login implements Serializable {
      
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioServico usuarioServico;     
    private static String email = null;
    private static String senha = null;
    private static Usuario user = null;
    private static String tipo = null;        
    private UIComponent mybutton;    
    
    
    public String fazerLogin() {
                
        FacesContext context = FacesContext.getCurrentInstance();
        user = usuarioServico.consultarPorEmail(email);
        tipo = user != null ? user.getClass().getSimpleName() : null;
        
                                      
              if(user != null) {
                    if(user.getSenha().equals(senha)) {
                             
                        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                        sessao.setAttribute("logado", user);
                        if(tipo.equals("Usuario")) return "encontrado";
                        else return "admin";                    
                                                 
                    }
                    else {
                        context.addMessage(mybutton.getClientId(context), 
                                new FacesMessage("", "Login ou senha invalidos!"));
                        return "invalido";
                    }
              }   
        
              else {
                  context.addMessage(mybutton.getClientId(context), 
                        new FacesMessage("", "Login ou senha invalidos!"));
                  return "invalido";
              }  
         
    }   
      
   
    public String getSenha() {
        return senha;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Usuario getUser() {
        return user;
    }
    
    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }

    public UIComponent getMybutton() {
        return mybutton;
    }
    
}