package sweet_home.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import sweet_home.Usuario;
import sweet_home.servico.UsuarioServico;
import sweet_home.servico.VerifyRecaptcha;

@ManagedBean(name="CadastroUsuario")
@RequestScoped
public class CadastroUsuario {
        
    @EJB
    private UsuarioServico usuarioServico; 
    private static String email = null;
    private static String senha = null;
    private static String nome = null;
    private static String sobrenome = null;   
    private UIComponent mybutton;
    private UIComponent campoInvalido;
    
        
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
            try {
                String gRecaptchaResponse = FacesContext.getCurrentInstance().
                  getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
                 boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
                 if(verify){
                     Usuario usuario = new Usuario();
                     usuario.setEmail(email);
                     usuario.setSenha(senha);
                     usuario.setPrimeiroNome(nome);
                     usuario.setUltimoNome(sobrenome);
                     usuarioServico.persistir(usuario);
                     return "encontrado";
                 }else{
                     context.addMessage(mybutton.getClientId(context), 
                                  new FacesMessage("","  Selecione o captcha!"));
                                  return "invalido";
                 }
            } catch (Exception e) {
                context.addMessage(mybutton.getClientId(context), 
                        new FacesMessage("", e.toString()));
                        return "invalido";
            }
            
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
