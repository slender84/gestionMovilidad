
package controllers;

import entities.Cronica;
import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UniversidadService;
import model.services.UsuarioService;
import model.utils.beanUtilidades;






@ManagedBean
@ViewScoped
public class MisCronicasController implements Serializable{

    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value ="#{mensajeService}")
    private MensajeService mensajeService;
    
    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioService usuarioService;
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty(value = "#{movilidadService}")
    private MovilidadService movilidadService;
    
    
    @PostConstruct
    public void init(){
        
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario=(Usuario)session.getAttribute("user");
        
        HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request.getRequestURI().equals(request.getContextPath()+"/usuario/verMisCronicas.xhtml")){
            
            
            listaMisCronicas=(ArrayList<Cronica>)universidadService.listarMisCronicas(usuario);
            
        } else{
        
        
        
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("movilidad")){
        selectedMovilidad=(Movilidad)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("movilidad");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("movilidad");
        try{
               selectedMovilidad=movilidadService.buscarMovilidad(selectedMovilidad.getCodMovilidad());
           }catch(InstanceNotFoundException ex){
               try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
           }
    }else{
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
    }
    }
    }
    

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
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

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
    }

    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }
    
    
    
    
    private Movilidad selectedMovilidad;
    
    private ArrayList<Cronica> listaMisCronicas;
    
    
    private Cronica selectedCronica;
    
    
    private Usuario usuario;
    private String texto;
    private String alias;

    private boolean btnActivados=true;
    private boolean panelEdicion;
    
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isBtnActivados() {
        return btnActivados;
    }

    public boolean isPanelEdicion() {
        return panelEdicion;
    }

    public void setPanelEdicion(boolean panelEdicion) {
        this.panelEdicion = panelEdicion;
    }
    
    

    public void setBtnActivados(boolean btnActivados) {
        this.btnActivados = btnActivados;
    }

    public ArrayList<Cronica> getListaMisCronicas() {
        return listaMisCronicas;
    }

    public void setListaMisCronicas(ArrayList<Cronica> listaMisCronicas) {
        this.listaMisCronicas = listaMisCronicas;
    }

    

    public Cronica getSelectedCronica() {
        return selectedCronica;
    }

    public void setSelectedCronica(Cronica selectedCronica) {
        this.selectedCronica = selectedCronica;
    }
    
    
    
    
    
    public MisCronicasController() {
    }
    
    
    
    
    
    
    public String enviarCronica(){
        
        
        
        
        
        if (alias.equals("")){
            alias=usuario.getNombre()+" "+usuario.getApellido1()+" "+usuario.getApellido2();
        }
        
        Cronica c=new Cronica(selectedMovilidad.getUniversidad(), usuario, Calendar.getInstance().getTime(),"pendiente", alias,texto);
        try{
            
            universidadService.enviarCronica(c);
            
        }catch(RuntimeException ex){
               try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
           }
        Usuario admin=null;
        try{
            
            admin=usuarioService.buscarUsuario("admin");
            
        }catch(InstanceNotFoundException ex){
            
        }
        
        Mensaje m=new Mensaje(admin, usuario, Calendar.getInstance().getTime(), "Comentario creado ", "el usuario "+usuario.getLogin()+" ha escrito una crónica",false,false,false);
        mensajeService.enviarMensaje(m);
        beanUtilidades.creaMensaje("crónica enviada correctamente, a la espera de moderación", FacesMessage.SEVERITY_INFO);
        btnActivados=false;
        texto="";
        alias="";
       
        return null;
        
    }
    
    public void verCronica(){
        
        
        panelEdicion=true;
        
    }
    
    
    public String editarCronica(){
    
        selectedCronica.setFecha(Calendar.getInstance().getTime());
    try{
        universidadService.editarCronica(selectedCronica);
        
    }catch(InstanceNotFoundException ex){
        
        beanUtilidades.creaMensaje("No existe ese comentario",FacesMessage.SEVERITY_ERROR );
        listaMisCronicas=(ArrayList<Cronica>)universidadService.listarMisCronicas(usuario);
        panelEdicion=false;
        return null;
    }
    Usuario admin=null;
    try{
            
            admin=usuarioService.buscarUsuario("admin");
            
        }catch(InstanceNotFoundException ex){
            
        }
        
        Mensaje m=new Mensaje(admin, usuario, Calendar.getInstance().getTime(), "Comentario editado ", "el usuario "+usuario.getLogin()+" ha editado un comentario",false,false,false);
        mensajeService.enviarMensaje(m);
    beanUtilidades.creaMensaje("Cronica actualizada, pendiente de moderación", FacesMessage.SEVERITY_INFO);
    return null;
    
}
    public void cerrarEdicion(){
        
        panelEdicion=false;
        
        
    }
    
    
}
