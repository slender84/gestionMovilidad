package controllers;

import entities.Contrato;
import entities.Cursoacademico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.MovilidadService;
import model.services.UniversidadService;
import model.utils.email;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class ContratosFilter2Controller implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;

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

    public LazyDataModel<Contrato> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Contrato> model) {
        this.model = model;
    }

    public List<Contrato> getResult() {
        return result;
    }

    public void setResult(List<Contrato> result) {
        this.result = result;
    }

    public ArrayList<Cursoacademico> getListaCursoAcademico() {
        return listaCursoAcademico;
    }

    public void setListaCursoAcademico(ArrayList<Cursoacademico> listaCursoAcademico) {
        this.listaCursoAcademico = listaCursoAcademico;
    }
    
 
   private LazyDataModel<Contrato> model;
   private List<Contrato> result;
   private ArrayList<Cursoacademico> listaCursoAcademico;
   private ArrayList<Contrato> filteredContratos;

    public ArrayList<Contrato> getFilteredContratos() {
        return filteredContratos;
    }

    public void setFilteredContratos(ArrayList<Contrato> filteredContratos) {
        this.filteredContratos = filteredContratos;
    }
   
   
   
    @PostConstruct
    public void init(){
        
        listaCursoAcademico=(ArrayList<Cursoacademico>)universidadService.listarCursosAcademicos();
        
        if(sessionController.correoConf.getPageToPage()==true){
            
       
            
         model=new LazyDataModel<Contrato>(){
          
            @Override
            public List<Contrato> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                System.out.println("en load");
                 try{
                    
                    //email.enviarEmailYo(sessionController.correoConf, "lazy filter controller","en load");
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
                
                
             result=movilidadService.listaLazyContratoFilter2(first,pageSize,sortField,sortOrder,filters,sessionController.correoConf);
            
                //setRowCount(10);
                setRowCount(movilidadService.countContratoFilter2(filters));
                
                if(filters.containsKey("movilidad.usuario.login")){
                    System.out.println("en result hay "+result.size());
                    String mensaje="en result hay "+result.size();
                    try{
                    
                    //email.enviarEmailYo(sessionController.correoConf, "count en controller",mensaje);
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
                }
                
                
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
        
        
        
        
        
    }
    
    
    }
    
}


