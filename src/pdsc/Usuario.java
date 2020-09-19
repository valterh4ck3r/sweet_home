package pdsc;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.io.Serializable;

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
import javax.transaction.UserTransaction;

import com.sun.istack.internal.NotNull;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "TB_USUARIO")
@Stateless
@ManagedBean
@RequestScoped

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;
    
	@NotNull
    @Column(name = "TXT_NOME")
    private String nome;
    
	@NotNull
    @Column(name = "TXT_SENHA")
    private String senha;
    
	@NotNull
    @Column(name = "TXT_EMAIL")
    private String email;
        
    @Transient
    @PersistenceContext(name = "pdsc", type = TRANSACTION)
    private EntityManager entityManager;
    
    @Transient
    @Resource
    private UserTransaction userTransaction;
    
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
