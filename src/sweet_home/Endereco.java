package sweet_home;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "TB_ENDERECO")
@NamedQueries(
        {           
            @NamedQuery(
                    name = "Endereco.RecuperarPorRua",
                    query = "SELECT e FROM Endereco e WHERE e.rua = ?1"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorNumero",
                    query = "SELECT e FROM Endereco e WHERE e.numero = :numero"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorBairro",
                    query = "SELECT e FROM Endereco e WHERE e.bairro = ?1"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorBairroAprox",
                    query = "SELECT e FROM Endereco e WHERE e.bairro LIKE :bairro ORDER BY e.bairro"
            )
            ,
            @NamedQuery(
                    name = "Endereco.RecuperarPorRuaOrdenando",
                    query = "SELECT e FROM Endereco e WHERE e.rua = :rua ORDER BY e.rua ASC"
            )
        }
)
@NamedNativeQueries(
        
		{
            @NamedNativeQuery(
                    name = "Endereco.RecuperarEnderecos",
                    query = "SELECT * FROM TB_ENDERECO",
                    resultClass = Endereco.class
            )
        
        }
)
@Access(AccessType.FIELD)
public class Endereco extends Entidade {

    private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "RUA")
    private String rua;

    @NotNull
    @Column(name = "NUMERO")
    private String numero;

    @NotNull
    @Column(name = "BAIRRO")
    private String bairro;

    @NotNull
    @Column(name = "CIDADE")
    private String cidade;
    
    @NotNull
    @Column(name = "ESTADO")
    private String estado;
    
    @NotNull
    @Column(name = "CEP")
    private String CEP;
       
    
    @OneToOne(mappedBy = "endereco", cascade = CascadeType.ALL)
    private Imovel imovel;

    
    public Endereco() {
        
    }

    public Endereco(Long id, String rua, String numero, String bairro, String cidade, String estado, String CEP, Imovel imovel) {
        this.id = id;        
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.CEP = CEP;
        this.imovel = imovel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCEP() {
        return CEP;
    }
    
    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
        //this.imovel.setEndereco(this);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Endereco)) {
            return false;
        }

        Endereco other = (Endereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }
    
}
