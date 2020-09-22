package pdsc;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "TB_USUARIO")
@Stateless
@ManagedBean
@RequestScoped

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//<-----Variaveis do objeto----->//
	@Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "TXT_NOME")
    private String nome;
    
    @Column(name = "TXT_SENHA")
    private String senha;
    
    @Column(name = "TXT_EMAIL")
    private String email;
        
    @Transient
    @PersistenceContext(name = "pdsc", type = TRANSACTION)
    private EntityManager entityManager;
    
    @Transient
    @Resource
    private UserTransaction userTransaction;
    
    //<-----Transações com o banco----->
    
	public List<Usuario> list() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> rootEntry = cq.from(Usuario.class);
        CriteriaQuery<Usuario> all = cq.select(rootEntry);
        TypedQuery<Usuario> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public String salvaUser(){
        int result = 0;
        Usuario user = new Usuario();
        user.setNome(nome);
        user.setSenha(senha);
        user.setEmail(email);
        
        try{
            userTransaction.begin();
        	entityManager.persist(user);
        	 userTransaction.commit();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }

    //to test
    public String editUser(Usuario user){
        try{
        	userTransaction.begin();
        	entityManager.merge(user);
        	userTransaction.commit();
        }catch(Exception e){
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";      
    }
    
    
    public void deleteUser(Usuario user){
        try{
        	Usuario removeUser = new Usuario();
        	userTransaction.begin();
        	removeUser = entityManager.merge(user);
        	entityManager.remove(removeUser);
        	userTransaction.commit();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    //<-----gets & sets----->//
    
    //----Id----//
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//----Nome----//
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
    
    //----Senha----//
	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	//----Email----//
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	//

}
