package entities;
// Generated 12-mar-2015 23:13:57 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ComentarioAsignatura generated by hbm2java
 */
@Entity
@Table(name="comentario_asignatura"
    ,catalog="gestordb"
)
public class ComentarioAsignatura  implements java.io.Serializable {


     private Integer idcomentario;
     private Asignatura asignatura;
     private Usuario usuario;
     private String texto;
     private Date fecha;
     private String estado;

    public ComentarioAsignatura() {
    }

	
    public ComentarioAsignatura(Asignatura asignatura, String texto, Date fecha, String estado) {
        this.asignatura = asignatura;
        this.texto = texto;
        this.fecha = fecha;
        this.estado = estado;
    }
    public ComentarioAsignatura(Asignatura asignatura, Usuario usuario, String texto, Date fecha, String estado) {
       this.asignatura = asignatura;
       this.usuario = usuario;
       this.texto = texto;
       this.fecha = fecha;
       this.estado = estado;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idcomentario", unique=true, nullable=false)
    public Integer getIdcomentario() {
        return this.idcomentario;
    }
    
    public void setIdcomentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns( { 
        @JoinColumn(name="codAsignatura", referencedColumnName="codAsignatura", nullable=false), 
        @JoinColumn(name="nombreUniversidad", referencedColumnName="nombreUniversidad", nullable=false) } )
    public Asignatura getAsignatura() {
        return this.asignatura;
    }
    
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="login")
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @Column(name="texto", nullable=false)
    public String getTexto() {
        return this.texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha", nullable=false, length=19)
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    @Column(name="estado", nullable=false, length=12)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }




}


