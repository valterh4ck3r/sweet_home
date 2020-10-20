package sweet_home;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.imageio.ImageIO;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Entity
@Table(name = "TB_IMOVEL")
@NamedQueries(
        {           
            @NamedQuery(
                    name = "Imovel.RecuperarPorBanheiros",
                    query = "SELECT b FROM Imovel b WHERE b.banheiros = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorQuartos",
                    query = "SELECT q FROM Imovel q WHERE q.quartos = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorSalas",
                    query = "SELECT s FROM Imovel s WHERE s.salas = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorTipo",
                    query = "SELECT t FROM Imovel t WHERE t.tipo = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorSalas",
                    query = "SELECT s FROM Imovel s WHERE s.salas = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarImoveis",
                    query = "SELECT i FROM Imovel i"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorCidade",
                    query = "SELECT i FROM Imovel i WHERE i.endereco IN (SELECT e FROM Endereco e WHERE e.cidade = ?1)"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorEstado",
                    query = "SELECT i FROM Imovel i WHERE i.endereco IN (SELECT e FROM Endereco e WHERE e.estado = ?1)"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorUsuario",
                    query = "SELECT i FROM Imovel i WHERE i.usuario = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorId",
                    query = "SELECT i FROM Imovel i WHERE i.id = ?1"
            )
          
        }
)


@Access(AccessType.FIELD)
@ManagedBean
@RequestScoped
public class Imovel extends Entidade {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
        
    @NotNull
    @Column(name = "QUARTOS")
    private int quartos;
    
    @NotNull
    @Column(name = "BANHEIROS")
    private int banheiros;
    
    @NotNull
    @Column(name = "SALAS")
    private int salas;
    
    
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @NotNull
    @Column(name = "TIPO_IMOVEL")
    private int tipo;
    
    @NotNull
    @Column(name = "VALOR")
    private double valor;
          
    @NotNull
    @Column(name = "PISCINA")
    private boolean piscina;

    @NotNull
    @Column(name = "GARAGEM")
    private boolean garagem;
    
    @NotNull
    @Column(name = "SALA_REUNIAO")
    private boolean salaReuniao;
    
    @NotNull
    @Column(name = "BEIRA_MAR")
    private boolean beiraMar;
    
    @Lob
    @ElementCollection
    @Column(name = "IMAGENS")
    private List<byte[]> imagens;
        
    
    @NotNull
    @ManyToOne
    private Usuario usuario;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_ENDERECO  ", referencedColumnName = "ID")
    private Endereco endereco;
    
    public Imovel() {
    	
    }
    
    public Imovel(Long id, int quartos, int banheiros, int salas, String descricao, int tipo, boolean piscina, boolean garagem, boolean salaReuniao, boolean beiraMar, double valor, List<byte[]> imagens, Usuario usuario, Endereco endereco) {
    	
    	this.id = id;
    	this.quartos= quartos;
    	this.banheiros = banheiros;
    	this.salas = salas;
    	this.descricao = descricao;
    	this.tipo = tipo;
    	this.valor = valor;
    	this.piscina = piscina;
    	this.garagem = garagem;
    	this.salaReuniao = salaReuniao;
    	this.beiraMar = beiraMar;
    	this.usuario = usuario;
    	this.endereco = endereco;    	
    	this.imagens = imagens;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuartos() {
        return quartos;
    }

    public void setQuartos(int quartos) {
        this.quartos = quartos;
    }
    
    public int getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(int banheiros) {
        this.banheiros = banheiros;
    }
    
    public int getSalas() {
        return salas;
    }

    public void setSalas(int salas) {
        this.salas = salas;
    }
    
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public boolean getPiscina() {
    	return piscina;
    }
    
    public void setPiscina(boolean piscina) {
    	this.piscina = piscina;
    }
    
    public boolean getGaragem() {
    	return garagem;
    }
    
    public void setGaragem(boolean garagem) {
    	this.garagem = garagem;
    }
    
    public boolean getSalaReuniao() {
    	return salaReuniao;
    }
    
    public void setSalaReuniao(boolean salaReuniao) {
    	this.salaReuniao = salaReuniao;
    }
    
    public boolean getBeiraMar() {
    	return beiraMar;
    }
    
    public void setBeiraMar(boolean beiraMar) {
    	this.beiraMar = beiraMar;
    }
    
    public List<byte[]> getImagens() {
    	return imagens;
    }
    
    public void setImagens(List<byte[]> imagens) {
    	this.imagens = imagens;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        //this.endereco.setImovel(this);
    }
    
    public static byte[] imagetoBytes(String name) {
    	
    	byte[] imageInByte = null;
    	
    	try {
    		InputStream stream = new FileInputStream(name);    		
    		imageInByte = IOUtils.toByteArray(stream);
    		return imageInByte;
    	}
    	catch(IOException e) {}
    	return imageInByte;
    }

    public StreamedContent byteToStream(byte[] img) {    	
    	
    	InputStream stream = new ByteArrayInputStream(img);
    	StreamedContent content = new DefaultStreamedContent(stream);
    	return content;
    }
    
    public int imgAltura(byte[] img) {
    	
    	try {
	        ByteArrayInputStream bais = new ByteArrayInputStream(img);
	        BufferedImage image = ImageIO.read(bais);	
	        int h = image.getHeight();
	        int w = image.getWidth();        
	        return (550*h)/w;
    	}catch(IOException e) {e.printStackTrace();}
    	return 370;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Imovel)) {
            return false;
        }

        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }
    
}
