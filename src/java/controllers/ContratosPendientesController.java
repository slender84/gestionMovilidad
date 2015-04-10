
package controllers;

import entities.Contrato;
import entities.Movilidad;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.services.MovilidadService;


@ManagedBean
@ViewScoped
public class ContratosPendientesController {

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
    private ArrayList<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    
    private Contrato selectedContrato;
    private Movilidad selectedMovilidad;
    
    
    
    
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
        
        listaContratos=(ArrayList<Contrato>)movilidadService.listarContratosPendientes();
        
        
    }
    
    
    public String verContrato(){
        
        
            
            selectedMovilidad=selectedContrato.getMovilidad();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
            
            return "gestionarContrato.xhtml?faces-redirect=true";
        
        
        
        
    }
    
    
    
    
    
}
