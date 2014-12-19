package controllers;



import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
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
import javax.servlet.http.HttpSession;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UsuarioService;
import model.utils.beanUtilidades;




@ManagedBean
@ViewScoped
public class MisMovilidadesController implements Serializable{

   
    
   @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
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
    
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
       usuario=(Usuario)session.getAttribute("user");
       listaMisMovilidades=(ArrayList < Movilidad >)movilidadService.listarMisMovilidades(usuario.getLogin());
       Collections.reverse(listaMisMovilidades);
       
        
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

   
    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
         admin=usuarioService.find(("admin"));
        }catch(UsuarioNotFoundException ex){
        }
        
        
        if(selectedMovilidad.getEstado().equalsIgnoreCase("pendiente")){
           
            try{
            movilidadService.eliminarMovilidad(selectedMovilidad);
            }catch(RuntimeException ex){
                    beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                    return "verMisMovilidades.xhtml";
                    }
            Mensaje mensaje=new Mensaje(admin,usuario,Calendar.getInstance().getTime(), "movilidad eliminada", "el usuario "+usuario.getLogin()+" ha eliminado una movilidad", "no","no","no");
                mensajeService.enviarMensaje(mensaje);
                beanUtilidades.creaMensaje("movilidad eliminada correctamente, se ha enviado un mensaje al coordinador ", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                actualizar();
                return null;
            }
            
         
            if(selectedMovilidad.getEstado().equalsIgnoreCase("aceptada")){
                
                Mensaje mensaje=new Mensaje( admin,usuario, Calendar.getInstance().getTime(), "movilidad eliminada", "el usuario "+usuario.getLogin()+" quiere cancelar una movilidad en curso en: "+selectedMovilidad.getUniversidad().getNombre()+" con fecha de inicio:"+ sdf.format(selectedMovilidad.getFechaInicio())+" y fecha fin:"+sdf.format(selectedMovilidad.getFechaFin()), "no","no","no");
                mensajeService.enviarMensaje(mensaje);
                beanUtilidades.creaMensaje("se ha enviado un mensaje al coordinador para su cancelación", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
                    
            }
                
                if(selectedMovilidad.getEstado().equalsIgnoreCase("rechazada")){
                    try{
                movilidadService.eliminarMovilidad(selectedMovilidad);    
                    }catch(RuntimeException ex){
                        beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                        return "verMisMovilidades.xhtml";
                    }
                actualizar();     
                beanUtilidades.creaMensaje("movilidad eliminada correctamente", FacesMessage.SEVERITY_INFO);
                selectedMovilidad=null;
                return null;
           
    }
     return null;
    }
    public void actualizar(){
        
        listaMisMovilidades=(ArrayList < Movilidad >)movilidadService.listarMisMovilidades(usuario.getLogin());
        Collections.reverse(listaMisMovilidades);
    }
    
   
    }
    

    
    

