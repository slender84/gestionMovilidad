/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;


import entities.CorreoConf;

import entities.Estado;
import entities.EstadoMovilidad;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;






@ManagedBean
@SessionScoped
public class beanUtilidades implements Serializable{

    @ManagedProperty(value="#{utilidadService}")
    private UtilidadService utilidadService;

    public UtilidadService getUtilidadService() {
        return utilidadService;
    }

    public void setUtilidadService(UtilidadService utilidadService) {
        this.utilidadService = utilidadService;
    }
    
    private ArrayList<Estado> listaEstados;
    private String estado;
    
    private ArrayList<EstadoMovilidad> listaEstadosMovilidad;
    private String estadoMovilidad;
    
   
    
    
    public beanUtilidades() {
    }
    
    
    @PostConstruct
    public void init(){
        
        
            
            
            setListaEstados((ArrayList < Estado >)utilidadService.listaEstados());
        
            
            setListaEstadosMovilidad((ArrayList < EstadoMovilidad >)utilidadService.listaEstadosMovilidad());
        
    }

    public ArrayList<Estado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(ArrayList<Estado> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<EstadoMovilidad> getListaEstadosMovilidad() {
        return listaEstadosMovilidad;
    }

    public void setListaEstadosMovilidad(ArrayList<EstadoMovilidad> listaEstadosMovilidad) {
        this.listaEstadosMovilidad = listaEstadosMovilidad;
    }

    public String getEstadoMovilidad() {
        return estadoMovilidad;
    }

    public void setEstadoMovilidad(String estadoMovilidad) {
        this.estadoMovilidad = estadoMovilidad;
    }

    
    public String creaEstado(){
        
        Estado e=new Estado(estado);
        try{
        utilidadService.crearEstado(e);
        //listaEstados.add(e);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            creaMensaje("El estado ya existe", FacesMessage.SEVERITY_ERROR);
            estado="";
            return null;
        }
        creaMensaje("estado creado o modificado", FacesMessage.SEVERITY_INFO);
        setListaEstados((ArrayList < Estado >)utilidadService.listaEstados());
        estado="";
        return null;
        
        
    }
    
    
    public String eliminaEstado(){
        
        Estado e=new Estado(estado);
        
        try{
        utilidadService.eliminaEstado(e);
        //listaEstados.remove(e);
        }catch(Exception ex){
            setListaEstados((ArrayList < Estado >)utilidadService.listaEstados());
            creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        creaMensaje("estado eliminado", FacesMessage.SEVERITY_INFO);
        estado="";
        setListaEstados((ArrayList < Estado >)utilidadService.listaEstados());
        return null;
    }
    
    
    public String creaEstadoMovilidad(){
        EstadoMovilidad e=new EstadoMovilidad(estadoMovilidad);
        try{
        utilidadService.crearEstadoMovilidad(e);
        //listaEstadosMovilidad.add(e);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            listaEstadosMovilidad=(ArrayList < EstadoMovilidad >)utilidadService.listaEstadosMovilidad();
            creaMensaje("El estado de la movilidad ya existe", FacesMessage.SEVERITY_ERROR);
            estadoMovilidad="";
            return null;
        }
        creaMensaje("estado guardado", FacesMessage.SEVERITY_INFO);
        listaEstadosMovilidad=(ArrayList < EstadoMovilidad >)utilidadService.listaEstadosMovilidad();
        estadoMovilidad="";
        return null;
    }
    
    public String eliminaEstadoMovilidad(){
        EstadoMovilidad e=new EstadoMovilidad(estadoMovilidad);
        try{
            utilidadService.eliminaEstadoMovilidad(e);
            //listaEstadosMovilidad.remove(e);
        }catch(Exception ex){
            listaEstadosMovilidad=(ArrayList < EstadoMovilidad >)utilidadService.listaEstadosMovilidad();
            creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        listaEstadosMovilidad=(ArrayList < EstadoMovilidad >)utilidadService.listaEstadosMovilidad();
        creaMensaje("estado eliminado", FacesMessage.SEVERITY_INFO);
        
        estadoMovilidad="";
        return null;
    }
    
   
    
    
        
    public CorreoConf getCorreoConf(){
        
        CorreoConf correoConf=utilidadService.getCorreoConf();
        try{
        correoConf.setPassword(Seguridad.decrypt(correoConf.getPassword()));
        }catch(Exception ex){
            
        }
        return correoConf;
    }
    
        
    
    public void setCorreoConf(CorreoConf correoConf) {
        
        utilidadService.setCorreoConf(correoConf);
        
    }
        
    
   
    
    
    
    
     public void creaMensaje(String texto,FacesMessage.Severity s){
            
            FacesContext context=FacesContext.getCurrentInstance();
            FacesMessage message=new FacesMessage(texto);
            message.setSeverity(s);
            context.addMessage(null, message);
        }
    
     
     public void request(){
         
         HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
         creaMensaje(request.getRequestURI(), FacesMessage.SEVERITY_INFO);
     }
     
     
    public String salir(){
            
            
            
            HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            return("/principal.xhtml?faces-redirect=true");
            
            
        }
    
}