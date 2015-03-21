
package controllers;



import entities.Configuracion;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.services.UsuarioService;





@ManagedBean
@RequestScoped
public class ConfiguracionCorreoController implements Serializable{

    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty (value = "#{usuarioService}")
    private UsuarioService usuarioService;
    
    private Configuracion correoConf;
    private String nuevoPassword;
    
    public ConfiguracionCorreoController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

   

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    

    public Configuracion getCorreoConf() {
        return correoConf;
    }

    public void setCorreoConf(Configuracion correoConf) {
        this.correoConf = correoConf;
    }
    
    
    
    @PostConstruct
    public void init(){
        
        correoConf=(Configuracion)sessionController.getCorreoConf();
        
        
    }
    
    public String editarConfiguracion(){
    
        if(nuevoPassword.equals("")==false)
            correoConf.setPassword(nuevoPassword);
        
    try{    
    //correoConf.setPassword(Seguridad.encrypt(correoConf.getPassword()));
    sessionController.setCorreoConf(correoConf);
    }catch(Exception ex){
        
        sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
    }
    
    sessionController.creaMensaje("Configuración guardada correctamente", FacesMessage.SEVERITY_INFO);
    correoConf=sessionController.getCorreoConf();
    return null;
    
}
    
    
    
}
