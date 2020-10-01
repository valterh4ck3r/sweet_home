package sweet_home.servico;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import org.hibernate.validator.constraints.NotBlank;

import sweet_home.Telefone;
import sweet_home.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static javax.persistence.PersistenceContextType.TRANSACTION;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Stateless(name = "ejb/TelefoneServico") // O professor comentou que é padrão
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class TelefoneServico extends Servico<Telefone> {

	@PostConstruct
    public void init() {
        super.setClasse(Telefone.class);
    }
	
	@Override
	public Telefone criar() {
		
		return new Telefone();
	}
	
	
    public List<Telefone> consultarTelefones() {
        TypedQuery<Telefone> query
                = entityManager.createNamedQuery("Telefone.RecuperarTelefones", classe);
        return query.getResultList();
    }   
      
    public Telefone consultarPorNumero(@NotNull String numero) {
        TypedQuery<Telefone> query
                = entityManager.createNamedQuery("Telefone.RecuperarPorNumero", classe);
                query.setParameter("numero", numero);
                try{
                    Object obj = query.getSingleResult();
                    return (Telefone) obj;
                }catch(javax.persistence.NoResultException e) {
                    return null;
                }  
    }
     
    public List<Telefone> consultarPorEmailUsuario(@NotNull String email) {
        TypedQuery<Telefone> query
                = entityManager.createNamedQuery("Telefone.RecuperarPorEmail", classe);
                query.setParameter(1, email);
                return query.getResultList();        
    }	
        
}


