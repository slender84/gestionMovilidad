package controllers;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.services.EquivalenciaService;
import model.services.MovilidadService;



@ManagedBean
@ViewScoped
public class VerContratosController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{equivalenciaService}")
    private EquivalenciaService equivalenciaService;

    @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
   private Usuario user;
    
   
    
    private ExternalContext context;
            
    
    
    
    
    private ArrayList<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    private ArrayList<Contrato> selectedContratos;
    private Contrato selectedContrato;
    
    private Movilidad selectedMovilidad;
  
    
    
    
    
    public VerContratosController() {
    }
    
    @PostConstruct
    public void init(){
        
       user=sessionController.getUser();
       context=FacesContext.getCurrentInstance().getExternalContext();
       if(context.getSessionMap().containsKey("movilidad")==true){
           
           selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
           //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("movilidad");
           //context.getSessionMap().remove("movilidad"); usar la version larga si falla
           
           try{
               selectedMovilidad=movilidadService.buscarMovilidad(selectedMovilidad.getCodMovilidad());
           }catch(InstanceNotFoundException ex){
               try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
           }
           
           listaContratos=(ArrayList < Contrato >)equivalenciaService.listarContratos(selectedMovilidad);
           
       }
       else{
           try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
       }
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
    
    
    
    
    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    

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

    public ArrayList<Contrato> getSelectedContratos() {
        return selectedContratos;
    }

    public void setSelectedContratos(ArrayList<Contrato> selectedContratos) {
        this.selectedContratos = selectedContratos;
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

    public String eliminarContratos(){
        
        if(selectedContratos.isEmpty()){
            return null;
        }
        ArrayList<Equivalencia> listaCopia;
        
        for(Contrato c:selectedContratos){
         
            try{
            c=equivalenciaService.buscarContrato(c.getIdContrato());
            }catch(InstanceNotFoundException ex){
             listaContratos=(ArrayList<Contrato>)equivalenciaService.listarContratos(selectedMovilidad);
              sessionController.creaMensaje("contrato no encontrado", FacesMessage.SEVERITY_ERROR);
             return null;
            }
            listaCopia=new ArrayList<>(c.getEquivalencias());
            
            c.setEquivalencias(null);
            equivalenciaService.eliminarContrato(c);
            
            listaContratos.remove(c);
            
            for(Equivalencia e:listaCopia){
            
            try{
            equivalenciaService.eliminarEquivalencia(e);
            }catch(org.springframework.dao.DataIntegrityViolationException ex){
                
            }
        }
          
            

           }
        
        
        
        sessionController.creaMensaje("contrato eliminado correctamente", FacesMessage.SEVERITY_INFO);
        //listaContratos=(ArrayList<Contrato>)equivalenciaService.listarContratos(selectedMovilidad);
        selectedContratos=null;
        
        return null;
    }
    
    public String seleccionarContratoAdmin(){
        
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContratos.get(0));
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
       return "gestionarContrato.xhtml?faces-redirect=true";
        
    }
    
    
    public String compararContratos(){
        
        if(selectedContratos.isEmpty()!=selectedContratos.size()>2){
            sessionController.creaMensaje("hay que elegir uno o dos contratos", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if(selectedContratos.size()==1){
            
            
           return seleccionarContratoAdmin();
        }else{
            Contrato contratoComparado; 
            if(selectedContratos.get(0).getFecha().compareTo(selectedContratos.get(1).getFecha())<0){
                
            contratoComparado=selectedContratos.get(0);
            selectedContrato=selectedContratos.get(1);
            
                
            }else{
               contratoComparado=selectedContratos.get(1);
            selectedContrato=selectedContratos.get(0);
            }
            
            
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contratoComparado",contratoComparado);
       return "compararContratos.xhtml?faces-redirect=true";
        }
        }  
    
    
    
}

    
    


