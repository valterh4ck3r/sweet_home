package sweet_home.servico;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionAttributeType.NOT_SUPPORTED;
import static javax.ejb.TransactionManagementType.CONTAINER;
import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sweet_home.Entidade;


@TransactionManagement(CONTAINER)
@TransactionAttribute(REQUIRED)
public abstract class Servico<T extends Entidade> {

    @PersistenceContext(name = "sweet_home", type = TRANSACTION)
    protected EntityManager entityManager;
    protected Class<T> classe;

    @TransactionAttribute(NOT_SUPPORTED)
    protected void setClasse(@NotNull Class<T> classe) {
        this.classe = classe;
    }

    @TransactionAttribute(SUPPORTS)
    public abstract T criar();

    @TransactionAttribute(SUPPORTS)
    public boolean existe(@NotNull T entidade) {
        entidade = entidade.getId() == null ? null :  
                entityManager.find(classe, entidade.getId());
        return entidade != null;
    }

    public void persistir(@Valid T entidade) {
        if (!existe(entidade)) {
            entityManager.persist(entidade);
        }
    }
    
    public void remover(@Valid T entidade){
        if(existe(entidade)){
            entidade = entityManager.merge(entidade);
            entityManager.remove(entidade);
            entityManager.flush();
        }
    }

    public void atualizar(@Valid T entidade) {
        if (existe(entidade)) {
            entityManager.merge(entidade);
            entityManager.flush();
        }
    }

    @TransactionAttribute(SUPPORTS)
    public T consultarPorId(@NotNull Long id) {
        return entityManager.find(classe, id);
    }

    @TransactionAttribute(SUPPORTS)
    protected T consultarEntidade(Object[] parametros, String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getSingleResult();
    }

    @TransactionAttribute(SUPPORTS)
    protected List<T> consultarEntidades(Object[] parametros, String nomeQuery) {
        TypedQuery<T> query = entityManager.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }
}
