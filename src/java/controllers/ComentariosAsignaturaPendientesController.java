
package controllers;

import entities.ComentarioAsignatura;
import entities.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.AsignaturaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

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
    
    private List<ComentarioAsignatura> result;
    private LazyDataModel<ComentarioAsignatura> model;
    
   
    public ComentariosAsignaturaPendientesController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public List<ComentarioAsignatura> getResult() {
        return result;
    }

    public void setResult(List<ComentarioAsignatura> result) {
        this.result = result;
    }

    public LazyDataModel<ComentarioAsignatura> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<ComentarioAsignatura> model) {
        this.model = model;
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
        
        //listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
        
        
         model=new LazyDataModel<ComentarioAsignatura>(){
          
            @Override
            public List<ComentarioAsignatura> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
    
                
                
             result=asignaturaService.listaLazyComentarioAsignatura(first,pageSize,sortField,sortOrder,filters);
            
                //setRowCount(10);
                setRowCount(asignaturaService.countComentarioAsignatura(filters));
              return result;
              
              
            }
            
            
            @Override
            public Object getRowKey(ComentarioAsignatura c){
                  
                  return c.getIdcomentario();
                  
              }
            
           
            public ComentarioAsignatura getRowData(int rowKey){
                
                for(ComentarioAsignatura c:result){
                    
                    if(c.getIdcomentario()==rowKey)
                        return c;
                    
                }
                return null;
            }
            
        
    };       
        
        
        
        
        
        
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
           //listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
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
            
            if(selectedComentarioAsignatura!=null){
            if(c.getIdcomentario().equals(selectedComentarioAsignatura.getIdcomentario()))
                selectedComentarioAsignatura.setTexto("");
            }
            try{
                
                result.remove(c);
                asignaturaService.eliminarComentario(c);
                
            }catch(RuntimeException ex){
                
                sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
                //listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosAsignaturaPendientes();
                return null;
                        
            }
            
           
            
            
        }
        
         sessionController.creaMensaje("Comentarios eliminado correctamente", FacesMessage.SEVERITY_INFO);
         return null;
        
    
    
    
    
    
}

    
}