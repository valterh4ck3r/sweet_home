package sweet_home.testes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import sweet_home.Telefone;
import sweet_home.Usuario;
import sweet_home.servico.UsuarioServico;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("JPQLValidation")
public class UsuarioTest extends BaseEJBTest {

//	@EJB
//    private UsuarioServico usuarioServico;
//
//    
//    @Before
//    public void setUp() throws NamingException {
//        usuarioServico= (UsuarioServico) container.getContext().lookup("java:global/pdsc/ejb/UsuarioServico!sweet_home.servico.UsuarioServico");
//    }
//        
//    
//    @After
//    public void tearDown() {   	
//    	
//        usuarioServico = null;
//    }
//
//    @Test    
//    public void t01_persistir() {
//            	
//    	
//    }	
    
}
