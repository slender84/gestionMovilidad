package controllers;

import entities.InfoCuenta;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import exceptions.PasswordIncorrectoException;
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
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty(value="#{usuarioService}")  
    private UsuarioService usuarioService;
    
   
    
    private String login;
    private String password; 
    private Usuario user;
    
    private boolean checkCaptcha;
    private String volverAPantallaInicial;
    
    
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

    public boolean isCheckCaptcha() {
        return checkCaptcha;
    }

    public void setCheckCaptcha(boolean checkCaptcha) {
        this.checkCaptcha = checkCaptcha;
    }

    public String getVolverAPantallaInicial() {
        return volverAPantallaInicial;
    }

    public void setVolverAPantallaInicial(String volverAPantallaInicial) {
        this.volverAPantallaInicial = volverAPantallaInicial;
    }
    
    public String comprobarAccesoHumano(){
        
        if(volverAPantallaInicial.equals("volver"))
            return "principalUsuario.xhtml?faces-redirect=true";
       
        volverAPantallaInicial="";
        return null;
        
        
    }
    
    
    
    
    
    public void changeCaptcha(boolean cambio ){
        
        checkCaptcha=cambio;
    }
    
    
    
    public String autenticarUsuario(){
             
        Usuario u;
        
             try{
             u=usuarioService.buscarUsuario(getLogin());
             }catch(InstanceNotFoundException ex){
             beanUtilidades.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
             login="";
             password="";
              return null; 
             }
             
            InfoCuenta i=u.getInfoCuenta();
            try{ 
            usuarioService.autenticarUsuario(password,u);
            }catch(PasswordIncorrectoException ex){
               beanUtilidades.creaMensaje("password incorrecto", FacesMessage.SEVERITY_ERROR);
               
               
               i.setNumeroIntentos(i.getNumeroIntentos()+1);
               usuarioService.actualizarIntentos(u.getInfoCuenta());
               
              
               
               if(i.getNumeroIntentos()>=3){
                   
                   changeCaptcha(true);
                   //return "tresIntentos.xhtml?faces-redirect=true";
               }
               
               return null;
            }
                if(u.getInfoCuenta().getNumeroIntentos()>0){
                u.getInfoCuenta().setNumeroIntentos(0);
                changeCaptcha(false);
                usuarioService.actualizarIntentos(u.getInfoCuenta());
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
