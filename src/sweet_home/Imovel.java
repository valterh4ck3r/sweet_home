package sweet_home;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

import org.primefaces.model.DefaultStreamedContent;

@Entity
@Table(name = "TB_IMOVEL")
@NamedQueries(
        {           
            @NamedQuery(
                    name = "Imovel.RecuperarPorBanheiros",
                    query = "SELECT b FROM Imovel b WHERE b.banheiros = ?1"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarImoveis",
                    query = "SELECT i FROM Imovel i"
            ),
            @NamedQuery(
                    name = "Imovel.RecuperarPorSalaReuniao",
                    query = "SELECT r FROM Imovel r WHERE r.salareuniao = ?1"
            ),
          
        }
)
//@NamedNativeQueries(
//        
//		{
//            @NamedNativeQuery(
//                    name = "Imovel.RecuperarImoveis",
//                    query = "SELECT * FROM TB_IMOVEL",
//                    resultClass = Endereco.class
//            )
//        
//        }
//)
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
    
    @NotNull
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @NotNull
    @Column(name = "TIPO_IMOVEL")
    private int tipo;
    
    @NotNull
    @Column(name = "VALOR")
    private double valor;
          
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
    
    @NotNull
    @Column(name = "PISCINA")
    private boolean piscina;
    
    @NotNull
    @Column(name = "BEIRAMAR")
    private boolean beiramar;
    
    @NotNull
    @Column(name = "SALAREUNIAO")
    private boolean salareuniao;
    
    public Imovel() {
    	
    }
    
    public Imovel(Long id, int quartos, int banheiros, int salas, String descricao, int tipo, double valor, List<byte[]> imagens, Usuario usuario, Endereco endereco , boolean piscina , boolean beiramar, boolean salareuniao) {
    	
    	this.id = id;
    	this.quartos= quartos;
    	this.banheiros = banheiros;
    	this.salas = salas;
    	this.descricao = descricao;
    	this.tipo = tipo;
    	this.valor = valor;
    	this.usuario = usuario;
    	this.endereco = endereco;    	
    	this.imagens = imagens;
    	this.piscina = piscina;
    	this.beiramar = beiramar;
    	this.salareuniao = salareuniao;
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

    public boolean isPiscina() {
		return piscina;
	}

	public void setPiscina(boolean piscina) {
		this.piscina = piscina;
	}

	public boolean isBeiramar() {
		return beiramar;
	}

	public void setBeiramar(boolean beiramar) {
		this.beiramar = beiramar;
	}
	
	public boolean isSalaReuniao() {
		return salareuniao;
	}

	public void setSalaReuniao(boolean salareuniao) {
		this.salareuniao = salareuniao;
	}

	public static byte[] imagetoBytes(String name) {
    	
    	byte[] imageInByte = null;
    	
    	try {
	    	BufferedImage imagem = ImageIO.read(new File(name));
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	ImageIO.write( imagem, "jpg", baos );
	    	baos.flush();
	    	imageInByte = baos.toByteArray();
	    	baos.close();
    	}
    	catch(IOException e) {}
    	return imageInByte;
    }

    public DefaultStreamedContent byteToStream(byte[] img) {    	
    	InputStream stream = new ByteArrayInputStream(img);
    	return new DefaultStreamedContent(stream);
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
