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

import sweet_home.Administrador;
import sweet_home.Imovel;
import sweet_home.Usuario;

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
               
    public List<Imovel> recuperarImoveis() {
        TypedQuery<Imovel> query
                = entityManager.createNamedQuery("Imovel.RecuperarImoveis", classe);
        return query.getResultList();
    }    
    
    
    
    public List<Imovel> filtrar(String t, String q, String b, String s, String p, String g, 
    		String sala, String mar, double menor, double maior, String estado, String cidade) {
    	    	
    	String tipo = "i.tipo = :tipo";
    	String quartos = q.length() == 0 ? "" : " AND i.quartos = :quartos";
    	String banheiros = b.length() == 0 ? "" : " AND i.banheiros = :banheiros";
    	String salas = s.length() == 0 ? "" : " AND i.salas = :salas";    	
    	String piscina = p.equals("2") ? "" : " AND i.piscina = :piscina";
    	String garagem = g.equals("2") ? "" : " AND i.garagem = :garagem";
    	String salaReuniao = sala.equals("2") ? "" : " AND i.salaReuniao = :salaReuniao";
    	String beiraMar = mar.equals("2") ? "" : " AND i.beiraMar = :beiraMar";    	
    	String cid = cidade.length() == 0 ? "" : " AND i.endereco.cidade = :cidade";
    	String est = estado.length() == 0 ? "" : " AND i.endereco.estado = :estado";
    	String valor = " AND i.valor BETWEEN :menor AND :maior";   	    	
    	
        TypedQuery<Imovel> query
                = entityManager.createQuery("SELECT i FROM Imovel i WHERE "
                		+ tipo + quartos + banheiros + salas + piscina + garagem 
                		+ salaReuniao + beiraMar + est + cid + valor , classe);
        
        query.setParameter("tipo", Integer.parseInt(t)); 
        if(q.length() > 0) query.setParameter("quartos", Integer.parseInt(q));
        if(b.length() > 0) query.setParameter("banheiros", Integer.parseInt(b));
        if(s.length() > 0) query.setParameter("salas", Integer.parseInt(s));
        if(!p.equals("2")) query.setParameter("piscina", p.equals("1"));
        if(!g.equals("2")) query.setParameter("garagem", g.equals("1"));
        if(!sala.equals("2")) query.setParameter("salaReuniao", sala.equals("1"));
        if(!mar.equals("2")) query.setParameter("beiraMar", mar.equals("1"));
        query.setParameter("menor", menor);
        query.setParameter("maior", maior);       
        if(estado.length() > 0) query.setParameter("estado", estado);
        if(cidade.length() > 0) query.setParameter("cidade", cidade);
        
        
        return query.getResultList();
    }    
    
    
    @TransactionAttribute(SUPPORTS) 
    public Imovel recuperarPorId(@NotNull Long id) {
        List<Imovel> lista = super.consultarEntidades(new Object[] {id}, "Imovel.RecuperarPorId");
        return lista.isEmpty() ? null : lista.get(0);
    }   
    
    @TransactionAttribute(SUPPORTS) 
    public List<Imovel> recuperarPorUsuario(@NotNull Usuario u) {
        return super.consultarEntidades(new Object[] {u}, "Imovel.RecuperarPorUsuario");         
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


