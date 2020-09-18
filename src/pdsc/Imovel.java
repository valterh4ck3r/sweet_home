/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdsc;

import static javax.persistence.PersistenceContextType.TRANSACTION;
import static javax.persistence.PersistenceContextType.EXTENDED;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author masc0
 */
@Entity
@Table(name = "TB_IMOVEL")
@Stateless
@ManagedBean
@RequestScoped
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "TXT_TITULO")
    private String titulo;
    
    @Column(name = "TXT_ENDERECO")
    private String endereco;
        
    @Transient
    @PersistenceContext(name = "pdsc", type = TRANSACTION)
    private EntityManager entityManager;
    
    @Transient
    @Resource
    private UserTransaction userTransaction;

    public void salvar(Imovel imovel) {
        entityManager.persist(imovel);
    }
    
    @Transient
    ArrayList usersList;

    public List<Imovel> list() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Imovel> cq = cb.createQuery(Imovel.class);
        Root<Imovel> rootEntry = cq.from(Imovel.class);
        CriteriaQuery<Imovel> all = cq.select(rootEntry);
        TypedQuery<Imovel> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public String salvar(){
        int result = 0;
        try{
            Imovel newImovel = new Imovel();
            newImovel.setTitulo(titulo);
            newImovel.setEndereco(endereco);
            userTransaction.begin();
        	entityManager.persist(newImovel);
        	 userTransaction.commit();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }

    public String save(){
        int result = 0;
        try{
        	entityManager.persist(new Imovel());
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }

    public String edit(int id){
    	Imovel imovel = null;
        System.out.println(id);
        try{
        	// Obter Imovel pelo ID
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/edit.xhtml?faces-redirect=true";
    }

    public String update(Imovel u){
        //int result = 0;
        try{

        	// Atualizar Usuario
        }catch(Exception e){
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";      
    }

    public void delete(int id){
        try{
            // Remover Usuario
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
