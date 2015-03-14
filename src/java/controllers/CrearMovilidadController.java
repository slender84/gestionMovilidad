package controllers;



import entities.Cursoacademico;
import entities.Mensaje;
import entities.Universidad;
import entities.Usuario;
import exceptions.DuracionException;
import exceptions.InstanceNotFoundException;
import exceptions.NumeroDeMovilidadesException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UniversidadService;
import model.services.UsuarioService;





@ManagedBean
//@javax.faces.bean.SessionScoped
@ViewScoped
public class CrearMovilidadController implements Serializable{

   @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
     @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;

     @ManagedProperty(value="#{usuarioService}")
     private UsuarioService usuarioService;
     
     @ManagedProperty(value="#{mensajeService}")
     private MensajeService mensajeService;
  

    
    public CrearMovilidadController() {
        }
    
    @PostConstruct
    public void init(){
        
        usuario=sessionController.getUser();
       fechaMin=movilidadService.fechaMin();
       fechaMax=movilidadService.fechaMax();
    } 
       
       
     
    private Usuario usuario;
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
   
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaMin;
    private Date fechaMax;
    private Cursoacademico cursoacademico;
    
    private String selectedPais;
    private String selectedUniversidad;
    private Universidad universidad;
    
    private ArrayList<Universidad> listaUniversidades;
    
    private boolean checkPais;

    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }

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

   
    
    

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaMin() {
        return fechaMin;
    }

    public void setFechaMin(Date fechaMin) {
        this.fechaMin = fechaMin;
    }

    public Date getFechaMax() {
        return fechaMax;
    }

    public void setFechaMax(Date fechaMax) {
        this.fechaMax = fechaMax;
    }

    public Cursoacademico getCursoacademico() {
        return cursoacademico;
    }

    public void setCursoacademico(Cursoacademico cursoacademico) {
        this.cursoacademico = cursoacademico;
    }

    public String getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(String selectedPais) {
        this.selectedPais = selectedPais;
    }

    public String getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(String selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }

    public ArrayList<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }
    
    

    public void setListaUniversidades(ArrayList<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }

    public boolean isCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
    }
    
    
    public void onDropboxChangePais(){
       
       checkPais=true;
       listaUniversidades=(ArrayList<Universidad>)universidadService.listarPorPais(selectedPais);
       universidad=null;
   }
    
    public void onDropboxchangeUni(){
        
        try{
        universidad=universidadService.buscarUniversidad(selectedUniversidad);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("universidad inexistente", FacesMessage.SEVERITY_ERROR);
            
        }
    }
    
    
public String crearMovilidad(){
   
        checkPais=false;
        //checkUni=false;
        
         Universidad u;
        try{
       u=universidadService.buscarUniversidad(selectedUniversidad);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("universidad inexistente", FacesMessage.SEVERITY_ERROR);
            selectedUniversidad="";
                selectedPais="";
                selectedUniversidad="";
                fechaFin=null;
                fechaInicio=null;
                universidad=null;
            return "";
        }
        Calendar cal1=Calendar.getInstance();
        Calendar cal2=Calendar.getInstance();
                cal1.setTime(fechaInicio);
                cal2.setTime(fechaFin);
                Cursoacademico ca=universidadService.buscarCursoAcademico(fechaInicio, fechaFin);
                
              try{
                  
                  movilidadService.crearMovilidad(fechaInicio, fechaFin, usuario, u,ca);
                  
                  
             
              
              }catch(DuracionException ex){
                  sessionController.creaMensaje(ex.getMessage(),FacesMessage.SEVERITY_ERROR);
                  return null;
              }
              catch(NumeroDeMovilidadesException ex){
                  sessionController.creaMensaje(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
                  return null;
              }
              
               catch(RuntimeException ex){
                 sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                  return "crearMovilidad.xhtml";
              }
             
              
              Usuario admin=null;
               try{
                    admin=usuarioService.buscarUsuario("admin");
               }catch(InstanceNotFoundException ex){
                   
               }
                
                
                Mensaje mensaje=new Mensaje(admin,usuario,  Calendar.getInstance().getTime(), "movilidad creada", "el usuario "+usuario.getNombre()+" "+usuario.getApellido1()+""
                        + " ha creado una movilidad a "+selectedUniversidad+" entre el "+sdf.format(fechaInicio)+" y "+sdf.format(fechaFin) , false,true,false);
                
                    mensajeService.enviarMensaje(mensaje);
                
                
                sessionController.creaMensaje("movilidad creada", FacesMessage.SEVERITY_INFO);
                sessionController.creaMensaje(usuario.getLogin()+" a "+ selectedUniversidad+" "+selectedPais+" "+" " + " de "+sdf.format(fechaInicio)+" a "+ sdf.format(fechaFin), FacesMessage.SEVERITY_INFO);
                selectedUniversidad="";
                selectedPais="";
                selectedUniversidad="";
                fechaFin=null;
                fechaInicio=null;
                universidad=null;
                
                
                
                
                return null;
        
    }


   
}
