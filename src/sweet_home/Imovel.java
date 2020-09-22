package sweet_home;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_IMOVEL")
@Access(AccessType.FIELD)
public class Imovel extends Entidade {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
        
    @Column(name = "QUARTOS")
    private int quartos;
    
    @Column(name = "BANHEIROS")
    private int banheiros;
    
    @Column(name = "SALAS")
    private int salas;
    
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @Column(name = "TIPO_IMOVEL")
    private int tipo;
    
    @Column(name = "VALOR")
    private double valor;
    
    @ManyToOne
    private Usuario usuario;
	
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_ENDERECO  ", referencedColumnName = "ID")
    private Endereco endereco;
    
    public Imovel() {
    	
    }
    
    public Imovel(Long id, int quartos, int banheiros, int salas, String descricao, int tipo, Usuario usuario, Endereco endereco) {
    	
    	this.id = id;
    	this.quartos= quartos;
    	this.banheiros = banheiros;
    	this.salas = salas;
    	this.descricao = descricao;
    	this.tipo = tipo;
    	this.usuario = usuario;
    	this.endereco = endereco;    			
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
