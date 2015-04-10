
package controllers;

import entities.ComentarioAsignatura;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.AsignaturaService;

@ManagedBean
@ViewScoped
public class ComentariosAsignaturaPendientesController {

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
    
    private ArrayList<ComentarioAsignatura> listaComentariosAsignatura;
    private ArrayList<ComentarioAsignatura> selectedComentariosAsignatura;
    private ArrayList<ComentarioAsignatura> filteredComentarioAsignatura;
    
    private ComentarioAsignatura selectedComentarioAsignatura;
    
    private String nuevoEstado;
   
    public ComentariosAsignaturaPendientesController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

   

    public ArrayList<ComentarioAsignatura> getListaComentariosAsignatura() {
        return listaComentariosAsignatura;
    }

    public void setListaComentariosAsignatura(ArrayList<ComentarioAsignatura> listaComentariosAsignatura) {
        this.listaComentariosAsignatura = listaComentariosAsignatura;
    }

    public ArrayList<ComentarioAsignatura> getSelectedComentariosAsignatura() {
        return selectedComentariosAsignatura;
    }

    public void setSelectedComentariosAsignatura(ArrayList<ComentarioAsignatura> selectedComentariosAsignatura) {
        this.selectedComentariosAsignatura = selectedComentariosAsignatura;
    }

    public ArrayList<ComentarioAsignatura> getFilteredComentarioAsignatura() {
        return filteredComentarioAsignatura;
    }

    public void setFilteredComentarioAsignatura(ArrayList<ComentarioAsignatura> filteredComentarioAsignatura) {
        this.filteredComentarioAsignatura = filteredComentarioAsignatura;
    }

    public ComentarioAsignatura getSelectedComentarioAsignatura() {
        return selectedComentarioAsignatura;
    }

    public void setSelectedComentarioAsignatura(ComentarioAsignatura selectedComentarioAsignatura) {
        this.selectedComentarioAsignatura = selectedComentarioAsignatura;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
    
    
    
    
    @PostConstruct
    public void init(){
        
        listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
        
    }
    
    
    
    public void leerComentarioAsignatura(){
        
        
        
        
    }
    
    
    public String cambiarEstado(){
        if(selectedComentarioAsignatura==null)
            return null;
        
        if(selectedComentarioAsignatura.getEstado().equals("pendiente")){
            
            selectedComentarioAsignatura.setEstado("aceptado");
        }else{
            
            selectedComentarioAsignatura.setEstado("pendiente");
            
        }
        
        
       try{
           
           asignaturaService.editarComentario(selectedComentarioAsignatura);
       }catch(RuntimeException ex){
           
           sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
           listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
           return null;
           
        }
       
       
        sessionController.creaMensaje("Comentario modificado", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
    
    public String eliminarComentarios(){
        
        if(selectedComentariosAsignatura==null)
            return null;
        
        
        if(selectedComentariosAsignatura.isEmpty())
            return null;
        
        for(ComentarioAsignatura c:selectedComentariosAsignatura){
            
            if(c.getIdcomentario().equals(selectedComentarioAsignatura.getIdcomentario()))
                selectedComentarioAsignatura.setTexto("");
            try{
                
                listaComentariosAsignatura.remove(c);
                asignaturaService.eliminarComentario(c);
                
            }catch(RuntimeException ex){
                
                sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
                listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
                return null;
                        
            }
            
           
            
            
        }
        
         sessionController.creaMensaje("Comentarios eliminado correctamente", FacesMessage.SEVERITY_INFO);
         return null;
        
    
    
    
    
    
}

    
}