

package controllers;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.services.EquivalenciaService;
import model.utils.beanUtilidades;


@ManagedBean
@ViewScoped
public class VerMisContratosController implements Serializable{

    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty(value="#{equivalenciaService}")
    private EquivalenciaService equivalenciaService;

    
    
   private Usuario user;
    
    private HttpSession session;
    
    
    private boolean nuevo;
    private boolean visibleContratos;
    private boolean verAceptado;
    
    
    
    private ArrayList<Contrato> listaContratos;
    private ArrayList<Contrato> filteredContratos;
    private Contrato selectedContrato;
    
    private Movilidad selectedMovilidad;
  
    
    public VerMisContratosController() {
    }
    
    @PostConstruct
    public void init(){
        
       
       session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        user=(Usuario)session.getAttribute("user");
         
        }

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
    }
       
     
    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }
    
    
   
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   

    public boolean isNuevo() {
        return nuevo;
    }

   
    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean isVisibleContratos() {
        return visibleContratos;
    }

    public void setVisibleContratos(boolean visibleContratos) {
        this.visibleContratos = visibleContratos;
    }

    public boolean isVerAceptado() {
        return verAceptado;
    }

    public void setVerAceptado(boolean verAceptado) {
        this.verAceptado = verAceptado;
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

    
    
    
    public String eliminarContrato(){
        try{
        selectedContrato=equivalenciaService.findContrato(selectedContrato.getIdContrato());
        }catch(ContratoNotFoundException ex){
            beanUtilidades.creaMensaje("contrato no encontrado", FacesMessage.SEVERITY_ERROR);
            verContratos();
            return null;
            
        }
        ArrayList<Equivalencia> listaCopia=new ArrayList<>(selectedContrato.getEquivalencias());
        
           
           selectedContrato.setEquivalencias(null);
           equivalenciaService.eliminaContrato(selectedContrato);
            
            for(Equivalencia e:listaCopia){
              
                try{
                equivalenciaService.eliminarEquivalencia(e);
                }catch(org.springframework.dao.DataIntegrityViolationException ex){
                    
                }
            }
            
          
        beanUtilidades.creaMensaje("contrato eliminado correctamente", FacesMessage.SEVERITY_INFO);
        verContratos();
        
        selectedContrato=null;
        
        return null;
    }
    
    
    public void verContratos(){
        verAceptado=true;
        visibleContratos=true;
        
        
        listaContratos=(ArrayList<Contrato>)equivalenciaService.listaContratos(selectedMovilidad);
        
        if(listaContratos.isEmpty()){
        nuevo=true;
    }else
            for (Contrato c: listaContratos){
                
        if(c.getEstado().equalsIgnoreCase("pendiente")||c.getEstado().equalsIgnoreCase("rechazado")||c.getEstado().equalsIgnoreCase("aceptado")){
            nuevo=false;
            break;
        }
    }
        
    }
    
     public void cerrarContratos(){
        
        visibleContratos=false;
    }
    
     
     public String revisarContrato(){
         
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       
        
        
        
        for(Contrato c:listaContratos){
            
            if(selectedContrato.getFecha().compareTo(c.getFecha())==-1){
                
             return ("verContrato.xhtml?faces-redirect=true");
            }
            
            
        }
        
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ultimo", "ultimo");
          return ("verContrato.xhtml?faces-redirect=true");
     }
     
    
    public String crearContrato(){
        System.out.println(selectedMovilidad.getUniversidad().getNombre());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        return ("elaborarContrato.xhtml?faces-redirect=true");
        
        
    }
    
    public String editarContrato(){
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
       
        
        return ("elaborarContratoEditado.xhtml?faces-redirect=true");
        
    }
    
    
    
    
    
    
    
    
    
   
    
    
    
  
    
    
}
