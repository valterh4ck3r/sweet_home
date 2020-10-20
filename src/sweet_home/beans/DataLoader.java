package sweet_home.beans;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import sweet_home.Administrador;
import sweet_home.Endereco;
import sweet_home.Entidade;
import sweet_home.Imovel;
import sweet_home.Telefone;
import sweet_home.Tipo;
import sweet_home.Usuario;
import sweet_home.servico.AdministradorServico;
import sweet_home.servico.EnderecoServico;
import sweet_home.servico.ImovelServico;
import sweet_home.servico.TelefoneServico;
import sweet_home.servico.UsuarioServico;


@javax.faces.bean.ManagedBean(name = "dataLoader")
@javax.faces.bean.SessionScoped
public class DataLoader implements Serializable {

	private static final long serialVersionUID = 1L;
			
	private List<Usuario> usuarios = null;	
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private AdministradorServico admServico;
	@EJB
	private TelefoneServico foneServico;	
	@EJB
	private ImovelServico imovelServico;
	
	@PersistenceContext(name = "sweet_home", type = TRANSACTION)
    protected EntityManager entityManager;
	
	
	public void carregarDados() {		

		List<Telefone> lista = foneServico.consultarTelefones();		
		if(lista.size() == 0) carregarUsuarios();
	}
	
	private void carregarUsuarios() {		
				
		admServico.persistir(constroeAdm("rosaalexandrino@gmail.com", "hBuxA23", "Rosa", "Alexandrino", true, "96712385", 1));
		admServico.persistir(constroeAdm("igormoraes@gmail.com", "LbyI20", "Igor", "Moraes", true, "96785321", 3));
		usuarioServico.persistir(new Usuario(null, "joanamendonca@gmail.com", "aopHMXX9", "Joana", "Mendonça", true));	
		usuarioServico.persistir(new Usuario(null, "mariojorge@outlook.com", "2pKbVjpe", "Mario", "Jorge", true));	
		admServico.persistir(constroeAdm("maiconrodrigues@bol.com.br", "MYJZf8gQ", "Maicon", "Rodrigues", true, "96532441", 3));
		admServico.persistir(constroeAdm("joicesantos@hotmail.com", "oB6LE1eF", "Joice", "Santos", true, "96225321", 2));
		usuarioServico.persistir(new Usuario(null, "mariaaparecida@live.com", "oPpP2rZN", "Maria", "Aparecida", false));	
		usuarioServico.persistir(new Usuario(null, "fernandotads@uol.com.br", "j8NsFXNS", "Fernando", "Bezerra", true));	
		usuarioServico.persistir(new Usuario(null, "alanaarruda@yahoo.com.br", "u5mrl9KL", "Alana", "Arruda", true));	
		admServico.persistir(constroeAdm("marcoslima@yahoo.com", "hdm1IwmZ", "Marcos", "Lima", true, "96717300", 1));
		admServico.persistir(constroeAdm("igorribas@live.com", "gHaiID", "Igor", "Ribas", false, "96785211", 3));
		usuarioServico.persistir(new Usuario(null, "cassiabatista@uol.com.br", "PmLLNS", "Cássia", "Batista", true));	
		admServico.persistir(constroeAdm("gustavogarcia@yahoo.com.br", "NamSDN", "Gustavo", "Garcia", true, "96534862", 3));
		admServico.persistir(constroeAdm("brunomachado@yahoo.com", "Uiab90", "Bruno", "Machado", true, "96221133", 2));
		usuarioServico.persistir(new Usuario(null, "marcosmedeiros@yahoo.com.br", "A976snsj", "Marcos", "Medeiros", true));	
		usuarioServico.persistir(new Usuario(null, "nayarapinheiro@yahoo.com", "654JhG", "Nayara", "Pinheiro", true));	
		
		carregarTelefones();
	}
	
