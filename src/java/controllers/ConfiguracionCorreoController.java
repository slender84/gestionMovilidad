
package controllers;



import entities.Configuracion;
import entities.Direccion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.services.UsuarioService;





@ManagedBean
@RequestScoped
public class ConfiguracionCorreoController implements Serializable{

    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty (value = "#{usuarioService}")
    private UsuarioService usuarioService;
    
    private Configuracion correoConf;
    private String nuevoPassword;
    
    private ArrayList<Direccion> listaDireccionesCopia;
    private String direccionCopia;
    
    private Direccion selectedDireccion;
    
    public ConfiguracionCorreoController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public String getNuevoPassword() {
        return nuevoPassword;
    }

    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    public ArrayList<Direccion> getListaDireccionesCopia() {
        return listaDireccionesCopia;
    }

    public void setListaDireccionesCopia(ArrayList<Direccion> listaDireccionesCopia) {
        this.listaDireccionesCopia = listaDireccionesCopia;
    }

    public String getDireccionCopia() {
        return direccionCopia;
    }

    public void setDireccionCopia(String direccionCopia) {
        this.direccionCopia = direccionCopia;
    }

    public Direccion getSelectedDireccion() {
        return selectedDireccion;
    }

    public void setSelectedDireccion(Direccion selectedDireccion) {
        this.selectedDireccion = selectedDireccion;
    }
    
    

   

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    

    public Configuracion getCorreoConf() {
        return correoConf;
    }

    public void setCorreoConf(Configuracion correoConf) {
        this.correoConf = correoConf;
    }
    
    
    
    @PostConstruct
    public void init(){
        
        correoConf=(Configuracion)sessionController.getCorreoConf();
        listaDireccionesCopia=(ArrayList<Direccion>)usuarioService.listaDirecciones();
        
    }
    
    public String editarConfiguracion(){
    
        if(nuevoPassword.equals("")==false)
            correoConf.setPassword(nuevoPassword);
        
    try{    
    //correoConf.setPassword(Seguridad.encrypt(correoConf.getPassword()));
    sessionController.setCorreoConf(correoConf);
    }catch(Exception ex){
        
        sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
    }
    
    sessionController.creaMensaje("Configuración guardada correctamente", FacesMessage.SEVERITY_INFO);
    correoConf=sessionController.getCorreoConf();
    return null;
    
}
    
    public String eliminarDireccionCopia(){
        
        try{
            
            
            
            listaDireccionesCopia.remove(selectedDireccion);
            correoConf.getDireccions().remove(selectedDireccion);
            usuarioService.eliminarDireccion(selectedDireccion);
            sessionController.setCorreoConf(correoConf);
        }catch(RuntimeException ex){
            
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            listaDireccionesCopia=(ArrayList<Direccion>)usuarioService.listaDirecciones();
            
        }
        
        sessionController.creaMensaje("Direccion eliminada", FacesMessage.SEVERITY_INFO);
       return null;
        
    }
    
    public String crearDireccionCopia(){
        
        
        
        
        
        
         Direccion d=new Direccion(direccionCopia,correoConf);
         
         Iterator i=correoConf.getDireccions().iterator();
         while(i.hasNext()){
             
             Direccion aux=(Direccion)i.next();
             if(aux.equals(d)){
                sessionController.creaMensaje("Ya existe ese correo", FacesMessage.SEVERITY_ERROR);
                direccionCopia=null;
                return null;
                 
             }
             
         }
         
         
         
             
            
         
         
         correoConf.getDireccions().add(d);
        
        try{
            
           
            
            sessionController.setCorreoConf(correoConf);
            
            }catch(Exception ex){
        
        sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
    }
    
    sessionController.creaMensaje("Configuración guardada correctamente", FacesMessage.SEVERITY_INFO);
    //correoConf=sessionController.getCorreoConf();
    listaDireccionesCopia.add(d);
    direccionCopia=null;
    return null;
            
            
        }
        
        
    }
    
    
    

