package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.services.MensajeService;
import model.services.UsuarioService;
import model.utils.beanUtilidades;


@ManagedBean
@ViewScoped
public class EscribeMensajeAdminController implements Serializable{

    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
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
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       user=(Usuario)session.getAttribute("user");
        
    }

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
        destino=usuarioService.find("admin");
        }catch(UsuarioNotFoundException ex){
            
        }
        
        Mensaje m=new Mensaje(destino, user, Calendar.getInstance().getTime(), tema, texto, "no","no","no");
       
        mensajeService.enviarMensaje(m);
        
        beanUtilidades.creaMensaje("mensaje enviado correctamente", FacesMessage.SEVERITY_INFO);
        texto="";
        tema="";
        
        //actualizarEnviados();
        return null;
    }
    
   
   
}
