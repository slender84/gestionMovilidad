package controllers;

import entities.InfoCuenta;
import entities.Usuario;
import java.io.Serializable;

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
public class CrearAdminController implements Serializable{
    
    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{usuarioService}")  
    private transient UsuarioService usuarioService;
    
    private String login;
    private String password;
    private String passwordAux;
    private String nuevoPassword;
    

    public CrearAdminController(){
        
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
            sessionController.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
           return null;
        }
        
        
        Short s=2;
       
        Usuario u=new Usuario(login,Seguridad.md5Password(password),s,"tutor","tutor","tutor");
        u.setInfoCuenta(new InfoCuenta(u,0));
        try{
        usuarioService.insertarUsuario(u);
    }catch(org.springframework.dao.DataIntegrityViolationException ex){
        sessionController.creaMensaje("Ese usuario ya existe", FacesMessage.SEVERITY_ERROR);
        login="";
        return null;
    }
        
        sessionController.creaMensaje("usuario creado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        login="";
        return null;
                
    
}
    
    public String cambiarPasswordAdmin(){
        
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario u=(Usuario)session.getAttribute("admin");
        if(Seguridad.md5Password(password).equals(u.getPassword())==false){
            sessionController.creaMensaje("password erróneo", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(nuevoPassword.equals(passwordAux)==false){
            sessionController.creaMensaje("el password no coincide con la confirmación", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        u.setPassword(Seguridad.md5Password(nuevoPassword));
        usuarioService.actualizarUsuario(u);
        sessionController.creaMensaje("password modificado", FacesMessage.SEVERITY_INFO);
        password="";
        passwordAux="";
        nuevoPassword="";
        login="";
        return null;
    }
    

}