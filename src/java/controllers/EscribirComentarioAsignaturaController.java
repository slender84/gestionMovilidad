
package controllers;

import entities.Asignatura;
import entities.ComentarioAsignatura;
import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.services.AsignaturaService;
import model.services.MensajeService;
import model.services.UsuarioService;





@ManagedBean
@ViewScoped
public class EscribirComentarioAsignaturaController {

    
    @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
    
    @ManagedProperty(value ="#{mensajeService}")
    private MensajeService mensajeService;
    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value ="#{usuarioService}")
    private UsuarioService usuarioService;
    
    private ArrayList<Asignatura> listaAsignaturas;
    private Movilidad selectedMovilidad;
    private Asignatura selectedAsignatura;
    private String texto;
    
    private boolean panelTexto;
    private boolean btnActivado=true;
     
    
    
    public EscribirComentarioAsignaturaController() {
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
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

    
    
    
    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isPanelTexto() {
        return panelTexto;
    }

    public void setPanelTexto(boolean panelTexto) {
        this.panelTexto = panelTexto;
    }

    public boolean isBtnActivado() {
        return btnActivado;
    }

    public void setBtnActivado(boolean btnActivado) {
        this.btnActivado = btnActivado;
    }

    public Asignatura getSelectedAsignatura() {
        return selectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura selectedAsignatura) {
        this.selectedAsignatura = selectedAsignatura;
    }

    

    
    
    
    
    
    
    
    
    @PostConstruct
    public void init(){
        
        
        ExternalContext context=FacesContext.getCurrentInstance().getExternalContext();
        
        if(context.getSessionMap().containsKey("movilidad")){
            selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
           context.getSessionMap().remove("movilidad");
            
           listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.asignaturasMovilidad(selectedMovilidad);
            
        }else{
            
            try{
                context.redirect(context.getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
                
                
            }catch(IOException ex){
                
                
            }
            
        }
    }
        
        
        public void escribirComentario(){
            
            panelTexto=true;
            texto="";
            btnActivado=true;
            
            
        }
        
        
        
        
        
        
        public String enviarComentario(){
            
            ComentarioAsignatura cm=new ComentarioAsignatura();
            cm.setTexto(texto);
            cm.setEstado("pendiente");
            cm.setFecha(Calendar.getInstance().getTime());
            cm.setUsuario(sessionController.getUser());
            cm.setAsignatura(selectedAsignatura);
            try{
                
                asignaturaService.insertarComentario(cm);
                
            }catch(RuntimeException ex){
                
                sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
                return null;
                
            }
            
            sessionController.creaMensaje("comentario creado correctamente", FacesMessage.SEVERITY_INFO);
            btnActivado=false;
            texto="";
           Usuario admin=null;
            
            try{
                
               admin=usuarioService.buscarUsuario("admin");
                
            }catch(InstanceNotFoundException ex){
                
            }
            
            String textoMensaje="el usuario "+sessionController.getUser().getLogin()+" ha escrito un comentario \n asignatura: "+selectedAsignatura.getNombreAsignatura()+"\n Universidad: "+selectedAsignatura.getId().getNombreUniversidad()+"\n Curso: "+selectedAsignatura.getCurso();
            
            
            Mensaje m=new Mensaje(admin, sessionController.getUser(), Calendar.getInstance().getTime(),"Asignatura comentada",textoMensaje,false ,true, false);
            mensajeService.enviarMensaje(m);
            
            return null;
            
        }
        
        public void cerrarPanelTexto(){
            
            panelTexto=false;
            texto="";
           btnActivado=true;
                   
        
    }
    
    
    
}