	private void carregarTelefones() {		
		
		usuarios = usuarioServico.recuperarUsuarios();
		
		foneServico.persistir(new Telefone(null, usuarios.get(0), "995201778", "81"));		
		foneServico.persistir(new Telefone(null, usuarios.get(0), "923758461", "81"));
		foneServico.persistir(new Telefone(null, usuarios.get(0), "987452100", "81"));
		foneServico.persistir(new Telefone(null, usuarios.get(0), "985203164", "81"));		
		foneServico.persistir(new Telefone(null, usuarios.get(2), "987264169", "81"));
		foneServico.persistir(new Telefone(null, usuarios.get(2), "976521543", "81"));
		foneServico.persistir(new Telefone(null, usuarios.get(3), "984673125", "81"));		
		foneServico.persistir(new Telefone(null, usuarios.get(5), "989865214", "81"));			
		foneServico.persistir(new Telefone(null, usuarios.get(8), "986452712", "81"));			
		foneServico.persistir(new Telefone(null, usuarios.get(9), "998764523", "81"));			
	
		carregarImoveis();		
	}
		
	private void carregarImoveis() {		
			
		Endereco e1 = new Endereco(null, "Rua Leônidas Cravo Gama", "120", "Afogados", "Recife", "PE", "50770-000", null);
		Endereco e2 = new Endereco(null, "Rua Armando Silva", "95", "Afogados", "Recife", "PE", "50830-500", null);
		Endereco e3 = new Endereco(null, "Rua do Cacique", "40", "Ibura", "Recife", "PE", "51230-000", null);
		Endereco e4 = new Endereco(null, "Rua da Ladeira", "125", "Ibura", "Recife", "PE", "54240-695", null);
		Endereco e5 = new Endereco(null, "Rua Córrego do Joaquim", "20", "Nova Descoberta", "Recife", "PE", "52091-300", null);
		Endereco e6 = new Endereco(null, "Rua Nossa Senhora da Pompéia", "200", "Encruzilhada", "Recife", "PE", "52041-160", null);
		Endereco e7 = new Endereco(null, "Rua Conselheiro Portella", "240", "Espinheiro", "Recife", "PE", "52240-400", null);
		Endereco e8 = new Endereco(null, "Rua Braz Moscow", "93", "Piedade", "Jaboatão dos Guararapes", "PE", "54420-140", null);	
		
		List<byte[]> l = new ArrayList<>();
		
		for(int i = 1; i < 22; i++) l.add(Imovel.imagetoBytes("C:\\imagens\\"+i+".jpg"));		
		
		imovelServico.persistir(new Imovel(null, 1, 2, 2, "Descricao 1", 1, false, true, false, false, 1000.5, l.subList(0, 3), usuarios.get(0), e1));
		imovelServico.persistir(new Imovel(null, 2, 3, 4, "Descricao 2", 2, true, true, false, false, 1400, l.subList(3, 6), usuarios.get(0), e2));
		imovelServico.persistir(new Imovel(null, 1, 2, 2, "Descricao 3", 2, true, true, true, true, 2200, l.subList(6, 9), usuarios.get(3), e3));
		imovelServico.persistir(new Imovel(null, 1, 1, 1, "Descricao 4", 1, false, false, false, false, 500, l.subList(9, 12), usuarios.get(5), e4));
		imovelServico.persistir(new Imovel(null, 2, 2, 2, "Descricao 5", 2, true, false, true, true, 1800, l.subList(12, 15), usuarios.get(5), e5));
		imovelServico.persistir(new Imovel(null, 1, 2, 2, "Descricao 6", 2, false, true, true, true, 3000, l.subList(15, 18), usuarios.get(2), e6));
		imovelServico.persistir(new Imovel(null, 1, 2, 2, "Descricao 7", 2, false, true, true, true, 2500, l.subList(18, 20), usuarios.get(8), e7));
		imovelServico.persistir(new Imovel(null, 0, 0, 0, "Descricao 8", 3, false, false, false, false, 1000, l.subList(20, 21), usuarios.get(9), e8));
	}
			
	
	private Administrador constroeAdm(String email, String senha, String nome, String sobrenome, boolean habilitado, String matricula, int cargo) {
		
		Administrador adm = new Administrador();
		adm.setEmail(email);
		adm.setSenha(senha);
		adm.setPrimeiroNome(nome);
		adm.setUltimoNome(sobrenome);
		adm.setHabilitado(habilitado);
		adm.setMatricula(matricula);
		adm.setCargo(cargo);
		return adm;
	}
		
}
