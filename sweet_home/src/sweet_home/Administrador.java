package sweet_home;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;


@Entity
@Table(name = "TB_ADMINISTRADOR")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Administrador.RecuperarPorMatricula",
                    query = "SELECT a FROM Administrador a WHERE a.matricula = ?1"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorMatriculaNumero",
                    query = "SELECT count(a) FROM Administrador a WHERE a.matricula = ?1"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorCargo",
                    query = "SELECT a FROM Administrador a WHERE a.cargo = :cargo ORDER BY a.primeiroNome"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorCargoNumero",
                    query = "SELECT a FROM Administrador a WHERE a.cargo IS NOT NULL AND a.cargo = ?1"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorNome",
                    query = "SELECT a FROM Administrador a WHERE a.primeiroNome LIKE ?1 OR a.ultimoNome LIKE ?1"
            )
            ,
            @NamedQuery(
                    name = "Administrador.RecuperarPorId",
                    query = "SELECT a FROM Administrador a WHERE a.id = ?1"
            )
        }
)

@Access(AccessType.FIELD)
@DiscriminatorValue(value = "ADMIN")
@PrimaryKeyJoinColumn(name = "ID_ADMIN", referencedColumnName = "ID")
public class Administrador extends Usuario {

    private static final long serialVersionUID = 1L;

	@NotNull
    @Size(min = 8, max = 10)
    @Pattern(regexp = "^[0-9]+$")
    @Column(name = "MATRICULA", unique = true)
    private String matricula;

    @NotNull
    @Column(name = "CARGO")
    private int cargo;

    public Administrador() {

    }

    public Administrador(String matricula, int cargo) {
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int numCargo) {
        this.cargo = numCargo;
    }

}
