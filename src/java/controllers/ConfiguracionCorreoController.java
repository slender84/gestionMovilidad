
package controllers;

import entities.CorreoConf;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.UsuarioService;
import model.utils.Encrypter;
import model.utils.beanUtilidades;
import org.apache.commons.configuration.ConfigurationException;


@ManagedBean
@ViewScoped
public class ConfiguracionCorreoController implements Serializable{

    @ManagedProperty (value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty (value = "#{usuarioService}")
    private UsuarioService usuarioService;
    
    private CorreoConf correoConf;
    
    
    public ConfiguracionCorreoController() {
    }

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
        
        correoConf=(CorreoConf)beanUtilidades.getCorreoConf();
        
        
    }
    
    public String editarConfiguracion(){
    
        
        
    try{    
    correoConf.setPassword(Encrypter.encrypt(correoConf.getPassword()));
    beanUtilidades.setCorreoConf(correoConf);
    }catch(Exception ex){
        ex.printStackTrace();
        beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
    }
    
    beanUtilidades.creaMensaje("Configuración de correo guardada correctamente", FacesMessage.SEVERITY_INFO);
    return null;
}
    
    
    
}
