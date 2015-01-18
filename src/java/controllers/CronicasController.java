
package controllers;

import entities.Cronica;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import model.services.UniversidadService;
import model.utils.beanUtilidades;



@ManagedBean
@ViewScoped
public class CronicasController implements Serializable{

    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
    }
    
    
    
    
    public CronicasController() {
    }
    
    @PostConstruct
    public void init(){
        
        listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas();
        
        
    }
    
    private Cronica selectedCronica;
    private ArrayList<Cronica> listaCronicas;
    private ArrayList<Cronica> filteredCronicas;
    private ArrayList<Cronica> selectedCronicas;
    
    private String nuevoEstado;
    
    private boolean panelTexto;
    
    private String pais;
    private String universidad;

    public Cronica getSelectedCronica() {
        return selectedCronica;
    }

    public void setSelectedCronica(Cronica selectedCronica) {
        this.selectedCronica = selectedCronica;
    }

    public ArrayList<Cronica> getListaCronicas() {
        return listaCronicas;
    }

    public void setListaCronicas(ArrayList<Cronica> listaCronicas) {
        this.listaCronicas = listaCronicas;
    }

    public ArrayList<Cronica> getFilteredCronicas() {
        return filteredCronicas;
    }

    public void setFilteredCronicas(ArrayList<Cronica> filteredCronicas) {
        this.filteredCronicas = filteredCronicas;
    }

    public ArrayList<Cronica> getSelectedCronicas() {
        return selectedCronicas;
    }

    public void setSelectedCronicas(ArrayList<Cronica> selectedCronicas) {
        this.selectedCronicas = selectedCronicas;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
    
    

    public boolean isPanelTexto() {
        return panelTexto;
    }

    public void setPanelTexto(boolean panelTexto) {
        this.panelTexto = panelTexto;
    }

    
    

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }
    
    
    public void verCronica(){
        
        panelTexto=true;
        
    }
    
    public void cerrarEdicion(){
        panelTexto=false;
    }
    
    public String modificarEstado(){
        
        
        if(nuevoEstado.equals(selectedCronica.getEstado())){
            
           return null;
           
        }
        
        selectedCronica.setEstado(nuevoEstado);
        try{
            universidadService.editarCronica(selectedCronica);
        }catch(InstanceNotFoundException ex){
            
            beanUtilidades.creaMensaje("El comentario no existe", FacesMessage.SEVERITY_ERROR);
            panelTexto=false;
            listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas();
            return null;
        }
        
        beanUtilidades.creaMensaje("Comentario modificado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
        
    }
    
    public String eliminarCronicas(){
        
        if(selectedCronicas.isEmpty()){
            return null;
        }
        
        for(Cronica c:selectedCronicas){
            
            try{
                
                universidadService.eliminarCronica(c);
            }catch(InstanceNotFoundException ex){
                
                beanUtilidades.creaMensaje("No existe ese comentario", FacesMessage.SEVERITY_ERROR);
                listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas();
                panelTexto=false;
                return null;
            }
            listaCronicas.remove(c);
            if(selectedCronica!=null&&selectedCronica.equals(c))
                panelTexto=false;
        }
            
        
        beanUtilidades.creaMensaje("Comentarios eliminados", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
}
