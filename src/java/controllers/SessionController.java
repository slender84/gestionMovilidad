
package controllers;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;



@ManagedBean
@SessionScoped
public class SessionController implements Serializable{

   
    private String zonaHoraria;
    private boolean mostrar=true;
    
    
    public SessionController() {
    }

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
    
    
    
}
