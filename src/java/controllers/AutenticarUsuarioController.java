package controllers;

import entities.InfoCuenta;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import exceptions.PasswordIncorrectoException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;



import model.services.UsuarioService;
import model.utils.Captcha;



@ManagedBean
@ViewScoped
public class AutenticarUsuarioController implements Serializable{

     @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{usuarioService}")  
    private UsuarioService usuarioService;
    
   
    
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
    
    Integer i;
    Integer j;
    String letra;
    
    Integer total;
    ArrayList<Object> listaCaptcha=new ArrayList<Object>();
    
    
    public AutenticarUsuarioController() {
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

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

   

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
    
    
    
    
    
    
    
    public void changeCaptcha(boolean cambio ){
        
        checkCaptcha=cambio;
    }
    
    public void generarCaptcha(){
        
        listaCaptcha=Captcha.generarCaptchaString();
        letra=listaCaptcha.get(2).toString();
        i=(Integer)listaCaptcha.get(0);
        j=(Integer)listaCaptcha.get(1);
        if(checkCaptcha==false)
          changeCaptcha(true);  
        
        
    }
    
    public boolean comprobarCapcha(){
        
        if(total!=i+j)
            return false;
        
        
        return true;
        
        
        
    }
    
    
    
    
    
    
    
    
    
    public String autenticarUsuario(){
          
        //sessionController.version();
        
        
        Usuario u;
        
             try{
             u=usuarioService.buscarUsuario(getLogin());
             }catch(InstanceNotFoundException ex){
             sessionController.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
             login="";
             password="";
             total=null;
              return null; 
             }
             if(u.getBorrado()==true){
                 sessionController.creaMensaje("login inexistente", FacesMessage.SEVERITY_ERROR);
                 login="";
                 password="";
                 total=null;
                 return null;
             }
             
             if(checkCaptcha==true){
                 
                 if(comprobarCapcha()==false){
                     sessionController.creaMensaje("el captcha es incorrecto", FacesMessage.SEVERITY_ERROR);
                     total=null;
                     generarCaptcha();
                     total=null;
                     return null;
                     
                     
                 }
                 
                 
             }
             
             
             
            InfoCuenta info=u.getInfoCuenta();
            
            
            try{ 
            usuarioService.autenticarUsuario(password,u);
            }catch(PasswordIncorrectoException ex){
              sessionController.creaMensaje("password incorrecto", FacesMessage.SEVERITY_ERROR);
               
               total=null;
               info.setNumeroIntentos(info.getNumeroIntentos()+1);
               usuarioService.actualizarIntentos(u.getInfoCuenta());
               
              
               
               if(info.getNumeroIntentos()>=3){
                  
                   generarCaptcha();
                  
                   
                   
               }
               
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






