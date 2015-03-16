
package controllers;

import entities.ComentarioAsignatura;
import entities.Mensaje;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.AsignaturaService;
import model.services.MensajeService;
import model.services.UsuarioService;



@ManagedBean
@ViewScoped
public class MisComentariosAsignaturaController {

    @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
   
    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value ="#{usuarioService}")
    private UsuarioService usuarioService;
    
    @ManagedProperty(value ="#{mensajeService}")
    private MensajeService mensajeService;
    
    private ArrayList<ComentarioAsignatura> listaComentarios;
    private ComentarioAsignatura selectedComentario;
    private boolean panelEdicion;
    
    
    
    public MisComentariosAsignaturaController() {
    }
    
    
    @PostConstruct
    public void init(){
        
        listaComentarios=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignatura(sessionController.getUser());
        
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public ArrayList<ComentarioAsignatura> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(ArrayList<ComentarioAsignatura> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public ComentarioAsignatura getSelectedComentario() {
        return selectedComentario;
    }

    public void setSelectedComentario(ComentarioAsignatura selectedComentario) {
        this.selectedComentario = selectedComentario;
    }

    public boolean isPanelEdicion() {
        return panelEdicion;
    }

    public void setPanelEdicion(boolean panelEdicion) {
        this.panelEdicion = panelEdicion;
    }

    
    
    
    
    public void verComentario(){
        
        panelEdicion=true;
        
        
    }
    
    
    public void cerrarPanelEdicion(){
        
        panelEdicion=false;
        
    }
    
    
    public String editarComentario(){
        
        selectedComentario.setFecha(Calendar.getInstance().getTime());
        
        
        try{
            
            asignaturaService.editarComentario(selectedComentario);
            
        }catch(RuntimeException ex){
            
            sessionController.creaMensaje("no existe el comentario", FacesMessage.SEVERITY_ERROR);
            listaComentarios=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignatura(sessionController.getUser());
        }
         Usuario admin=null;
        try{
                
               admin=usuarioService.buscarUsuario("admin");
                
            }catch(InstanceNotFoundException ex){
                
            }
            
            String textoMensaje="el usuario "+sessionController.getUser().getLogin()+" ha escrito un comentario \n asignatura: "+selectedComentario.getAsignatura().getNombreAsignatura()+"\n Universidad: "+selectedComentario.getAsignatura().getUniversidad().getNombre()+"\n Curso: "+selectedComentario.getAsignatura().getCurso();
            
            Mensaje m=new Mensaje(admin, sessionController.getUser(), Calendar.getInstance().getTime(),"Comentario de asignatura editado",textoMensaje,false ,true, false);
            mensajeService.enviarMensaje(m);
        sessionController.creaMensaje("comentario editado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
    
    
    
    
}
