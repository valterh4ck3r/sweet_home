/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweethome;

import static javax.persistence.PersistenceContextType.TRANSACTION;

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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
    
    @Column(name = "INT_QUARTOS")
    private Integer quartos;
    
    @Column(name = "BOOL_PISCINA")
    private Boolean piscina;
    
    @Column(name = "BOOL_GARAGEM")
    private boolean garagem;
    
    @Transient
    @PersistenceContext(name = "pdsc", type = TRANSACTION)
    private EntityManager entityManager;
    
    @Transient
    @Resource
    private UserTransaction userTransaction;
    
    @Transient
    ArrayList usersList;
    
    @Transient
    Imovel imovelToUpdate;

    public List<Imovel> list() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Imovel> cq = cb.createQuery(Imovel.class);
        Root<Imovel> rootEntry = cq.from(Imovel.class);
        CriteriaQuery<Imovel> all = cq.select(rootEntry);
        TypedQuery<Imovel> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public String save(){
    	try {
    		Imovel novoImovel = new Imovel();
        	novoImovel.setTitulo(titulo);
        	novoImovel.setEndereco(endereco);
        	novoImovel.setGaragem(garagem);
        	novoImovel.setPiscina(piscina);
        	novoImovel.setQuartos(quartos);
            userTransaction.begin();
        	entityManager.persist(novoImovel);
        	userTransaction.commit();
    	} catch(Exception e) {
    		System.out.println(e);
    	}
    	return "index.xhtml?faces-redirect=true";
    }

    public String edit(Imovel imoveltoUpdate){
        try{
        	this.imovelToUpdate = imoveltoUpdate;
        	this.id = imoveltoUpdate.id;
        	this.titulo = imoveltoUpdate.titulo;
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/edit.xhtml?faces-redirect=true";
    }

    public String update(Imovel updatedImovel){
        try{
	        userTransaction.begin();
	    	entityManager.merge(this.imovelToUpdate);
	    	userTransaction.commit();
        	// Atualizar Usuario
        }catch(Exception e){
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";      
    }

	public String delete(Imovel deleteImovel){
        try{
        	System.out.println(deleteImovel);
	        userTransaction.begin();
	        entityManager.createQuery("DELETE FROM Imovel WHERE id = " + deleteImovel.id).executeUpdate();
	    	userTransaction.commit();
        }catch(Exception e){
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect=true";   
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

	public Integer getQuartos() {
		return quartos;
	}

	public void setQuartos(Integer quartos) {
		this.quartos = quartos;
	}

	public Boolean getPiscina() {
		return piscina;
	}

	public void setPiscina(Boolean piscina) {
		this.piscina = piscina;
	}

	public boolean getGaragem() {
		return garagem;
	}

	public void setGaragem(boolean garagem) {
		this.garagem = garagem;
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
    
    public Imovel getImovelToUpdate() {
		return imovelToUpdate;
	}

	public void setImovelToUpdate(Imovel imovelToUpdate) {
		this.imovelToUpdate = imovelToUpdate;
	}

	@Override
	public String toString() {
		return "Imovel [id=" + id + ", titulo=" + titulo + ", endereco=" + endereco + ", quartos=" + quartos
				+ ", piscina=" + piscina + ", garagem=" + garagem + "]";
	}
    
}
