package sweet_home.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import sweet_home.servico.ImovelServico;
import sweet_home.Imovel;

public class ImovelTest {
	
	Imovel imov = new Imovel();
	List<Imovel> imovList;
	ImovelServico imovServ = new ImovelServico();
	
	
	@Test
	public void TestConsultar() {
		Long id = new Long(0);
		
		imov = imovServ.consultarPorId(id);
		assertEquals(id, imov.getId());		
	}
	
	@Test
	public void TestListarImoveis() {
		
		imovList = imovServ.recuperarImoveis();
		assertNotNull(imovList);	
	}
	
	@Test
	public void TestRemoverImovel() {
		Imovel imovReturned = new Imovel();
		
		imovServ.remover(imov);
		imovServ.consultarPorId(imov.getId());
		assertNull(imovReturned);	
	}
	
	@Test
	public void TestAdicionarImovel() {
		Imovel imovReturned = new Imovel();
		
		imovServ.salvar(imov);
		imovReturned = imovServ.consultarPorId(imov.getId());
		assertEquals(imov, imovReturned);	
	}
	

}
