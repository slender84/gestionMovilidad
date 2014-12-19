package controllers;

import entities.Usuario;
import java.io.Serializable;

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
public class CrearAdminController implements Serializable{
    
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty(value="#{usuarioService}")  
    private transient UsuarioService usuarioService;
    
    private String login;
    private String password;
    private String passwordAux;
    private String nuevoPassword;
    

    public CrearAdminController(){
        
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAux() {
        return passwordAux;
    }

    public void setPasswordAux(String passwordAux) {
        this.passwordAux = passwordAux;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }
    
    
    
    public String crearAdmin(){
        
        if(password.equals(passwordAux)==false){
            beanUtilidades.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
           return null;
        }
        
        
        Short s=2;
       
        Usuario u=new Usuario(login,usuarioService.md5Password(password),s,"tutor","tutor","tutor");
        try{
        usuarioService.insertarUsuario(u);
    }catch(org.springframework.dao.DataIntegrityViolationException ex){
        beanUtilidades.creaMensaje("Ese usuario ya existe", FacesMessage.SEVERITY_ERROR);
        return null;
    }
        
        beanUtilidades.creaMensaje("usuario creado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        return null;
                
    
}
    
    public String cambiarPasswordAdmin(){
        
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario u=(Usuario)session.getAttribute("admin");
        if(usuarioService.md5Password(password).equals(u.getPassword())==false){
            beanUtilidades.creaMensaje("password erróneo", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(nuevoPassword.equals(passwordAux)==false){
            beanUtilidades.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        u.setPassword(usuarioService.md5Password(nuevoPassword));
        usuarioService.actualizar(u);
        beanUtilidades.creaMensaje("password modificado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        login="";
        return null;
    }
    

}