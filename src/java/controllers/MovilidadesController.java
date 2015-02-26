package controllers;



import entities.Cursoacademico;
import entities.Mensaje;
import entities.Movilidad;
import entities.Universidad;
import entities.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
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
import model.services.UniversidadService;
import model.utils.beanUtilidades;





@ManagedBean
@ViewScoped
public class MovilidadesController implements Serializable{

   @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
   @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
     @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;

     
     @ManagedProperty(value="#{mensajeService}")
     private MensajeService mensajeService;
  
    
    
    public MovilidadesController() {
        
    }
    
    private String filtroEstado;
    private String filtroCursoAcademico;
    private String filtroPais;
    private String filtroUniversidad;
    
   private boolean checkPais;
    
  private Usuario usuario;
    
    private String texto;
    private String tema;
    private boolean activaTexto;
    
    private String changeEstado;
    
    private Movilidad selectedMovilidad;
    private ArrayList<Movilidad>listaMovilidades;
    private ArrayList<Movilidad> filteredMovilidades;
    private ArrayList<Movilidad> selectedMovilidades;
    private ArrayList<Cursoacademico> listaCursoAcademico;
    private ArrayList<Universidad> listaUniversidades;
    
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
    
  
    @PostConstruct
    public void init(){
    
       HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      
       usuario=(Usuario)session.getAttribute("admin");
       //listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
       listaCursoAcademico=(ArrayList<Cursoacademico>)universidadService.listarCursosAcademicos();
       
       
       
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

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public String getFiltroEstado() {
        return filtroEstado;
    }

    public void setFiltroEstado(String filtroEstado) {
        this.filtroEstado = filtroEstado;
    }

    public String getFiltroCursoAcademico() {
        return filtroCursoAcademico;
    }

    public void setFiltroCursoAcademico(String filtroCursoAcademico) {
        this.filtroCursoAcademico = filtroCursoAcademico;
    }

    public String getFiltroPais() {
        return filtroPais;
    }

    public void setFiltroPais(String filtroPais) {
        this.filtroPais = filtroPais;
    }

    public String getFiltroUniversidad() {
        return filtroUniversidad;
    }

    public void setFiltroUniversidad(String filtroUniversidad) {
        this.filtroUniversidad = filtroUniversidad;
    }

    public ArrayList<Cursoacademico> getListaCursoAcademico() {
        return listaCursoAcademico;
    }

    public void setListaCursoAcademico(ArrayList<Cursoacademico> listaCursoAcademico) {
        this.listaCursoAcademico = listaCursoAcademico;
    }

    public ArrayList<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(ArrayList<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
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

    public boolean isCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
    }

    
    
    
    
    public void onChangePais(){
        
        System.out.println(filtroPais);
        
        if(filtroPais==null){
            
            checkPais=false;
        }
        else{
            if(checkPais==false)
            checkPais=true;
            
          listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(filtroPais);  
        }
        
        
    }
    
    
    public void buscarMovilidades(){
        
        
        
        listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
        
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
        movilidadService.editarMovilidad(m);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("No existe la movilidad", FacesMessage.SEVERITY_ERROR);
            listaMovilidades=(ArrayList<Movilidad>)movilidadService.listarTodasMovilidades();
            return null;
        }
        Mensaje mensaje=new Mensaje(m.getUsuario() ,usuario, Calendar.getInstance().getTime(),"cambio de estado de movilidad","destino:"+m.getUniversidad().getNombre()+" \n"+"fecha de inicio:"+sdf.format(m.getFechaInicio())+" \n"+"fecha fin:"+sdf.format(m.getFechaFin())+"\n\n"+ "el estado de la movilidad ahora es: "+m.getEstado(), false,false,false);
            mensajeService.enviarMensaje(mensaje);
            beanUtilidades.creaMensaje("estado de una movilidad modificado, se ha enviado un mensaje", FacesMessage.SEVERITY_INFO);
        }
               return null;
    }
    
    
        
    public String eliminaMovilidadLista(){
        
        for(Movilidad m:selectedMovilidades){
            try{
        movilidadService.eliminarMovilidad(m);
        listaMovilidades.remove(m);
            }catch(RuntimeException ex){
                actualizarTodasMovilidades();
                beanUtilidades.creaMensaje("No existe la movilidad", FacesMessage.SEVERITY_ERROR);
                return null;
            }
        }
        
        //actualizarTodasMovilidades();
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
    
     Set<Usuario> s=new HashSet<Usuario>();
    for(Movilidad m:selectedMovilidades){
        s.add(m.getUsuario());
    }
    
        
    
    for(Usuario u:s){
        Mensaje mensaje=new Mensaje(u,usuario,  Calendar.getInstance().getTime(), tema, texto, false,false,false);
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
  