package sweet_home;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_USUARIO")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Usuario.RecuperarPorEmail",
                    query = "SELECT u FROM Usuario u WHERE u.email = ?1"
            ),
            @NamedQuery(
                    name = "Usuario.RecuperarAtivos",
                    query = "SELECT u FROM Usuario u WHERE u.habilitado = :habilitado ORDER BY u.primeiroNome"
            ),
            @NamedQuery(	
                    name = "Usuario.RecuperarPorNome",
                    query = "SELECT u FROM Usuario u WHERE u.primeiroNome LIKE ?1 OR u.ultimoNome LIKE ?1"
            ),
            @NamedQuery(
                    name = "Usuario.RecuperarPorPrimeiroNome",
                    query = "SELECT u FROM Usuario u WHERE u.primeiroNome LIKE ?1"
            ),
            @NamedQuery(
                    name = "Usuario.RecuperarPorUltimoNome",
                    query = "SELECT u FROM Usuario u WHERE u.ultimoNome LIKE ?1"
            ),
            @NamedQuery(
                    name = "Usuario.RecuperarPorId",
                    query = "SELECT u FROM Usuario u WHERE u.id = ?1"
            ),
                   
                 
        }
)
@NamedNativeQueries(
                
		{
            @NamedNativeQuery(
                    name = "Usuario.RecuperarUsuarios",
                    query = "SELECT * FROM TB_USUARIO",
                    resultClass = Usuario.class
            )
        
        }
)
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
@ManagedBean
@RequestScoped
public class Usuario extends Entidade {

    private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull    
    @Column(name = "EMAIL", unique = true, length = 100)
    private String email;

    @NotNull
    @Column(name = "SENHA", length = 200)
    private String senha;

    @NotNull
    @Column(name = "PRIMEIRO_NOME", length = 50)
    private String primeiroNome;

    @NotNull
    @Column(name = "ULTIMO_NOME", length = 50)
    private String ultimoNome;

    @NotNull
    @Column(name = "HABILITADO")
    private boolean habilitado;
   
    
    @OneToMany(mappedBy = "usuario", 
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "TB_USUARIO_TELEFONE", joinColumns
            = {
                @JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns
            = {
                @JoinColumn(name = "ID_TELEFONE")})
    private List<Telefone> telefones;
    
    @OneToMany(mappedBy = "usuario", 
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "TB_USUARIO_IMOVEL", joinColumns
            = {
                @JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns
            = {
                @JoinColumn(name = "ID_IMOVEL")})
    private List<Imovel> imoveis;    
       
    
    public Usuario() {
        this.telefones = new ArrayList<>();   
        this.imoveis = new ArrayList<>();   
    }
    
    public Usuario(Long id, String email, String senha, String primeiroNome, String ultimoNome, boolean habilitado) {
    	this.id = id;
        this.email = email;
        this.senha = senha;
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.telefones = new ArrayList<>();
        this.imoveis = new ArrayList<>();   
        this.habilitado = habilitado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }
           
    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }
    
    public boolean addIMovel(Imovel imovel) {
        imovel.setUsuario(this);
        return imoveis.add(imovel);
    }
        
    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
    
    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
   
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }

        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }
}
