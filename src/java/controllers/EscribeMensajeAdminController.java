package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.services.MensajeService;
import model.services.UsuarioService;



@ManagedBean
@RequestScoped
public class EscribeMensajeAdminController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{mensajeService}")
    private MensajeService mensajeService;
    
    @ManagedProperty(value="#{usuarioService}")
    private UsuarioService usuarioService;
    
    
    private Usuario user;
    
    private String tema;
    private String texto;
    
   
    
    
    
    
    
    
    public EscribeMensajeAdminController() {
    }
    
    @PostConstruct
    public void init(){
       
       user=sessionController.getUser();
        
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    

    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    
    

   
    public String enviarMensajeCoordinador(){
        
        Usuario destino=null;
        try{
        destino=usuarioService.buscarUsuario("admin");
        }catch(InstanceNotFoundException ex){
            
        }
        
        Mensaje m=new Mensaje(destino, user, Calendar.getInstance().getTime(), tema, texto, false,false,false);
       
        mensajeService.enviarMensaje(m);
        
        sessionController.creaMensaje("mensaje enviado correctamente", FacesMessage.SEVERITY_INFO);
        texto="";
        tema="";
        
        //actualizarEnviados();
        return null;
    }
    
   
   
}
