package sweet_home.servico;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

import javax.persistence.Query;
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
    
    public List<Imovel> recuperarImoveisComFiltro(boolean garagem, boolean piscina, boolean beiramar, boolean salareuniao , int quartos , int metros) {
    	
    	String sqlQuery = "SELECT * FROM TB_IMOVEL i";
    	
    	if(garagem || piscina || beiramar || salareuniao || quartos > 0 || metros > 0) {
    		sqlQuery+= " WHERE ";
    	}
    	
    	if(garagem) {
    		sqlQuery+= " i.garagem = " + garagem;
    	}
    	
    	if(piscina) {    		
    		if(garagem) {
    			sqlQuery+=" AND ";
    		}    		
    		sqlQuery+= " i.piscina = " + piscina;
    	}
    	
    	if(beiramar) {
    		if(garagem || piscina) {
    			sqlQuery+=" AND ";
    		}  
    		sqlQuery+= " i.beiramar = " + beiramar;
    	}
    	
    	if(salareuniao) {    		
    		if(garagem || piscina || beiramar) {
    			sqlQuery+=" AND ";
    		}  
    		sqlQuery+= " i.salareuniao = " + salareuniao;
    	}  
    	
    	if(quartos != 0) {
    		if(garagem || piscina || beiramar || salareuniao) {
    			sqlQuery+=" AND ";
    		}  
    		sqlQuery+= " i.quartos = " + quartos;    		
    	}
    	
    	if(metros != 0) {
    		if(garagem || piscina || beiramar || salareuniao || quartos != 0) {
    			sqlQuery+=" AND ";
    		}  
    		sqlQuery+= " i.metros = " + metros;    		
    	}
    	
    	
        Query query = entityManager.createNativeQuery(sqlQuery, classe);
        List<Imovel> imoveis = query.getResultList();       
        return imoveis;
    }  
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> consultarPorQuartos(@NotNull int quartos) {
        return super.consultarEntidades(new Object[] {quartos}, "Imovel.RecuperarPorQuartos");         
    }
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> consultarPorBanheiros(@NotNull int banheiros) {
        return super.consultarEntidades(new Object[] {banheiros}, "Imovel.RecuperarPorBanheiros");
    }
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> consultarPorSalaReuniao(@NotNull int salareuniao) {
        return super.consultarEntidades(new Object[] {salareuniao}, "Imovel.RecuperarPorSalaReuniao");
    }

}


