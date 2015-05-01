
package controllers;

import entities.ComentarioAsignatura;
import entities.Cronica;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.services.UniversidadService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;




@ManagedBean
@ViewScoped
public class CronicasController implements Serializable{

    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   
    
    
    
    
    public CronicasController() {
    }
    
    @PostConstruct
    public void init(){
        
        HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request.getRequestURI().equals(request.getContextPath()+"/admin/verCronicas.xhtml")){
            
           //listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas(); 
            
             model=new LazyDataModel<Cronica>(){
          
            @Override
            public List<Cronica> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
    
                
                
             result=universidadService.listaLazyCronica(first,pageSize,sortField,sortOrder,filters);
            
                //setRowCount(10);
                setRowCount(universidadService.countCronica(filters));
              return result;
              
              
            }
            
            
            @Override
            public Object getRowKey(Cronica c){
                  
                  return c.getIdcronica();
                  
              }
            
           
            public Cronica getRowData(int rowKey){
                
                for(Cronica c:result){
                    
                    if(c.getIdcronica()==rowKey)
                        return c;
                    
                }
                return null;
            }
            
        
    };       
            
            
            
            
            
        }
        
        
        
        
        
    }
    
    LazyDataModel<Cronica> model;
    List<Cronica> result;
    
    
    
    private Cronica selectedCronica;
    private ArrayList<Cronica> listaCronicas;
    private ArrayList<Cronica> filteredCronicas;
    private ArrayList<Cronica> selectedCronicas=new ArrayList<>();
    
    private String nuevoEstado;
    
    private boolean panelTexto;
    
    private String pais;
    private String universidad;
    private ArrayList<Universidad>listaUniversidad;
    
    private boolean checkUni;
    private boolean checkPais;

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

    public ArrayList<Universidad> getListaUniversidad() {
        return listaUniversidad;
    }

    public void setListaUniversidad(ArrayList<Universidad> listaUniversidad) {
        this.listaUniversidad = listaUniversidad;
    }

    public boolean isCheckUni() {
        return checkUni;
    }

    public void setCheckUni(boolean checkUni) {
        this.checkUni = checkUni;
    }

    public boolean isCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
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

    public LazyDataModel<Cronica> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Cronica> model) {
        this.model = model;
    }

    public List<Cronica> getResult() {
        return result;
    }

    public void setResult(List<Cronica> result) {
        this.result = result;
    }

    
    
    
    
    
    
    
    
    public String modificarEstado(){
        
        
        if(nuevoEstado.equals(selectedCronica.getEstado())){
            
           return null;
           
        }
        
        selectedCronica.setEstado(nuevoEstado);
        try{
            universidadService.editarCronica(selectedCronica);
        }catch(InstanceNotFoundException ex){
            
            sessionController.creaMensaje("El comentario no existe", FacesMessage.SEVERITY_ERROR);
            panelTexto=false;
            //listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas();
            return null;
        }
        
        sessionController.creaMensaje("Comentario modificado correctamente", FacesMessage.SEVERITY_INFO);
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
                
                sessionController.creaMensaje("No existe ese comentario", FacesMessage.SEVERITY_ERROR);
                //listaCronicas=(ArrayList<Cronica>)universidadService.listaCronicas();
                panelTexto=false;
                return null;
            }
            //listaCronicas.remove(c);
            result.remove(c);
            if(selectedCronica!=null&&selectedCronica.equals(c))
                panelTexto=false;
        }
            
        
        sessionController.creaMensaje("Comentarios eliminados", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    public void onChangePais(){
        
        checkPais=true;
        checkUni=false;
        universidad="";
        listaUniversidad=(ArrayList < Universidad >)universidadService.listarPorPais(pais);
        
    }
    
    public void onChangeUni(){
        
        checkUni=true;
        
    }
    
    public String buscar(){
        try{
        listaCronicas=(ArrayList<Cronica>)universidadService.listarCronicasPublicas(universidad);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("No existe esa universidad", FacesMessage.SEVERITY_ERROR);
            universidad="";
            return null;
        }
        
        return null;
    }
    
    
}
