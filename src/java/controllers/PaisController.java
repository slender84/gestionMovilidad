package controllers;

import entities.Pais;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.UniversidadService;



@ManagedBean
@ViewScoped
public class PaisController implements Serializable{
    
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

    
     
    private String paisStr;
    private Pais pais;
    private ArrayList<Pais> listaPaises;
    
     @PostConstruct
    public void init(){
        
        setListaPaises((ArrayList<Pais>)universidadService.listarPaises());
        
    }

    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public ArrayList<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(ArrayList<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }
   
    
    
    
    
    
    public String creaPais(){
      
      
      try{
          Pais p=new Pais(paisStr);
      universidadService.insertarPais(p);
      listaPaises.add(p);
      }catch(org.springframework.dao.DataIntegrityViolationException ex){
          
          sessionController.creaMensaje("Ya existe ese país", FacesMessage.SEVERITY_ERROR);
          paisStr="";
          return null;
      }
          
          sessionController.creaMensaje("se ha creado el país correctamente", FacesMessage.SEVERITY_INFO);
          paisStr="";
          
      return null;
      
      
  }
  
  public String eliminaPais(){
      
      try{
          listaPaises.remove(pais);
          universidadService.eliminarPais(pais);
          
          
      }catch(RuntimeException ex){
           listaPaises=(ArrayList<Pais>)universidadService.listarPaises();
          sessionController.creaMensaje("se ha producido un error eliminando el país", FacesMessage.SEVERITY_ERROR);
          return null;
      }
      
      sessionController.creaMensaje("se ha eliminado correctamente el pais", FacesMessage.SEVERITY_INFO);
     
      return null;
  }
  
    
    
    
    
    
    
    
}  