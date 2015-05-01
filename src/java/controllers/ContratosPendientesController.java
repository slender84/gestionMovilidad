
package controllers;

import entities.Contrato;
import entities.Cursoacademico;
import entities.Movilidad;
import entities.Universidad;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.services.MovilidadService;
import model.services.UniversidadService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


@ManagedBean
@ViewScoped
public class ContratosPendientesController {

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    
    private ArrayList<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    
    private Contrato selectedContrato;
    private Movilidad selectedMovilidad;
    
    private ArrayList<Cursoacademico>listaCursoAcademico;
    private ArrayList<Universidad> listaUniversidades;
    
    private boolean checkPais;
    
    private List<Contrato> result;
    private LazyDataModel<Contrato> model;
    
    public ContratosPendientesController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public ArrayList<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(ArrayList<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }

    public boolean getCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
    }

    public List<Contrato> getResult() {
        return result;
    }

    public void setResult(List<Contrato> result) {
        this.result = result;
    }

    public LazyDataModel<Contrato> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Contrato> model) {
        this.model = model;
    }
    
    

    public ArrayList<Cursoacademico> getListaCursoAcademico() {
        return listaCursoAcademico;
    }

    public void setListaCursoAcademico(ArrayList<Cursoacademico> listaCursoAcademico) {
        this.listaCursoAcademico = listaCursoAcademico;
    }
    
    
    

    public ArrayList<Contrato> getListaContratos() {
        return listaContratos;
    }

    public void setListaContratos(ArrayList<Contrato> listaContratos) {
        this.listaContratos = listaContratos;
    }

    public ArrayList<Contrato> getFilteredContratos() {
        return filteredContratos;
    }

    public void setFilteredContratos(ArrayList<Contrato> filteredContratos) {
        this.filteredContratos = filteredContratos;
    }

    public Contrato getSelectedContrato() {
        return selectedContrato;
    }

    public void setSelectedContrato(Contrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }
    
    
    
    
    
    
    
    
    @PostConstruct
    public void init(){
        
        if(sessionController.correoConf.getPageToPage()==true){
            
       listaCursoAcademico=(ArrayList<Cursoacademico>)universidadService.listarCursosAcademicos();
            
         model=new LazyDataModel<Contrato>(){
          
            @Override
            public List<Contrato> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
    
             result=movilidadService.listaLazyContrato(first,pageSize,sortField,sortOrder,filters);
            
                //setRowCount(10);
                setRowCount(movilidadService.countContrato(filters));
              return result;
              
              
            }
            
            
            @Override
            public Object getRowKey(Contrato contrato){
                  
                  return contrato.getIdContrato();
                  
              }
            
            public Contrato getRowData(int rowKey){
                
                for(Contrato c:result){
                    
                    if(c.getIdContrato()==rowKey)
                        return c;
                    
                }
                return null;
            }
            
        
    };           

           
            
        }else{
        
        
    boolean todosNulos=true;
        Map<String,String> m=new HashMap<String,String>();
        
        if(sessionController.getFiltroContratoEstado().equals("todos")==false){
            
          m.put("estado", sessionController.getFiltroContratoEstado());
        todosNulos=false;
            
        }
        
        if(sessionController.getFiltroContratoCursoAcademico().equals("todos")==false){
          m.put("curso", sessionController.getFiltroContratoCursoAcademico());
           
        todosNulos=false;
        }
         
        if(sessionController.getFiltroContratoPais().equals("todos")==false){
            
          m.put("pais", sessionController.getFiltroContratoPais());
          todosNulos=false; 
          checkPais=true;
           listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(sessionController.getFiltroContratoPais());
             if(sessionController.getFiltroContratoUniversidad().equals("todos")==false){
                 m.put("universidad", sessionController.getFiltroContratoUniversidad());
                 
                
                 
             }
                }
     
        
        
        if(todosNulos==true){
           
            if(sessionController.getCorreoConf().getCargarTodosContratos()==true){
             
             listaContratos=(ArrayList<Contrato>)movilidadService.listarTodosContratos();
             
         } 
       
       }else{
            
            listaContratos=(ArrayList<Contrato>)movilidadService.listarContratosPorFiltro(m);
        }
       
        }
    }
    
    
    public String verContrato(){
        
        
            
            selectedMovilidad=selectedContrato.getMovilidad();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
            
            return "gestionarContrato.xhtml?faces-redirect=true";
        
        
        
        
    }
    
    
    public void limpiarFiltros(){
        
        
        sessionController.limpiarFiltrosContrato();
        checkPais=false;
        
        
    }
    
    
    public void onChangePais(){
        
        
        
        if(sessionController.getFiltroContratoPais().equals("todos")){
            checkPais=false;
            sessionController.setFiltroContratoUniversidad("todos");
        }
        else{
            if(checkPais==false)
            checkPais=true;
            
          listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(sessionController.getFiltroContratoPais());  
        }
        
    }
    
    public String buscarContratos(){
        
        boolean todosNulos=false;
        Map<String,String> m=new HashMap<String,String>();
         
       
        
        if(sessionController.getFiltroContratoEstado().equals("todos")==false){
            
          m.put("estado", sessionController.getFiltroContratoEstado());
        todosNulos=false;
            
        }
        
        if(sessionController.getFiltroContratoCursoAcademico().equals("todos")==false){
          m.put("curso", sessionController.getFiltroContratoCursoAcademico());
           
        todosNulos=false;
        }
        
        if(sessionController.getFiltroContratoPais().equals("todos")==false){
            
          m.put("pais", sessionController.getFiltroContratoPais());
          todosNulos=false;  
             if(sessionController.getFiltroContratoUniversidad().equals("todos")==false){
                 m.put("universidad", sessionController.getFiltroContratoUniversidad());
                 
             }
                }
     
        if(todosNulos==true){
           
            listaContratos=(ArrayList<Contrato>)movilidadService.listarTodosContratos();
        return null;
                }
        
        listaContratos=(ArrayList<Contrato>)movilidadService.listarContratosPorFiltro(m);
        return null;
        
        
    }
    
    
    
    
}
