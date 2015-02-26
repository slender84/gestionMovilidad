
package controllers;


import entities.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class SessionController implements Serializable {

    
    public SessionController() {
        
        
        
    }
    
     private Usuario user;
     
    
    private String zonaHoraria;
    private boolean mostrar=true;

    
    
    
    
    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }
    
    
    public void timezoneChange(){
     
        mostrar=false;
        
           
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    
    
    
}
