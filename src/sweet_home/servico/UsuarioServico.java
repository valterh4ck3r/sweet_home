package sweet_home.servico;

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

import sweet_home.Usuario;


@Stateless(name = "ejb/UsuarioServico") 
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class UsuarioServico extends Servico<Usuario> {

    @PostConstruct
    public void init() {
        super.setClasse(Usuario.class);
    }
    
    @Override
    public Usuario criar() {
        return new Usuario();
    }
    
    
        
    public boolean existeUsuarioPorId(@NotNull Usuario entidade) {
        TypedQuery<Usuario> query
                = entityManager.createNamedQuery("Usuario.RecuperarPorId", classe);
        query.setParameter(1, entidade.getId());
        return !query.getResultList().isEmpty();
    }

    public boolean existeUsuarioPorPrimeiroNome(@NotNull Usuario entidade) {
        TypedQuery<Usuario> query
                = entityManager.createNamedQuery("Usuario.RecuperarPorPrimeiroNome", classe);
        query.setParameter(1, entidade.getPrimeiroNome());
        return !query.getResultList().isEmpty();
    }
    
    public boolean existeUsuarioPorUltimoNome(@NotNull Usuario entidade) {
        TypedQuery<Usuario> query
                = entityManager.createNamedQuery("Usuario.RecuperarPorUltimoNome", classe);
        query.setParameter(1, entidade.getUltimoNome());
        return !query.getResultList().isEmpty();
    }
    
    public boolean existeUsuarioPorEmail(@NotNull Usuario entidade) {
        TypedQuery<Usuario> query
                = entityManager.createNamedQuery("Usuario.RecuperarPorEmail", classe);
        query.setParameter(1, entidade.getEmail());
        return !query.getResultList().isEmpty();
    }
    
    public List<Usuario> recuperarUsuarios() {
        TypedQuery<Usuario> query
                = entityManager.createNamedQuery("Usuario.RecuperarUsuarios", classe);
        return query.getResultList();
    }  
        
    @TransactionAttribute(SUPPORTS) 
    public Usuario consultarPorNome(@NotNull String nome) {
        return super.consultarEntidade(new Object[] {nome}, "Usuario.RecuperarPorNome");
    }

    @TransactionAttribute(SUPPORTS) 
    public Usuario consultarPorEmail(@NotNull String email) {
        List<Usuario> lista =  super.consultarEntidades(new Object[] {email}, "Usuario.RecuperarPorEmail");
        return lista.isEmpty() ? null : lista.get(0);
    }
    
}
