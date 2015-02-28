package controllers;

import entities.Usuario;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.services.UsuarioService;
import model.utils.Seguridad;



@ManagedBean
@RequestScoped
public class EditarPerfilController implements Serializable{

    
    @ManagedProperty(value="#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
     
   private Usuario user;
   private String password;
   private String nuevoPassword; 
   private String repitePassword;
   
    public EditarPerfilController() {
    }
    
    @PostConstruct
    public void init(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("user");
        
        
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }
    
    
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public String getRepitePassword() {
        return repitePassword;
    }

    public void setRepitePassword(String repitePassword) {
        this.repitePassword = repitePassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String editar(){
        
        if(user.getPassword().equals(Seguridad.md5Password(password))==false){
            
            sessionController.creaMensaje("el password es incorrecto", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if(nuevoPassword.equals(repitePassword)==false){
            
            sessionController.creaMensaje("el nuevo password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        user.setPassword(Seguridad.md5Password(nuevoPassword));
        usuarioService.actualizarUsuario(user);
        
        sessionController.creaMensaje("password modificado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
}


