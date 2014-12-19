package controllers;



import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
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
import org.primefaces.event.RowEditEvent;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.utils.beanUtilidades;




@ManagedBean
@ViewScoped
public class MovilidadesController implements Serializable{

   
    
   @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
     @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;

     
     @ManagedProperty(value="#{mensajeService}")
     private MensajeService mensajeService;
  
    
    
    public MovilidadesController() {
        
    }
    
  private Usuario usuario;
    
    private String texto;
    private String tema;
    private boolean activaTexto;
    
    private String changeEstado;
    
    private Movilidad selectedMovilidad;
    private ArrayList<Movilidad>listaMovilidades;
    private ArrayList<Movilidad> filteredMovilidades;
    private ArrayList<Movilidad> selectedMovilidades;
   
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
  
    @PostConstruct
    public void init(){
    
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      
       usuario=(Usuario)session.getAttribute("admin");
       listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
      
       }

    
     public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
    }
    
     public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }
    
    
    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }


    public ArrayList<Movilidad> getSelectedMovilidades() {
        return selectedMovilidades;
    }

    public void setSelectedMovilidades(ArrayList<Movilidad> selectedMovilidades) {
        this.selectedMovilidades = selectedMovilidades;
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
    
    public ArrayList<Movilidad> getListaMovilidades() {
        Collections.reverse(listaMovilidades);
        return listaMovilidades;
    }

    public void setListaMovilidades(ArrayList<Movilidad> listaMovilidades) {
        this.listaMovilidades = listaMovilidades;
    }

    public ArrayList<Movilidad> getFilteredMovilidades() {
        return filteredMovilidades;
    }

    public void setFilteredMovilidades(ArrayList<Movilidad> filteredMovilidades) {
        this.filteredMovilidades = filteredMovilidades;
    }

    public String getChangeEstado() {
        return changeEstado;
    }

    public void setChangeEstado(String changeEstado) {
        this.changeEstado = changeEstado;
    }

    
       
    public String verContratos(){
       
        
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", selectedMovilidad);
        
        return ("verContratos.xhtml?faces-redirect=true");
       
    }    
   

   
    public void onRowCancel(){
        
        
    }
    
    
    
    public String onRowEdit(RowEditEvent event){
       
        Movilidad m=(Movilidad)event.getObject();
        
        if(changeEstado.equals(m.getEstado())==false){
        
        m.setEstado(changeEstado);
        try{
        movilidadService.crearMovilidad(m);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
            return "verMovilidades.xhtml";
        }
        Mensaje mensaje=new Mensaje(m.getUsuario() ,usuario, Calendar.getInstance().getTime(),"cambio de estado de movilidad","destino:"+m.getUniversidad().getNombre()+" \n"+"fecha de inicio:"+sdf.format(m.getFechaInicio())+" \n"+"fecha fin:"+sdf.format(m.getFechaFin())+"\n\n"+ "el estado de la movilidad ahora es: "+m.getEstado(), "no","no","no");
            mensajeService.enviarMensaje(mensaje);
            beanUtilidades.creaMensaje("estado de una movilidad modificado, se ha enviado un mensaje", FacesMessage.SEVERITY_INFO);
        }
               return null;
    }
    
    
        
    public String eliminaMovilidadLista(){
        
        for(Movilidad m:selectedMovilidades){
            try{
        movilidadService.eliminarMovilidad(m);
            }catch(RuntimeException ex){
                beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                return "verMovilidades.xhtml";
            }
        }
        
        actualizarTodasMovilidades();
        beanUtilidades.creaMensaje("movilidad eliminada correctamente", FacesMessage.SEVERITY_INFO);
        return null;
        
    }
    
     public void actualizarTodasMovilidades(){
        
        listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
        
    }
    
    public void activaTexto(){
        
        if(selectedMovilidades.isEmpty()==false){
        activaTexto=true;
    }else{
            beanUtilidades.creaMensaje("hay que seleccionar al menos un usuario", FacesMessage.SEVERITY_ERROR);
        }
    }
    
    
    public String enviarMensajesVarios(){
    if(selectedMovilidades.isEmpty()){
        return null;
    }
    for(Movilidad m:selectedMovilidades){
        Mensaje mensaje=new Mensaje(m.getUsuario(),usuario,  Calendar.getInstance().getTime(), tema, texto, "no", "no", "no");
        mensajeService.enviarMensaje(mensaje);
        
        
    }
        beanUtilidades.creaMensaje("mensajes enviados correctamente", FacesMessage.SEVERITY_INFO);
        activaTexto=false;
        tema="";
        texto="";
        selectedMovilidades=null;
        
    return null;
}
    
    public void cancelar(){
        
        activaTexto=false;
    }
    
    
    
    
    }
  