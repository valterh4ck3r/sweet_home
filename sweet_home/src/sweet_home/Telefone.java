package sweet_home;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_TELEFONE")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Telefone.RecuperarTelefones",
                    query = "SELECT t FROM Telefone t"
            ),
            @NamedQuery(
                    name = "Telefone.RecuperarPorNumero",
                    query = "SELECT t FROM Telefone t WHERE t.numero = :numero"
            ),
            @NamedQuery(
                    name = "Telefone.RecuperarPorEmail",
                    query = "SELECT t FROM Telefone t WHERE t.usuario IN (SELECT u FROM Usuario u WHERE u.email = ?1)"
            )    
        }
)
@Access(AccessType.FIELD)
@ManagedBean
@RequestScoped
public class Telefone extends Entidade {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @Column(name = "NUMERO")
    private String numero;

    @NotNull
    @Column(name = "DDD")
    private String ddd;

    public Telefone() {

    }

    public Telefone(Long id, Usuario usuario, String numero, String ddd) {
        this.id = id;
        this.usuario = usuario;
        this.numero = numero;
        this.ddd = ddd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Telefone)) {
            return false;
        }

        Telefone other = (Telefone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
    }
}
