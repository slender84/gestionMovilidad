package controllers;

import entities.Usuario;
import exceptions.PasswordIncorrectoException;
import exceptions.UsuarioNotFoundException;
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
public class AutenticarUsuarioController implements Serializable{

     @ManagedProperty(value="#{beanUtilidades}")
    private transient beanUtilidades beanUtilidades;
    
    @ManagedProperty(value="#{usuarioService}")  
    private transient UsuarioService usuarioService;
    
   
    
    private String login;
    private String password; 
    private Usuario user;
    
    public AutenticarUsuarioController() {
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

    @PostConstruct
    public void init(){
         HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session.getAttribute("admin")!=null){
            user=(Usuario)session.getAttribute("admin");
        }
        
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
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
    
    
    
    public String autenticarUsuario(){
             
        Usuario u;
        
             try{
             u=usuarioService.find(getLogin());
             }catch(UsuarioNotFoundException ex){
             beanUtilidades.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
              return null; 
             }
             
             
            try{ 
            usuarioService.autenticarUsuario(password,u);
            }catch(PasswordIncorrectoException ex){
               beanUtilidades.creaMensaje("password incorrecto", FacesMessage.SEVERITY_ERROR);
               return null;
            }
                
                if(u.getTipoUsuario()==1){          
            
                HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("user", u);
                return "usuario/index.xhtml?faces-redirect=true";
                }
                else{
                    HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("admin", u);
                    return "admin/index.xhtml?faces-redirect=true";
                }
        }
    
    
}
