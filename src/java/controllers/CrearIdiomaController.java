/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Idioma;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.services.AsignaturaService;
import model.utils.email;

@ManagedBean
@RequestScoped
public class CrearIdiomaController {

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    
    @ManagedProperty(value = "#{asignaturaService}")
    private AsignaturaService asignaturaService;

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
    
    private String idioma;

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    
    
    
    private ArrayList<Idioma>listaIdiomas;

    public ArrayList<Idioma> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaIdiomas(ArrayList<Idioma> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }
    
    
    
    
    public CrearIdiomaController() {
    }
    
    @PostConstruct
    public void init(){
        
        listaIdiomas=(ArrayList<Idioma>)asignaturaService.listarIdiomas();
        
        
        
        
        
    }
    
    public String insertarIdioma(){
        
        Idioma i=new Idioma(idioma);
        try{
            
            asignaturaService.crearIdioma(i);
            listaIdiomas.add(i);
            
            
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            
            sessionController.creaMensaje("ya existe ese idioma", FacesMessage.SEVERITY_ERROR);
            idioma="";
            return null;
        }
        
        idioma="";
        sessionController.creaMensaje("idioma creado", FacesMessage.SEVERITY_INFO);
        return null;
        
    }
    
    public String eliminarIdioma(){
        
        Idioma i=new Idioma(idioma);
        
        try{
            
            asignaturaService.eliminarIdioma(i);
            listaIdiomas.remove(i);
            
        }catch(RuntimeException ex){
            
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            idioma="";
            return null;
        }
        
        sessionController.creaMensaje("idioma eliminado correctamente", FacesMessage.SEVERITY_INFO);
        setListaIdiomas(( ArrayList<Idioma> )asignaturaService.listarIdiomas());
        idioma="";
        return null;
        
    }
    
    
    
    
    
    
    
    
}
