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

import sweet_home.Imovel;

@Stateless(name = "ejb/ImovelServico")
@LocalBean
@ValidateOnExecution(type = ExecutableType.ALL)
public class ImovelServico extends Servico<Imovel> {

    @PostConstruct
    public void init() {
        super.setClasse(Imovel.class);
    }
    
    public Imovel criar() {
        return new Imovel();
    }
    
    public void salvar(Imovel imovel){
        entityManager.persist(imovel);
    }
    
    public void atualizar(Imovel imovel){
        entityManager.merge(imovel);
        entityManager.flush();
    }
         
    public List<Imovel> recuperarImoveis() {
        TypedQuery<Imovel> query
                = entityManager.createNamedQuery("Imovel.RecuperarImoveis", classe);
        return query.getResultList();
    }    
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> consultarPorQuartos(@NotNull int quartos) {
        return super.consultarEntidades(new Object[] {quartos}, "Imovel.RecuperarPorQuartos");         
    }
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> consultarPorBanheiros(@NotNull int banheiros) {
        return super.consultarEntidades(new Object[] {banheiros}, "Imovel.RecuperarPorBanheiros");
    }

}


