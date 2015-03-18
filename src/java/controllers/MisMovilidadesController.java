package controllers;



import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UsuarioService;





@ManagedBean
@ViewScoped
public class MisMovilidadesController implements Serializable{

   
    
   @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
     @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;

     @ManagedProperty(value="#{usuarioService}")
     private UsuarioService usuarioService;
     
     @ManagedProperty(value="#{mensajeService}")
     private MensajeService mensajeService;
  
    
    
    public MisMovilidadesController() {
    }
    
    private Usuario usuario;
    
    
    
    private Movilidad selectedMovilidad;
    private ArrayList<Movilidad> listaMisMovilidades;
    private ArrayList<Movilidad> filteredMovilidades;
    
   
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
    
    @PostConstruct
    public void init(){
    
      
       usuario=sessionController.getUser();
       listaMisMovilidades=(ArrayList < Movilidad >)movilidadService.listarMisMovilidades(usuario.getLogin());
       
       
        
       }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    
    
   
     public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }
    
    
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   
    

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }
    
    

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public ArrayList<Movilidad> getListaMisMovilidades() {
         
         return listaMisMovilidades;
    }

    public void setListaMisMovilidades(ArrayList<Movilidad> listaMisMovilidades) {
        this.listaMisMovilidades = listaMisMovilidades;
    }

    public ArrayList<Movilidad> getFilteredMovilidades() {
        return filteredMovilidades;
    }

    public void setFilteredMovilidades(ArrayList<Movilidad> filteredMovilidades) {
        this.filteredMovilidades = filteredMovilidades;
    }

    
    public String eliminarMovilidad(){
        Usuario admin=null;
        try{
         admin=usuarioService.buscarUsuario(("admin"));
        }catch(InstanceNotFoundException ex){
        }
        
        
        if(selectedMovilidad.getEstado().equalsIgnoreCase("pendiente")){
           
            try{
            movilidadService.eliminarMovilidad(selectedMovilidad);
            listaMisMovilidades.remove(selectedMovilidad);
            }catch(RuntimeException ex){
                    sessionController.creaMensaje("No existe la movilidad", FacesMessage.SEVERITY_ERROR);
                    actualizar();
                    return "verMisMovilidades.xhtml";
                    }
           // Mensaje mensaje=new Mensaje(admin,usuario,Calendar.getInstance().getTime(), "movilidad eliminada", "el usuario "+usuario.getLogin()+" ha eliminado una movilidad", "no","no","no");
             //   mensajeService.enviarMensaje(mensaje);
                sessionController.creaMensaje("movilidad eliminada correctamente ", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                actualizar();
                return null;
            }
            
         
            if(selectedMovilidad.getEstado().equalsIgnoreCase("aceptada")){
                
                Mensaje mensaje=new Mensaje( admin,usuario, Calendar.getInstance().getTime(), "movilidad eliminada", "el usuario "+usuario.getLogin()+" quiere cancelar una movilidad en curso en: "+selectedMovilidad.getUniversidad().getNombre()+" con fecha de inicio:"+ sdf.format(selectedMovilidad.getFechaInicio())+" y fecha fin:"+sdf.format(selectedMovilidad.getFechaFin()), false,false,false);
                mensajeService.enviarMensaje(mensaje);
                sessionController.creaMensaje("se ha enviado un mensaje al coordinador para su cancelación", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
                    
            }
                
                if(selectedMovilidad.getEstado().equalsIgnoreCase("rechazada")){
                    try{
                movilidadService.eliminarMovilidad(selectedMovilidad);   
                listaMisMovilidades.remove(selectedMovilidad);
                    }catch(RuntimeException ex){
                        actualizar();
                        sessionController.creaMensaje("No existe la movilidad", FacesMessage.SEVERITY_ERROR);
                        return "verMisMovilidades.xhtml";
                    }
                //actualizar();  
                    
                sessionController.creaMensaje("movilidad eliminada correctamente", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
           
    }
     return null;
    }
    public void actualizar(){
        
        listaMisMovilidades=(ArrayList < Movilidad >)movilidadService.listarMisMovilidades(usuario.getLogin());
        Collections.reverse(listaMisMovilidades);
    }
    
    public String escribirCronica(){
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        return "escribirCronica.xhtml?faces-redirect=true";
    }
    
    public String escribirComentario(){
        
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        return "escribirComentarioAsignatura.xhtml?faces-redirect=true";
        
        
    }
    
   
    }
    

    
    

