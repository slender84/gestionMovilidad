package controllers;

import entities.Usuario;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.services.UsuarioService;
import model.utils.beanUtilidades;


@ManagedBean
@ViewScoped
public class EditarPerfilController implements Serializable{

    
    @ManagedProperty(value="#{usuarioService}")
    private UsuarioService usuarioService;
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
     
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
    
    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
        
        if(user.getPassword().equals(usuarioService.md5Password(password))==false){
            
            beanUtilidades.creaMensaje("el password es incorrecto", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if(nuevoPassword.equals(repitePassword)==false){
            
            beanUtilidades.creaMensaje("el nuevo password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        user.setPassword(usuarioService.md5Password(nuevoPassword));
        usuarioService.actualizar(user);
        
        beanUtilidades.creaMensaje("password modificado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
}


