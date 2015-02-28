package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.MensajeService;



@ManagedBean
@ViewScoped
public class MensajesEnviadosAdminController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{mensajeService}")
    private MensajeService mensajeService;
    
    private Usuario user;
    
     private boolean activaEnviado;
    private String textAreaEnviado;
    private String temaEnviado;
    
    private boolean activaTexto;
    
    private Mensaje selectedMensajeEnviado;
    private ArrayList<Mensaje> listaMensajesEnviados;
    private ArrayList<Mensaje> selectedMensajesEnviados;
    private ArrayList<Mensaje> filteredMensajesEnviados;

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }
    
    
    
    
    public MensajesEnviadosAdminController() {
    }
    
    @PostConstruct
    public void init(){
        
        user=sessionController.getUser();
        setListaMensajesEnviados((ArrayList<Mensaje>)mensajeService.mensajesEnviadosTotal("admin"));
    }

   

    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public boolean isActivaEnviado() {
        return activaEnviado;
    }

    public void setActivaEnviado(boolean activaEnviado) {
        this.activaEnviado = activaEnviado;
    }

    public String getTextAreaEnviado() {
        return textAreaEnviado;
    }

    public void setTextAreaEnviado(String textAreaEnviado) {
        this.textAreaEnviado = textAreaEnviado;
    }

    public String getTemaEnviado() {
        return temaEnviado;
    }

    public void setTemaEnviado(String temaEnviado) {
        this.temaEnviado = temaEnviado;
    }

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }

    public Mensaje getSelectedMensajeEnviado() {
        return selectedMensajeEnviado;
    }

    public void setSelectedMensajeEnviado(Mensaje selectedMensajeEnviado) {
        this.selectedMensajeEnviado = selectedMensajeEnviado;
    }

    public ArrayList<Mensaje> getListaMensajesEnviados() {
        return listaMensajesEnviados;
    }

    public void setListaMensajesEnviados(ArrayList<Mensaje> listaMensajesEnviados) {
        this.listaMensajesEnviados = listaMensajesEnviados;
    }

    public ArrayList<Mensaje> getSelectedMensajesEnviados() {
        return selectedMensajesEnviados;
    }

    public void setSelectedMensajesEnviados(ArrayList<Mensaje> selectedMensajesEnviados) {
        this.selectedMensajesEnviados = selectedMensajesEnviados;
    }

    public ArrayList<Mensaje> getFilteredMensajesEnviados() {
        return filteredMensajesEnviados;
    }

    public void setFilteredMensajesEnviados(ArrayList<Mensaje> filteredMensajesEnviados) {
        this.filteredMensajesEnviados = filteredMensajesEnviados;
    }

    
    
    
     public void leerMensajeEnviado(){
        
        activaEnviado=true;
        textAreaEnviado=selectedMensajeEnviado.getTexto();
        temaEnviado=selectedMensajeEnviado.getTema();
        
    }
    
    
    
    public void actualizarEnviados(){
        setListaMensajesEnviados((ArrayList<Mensaje>)mensajeService.mensajesEnviadosTotal("admin"));
        for(Mensaje m:selectedMensajesEnviados){
            
            if(selectedMensajeEnviado!=null&&m.getIdmensaje().equals(selectedMensajeEnviado.getIdmensaje()))
             
            activaEnviado=false;
            
        }       
         
    }
    
    
      public void cerrarMensajeEnviado(){
        
        textAreaEnviado="";
        temaEnviado="";
        activaEnviado=false;
        
    }  
              
       public String eliminarMensajesEnviados(){
        
        if(selectedMensajesEnviados.isEmpty()){
            return null;
        }
        
        for(Mensaje m:selectedMensajesEnviados){
            
            try{
            mensajeService.eliminarMensaje(m,"enviado");
            }catch(InstanceNotFoundException ex){
                
            }
        }
     
        //sessionController.creaMensaje("mensajes eliminados correctamente", FacesMessage.SEVERITY_INFO);
        actualizarEnviados();
        return null;
        
    }
       
}