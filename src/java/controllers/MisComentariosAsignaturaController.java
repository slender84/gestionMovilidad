
package controllers;

import entities.ComentarioAsignatura;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.AsignaturaService;



@ManagedBean
@ViewScoped
public class MisComentariosAsignaturaController {

    @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
   
    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
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
        
        sessionController.creaMensaje("comentario editado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
    
    
    
    
}
