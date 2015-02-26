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
    
   @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }
    
    private String login;
    private String password; 
    private Usuario user;
    
    private boolean checkCaptcha;
    private String volverAPantallaInicial;
    
    //private String zonaHoraria;
    //private boolean mostrar=true;
    
    
    
    
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
         
        
         //HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //if(session.getAttribute("user")!=null)
        //user=(Usuario)session.getAttribute("user");
        
        
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

   // public String getZonaHoraria() {
   //     return zonaHoraria;
    //}

    //public void setZonaHoraria(String zonaHoraria) {
      //  this.zonaHoraria = zonaHoraria;
    //}

   // public boolean isMostrar() {
     //   return mostrar;
    //}

    //public void setMostrar(boolean mostrar) {
      //  this.mostrar = mostrar;
    //}
    
    
   // public void timezoneChange(){
     
     //   mostrar=false;
        
           
    //}
    
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
          
        //String version = FacesContext.class.getPackage().getImplementationVersion();
        //System.out.println(version);
        
        
        Usuario u;
        
             try{
             u=usuarioService.buscarUsuario(getLogin());
             }catch(InstanceNotFoundException ex){
             beanUtilidades.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
             login="";
             password="";
              return null; 
             }
             if(u.getBorrado()==true){
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
               login="";
               return null;
               
            }
                if(u.getInfoCuenta().getNumeroIntentos()>0){
                u.getInfoCuenta().setNumeroIntentos(0);
                changeCaptcha(false);
                usuarioService.actualizarIntentos(u.getInfoCuenta());
                }
                
                login="";
                
                sessionController.setUser(u);
                
                if(u.getTipoUsuario()==1){          
            
                HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("user", u);
               user=u;
                return "usuario/index.xhtml?faces-redirect=true";
                }
                else{
                    HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("admin", u);
                    user=u;
                    return "admin/index.xhtml?faces-redirect=true";
                }
        }
    
    
    
    
    
}






