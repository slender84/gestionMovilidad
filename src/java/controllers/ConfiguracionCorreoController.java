
package controllers;

import entities.CorreoConf;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.services.UsuarioService;
import model.utils.Seguridad;




@ManagedBean
@RequestScoped
public class ConfiguracionCorreoController implements Serializable{

    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty (value = "#{usuarioService}")
    private UsuarioService usuarioService;
    
    private CorreoConf correoConf;
    
    
    public ConfiguracionCorreoController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    

    public CorreoConf getCorreoConf() {
        return correoConf;
    }

    public void setCorreoConf(CorreoConf correoConf) {
        this.correoConf = correoConf;
    }
    
    
    
    @PostConstruct
    public void init(){
        
        correoConf=(CorreoConf)sessionController.getCorreoConf();
        
        
    }
    
    public String editarConfiguracion(){
    
        
        
    try{    
    correoConf.setPassword(Seguridad.encrypt(correoConf.getPassword()));
    sessionController.setCorreoConf(correoConf);
    }catch(Exception ex){
        
        sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
    }
    
    sessionController.creaMensaje("Configuración de correo guardada correctamente", FacesMessage.SEVERITY_INFO);
    return null;
}
    
    
    
}
