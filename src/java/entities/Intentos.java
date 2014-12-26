package entities;
// Generated 26-dic-2014 18:13:18 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Intentos generated by hbm2java
 */
@Entity
@Table(name="intentos"
    ,catalog="gestordb"
)
public class Intentos  implements java.io.Serializable {


     private String login;
     private Usuario usuario;
     private Integer numero;

    public Intentos() {
    }

	
    public Intentos(Usuario usuario) {
        this.usuario = usuario;
    }
    public Intentos(Usuario usuario, Integer numero) {
       this.usuario = usuario;
       this.numero = numero;
    }
   
     @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="usuario"))@Id @GeneratedValue(generator="generator")

    
    @Column(name="login", unique=true, nullable=false, length=20)
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }

@OneToOne(fetch=FetchType.LAZY)@PrimaryKeyJoinColumn
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @Column(name="numero")
    public Integer getNumero() {
        return this.numero;
    }
    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }




}


