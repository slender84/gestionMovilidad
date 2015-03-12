package controllers;


import entities.Configuracion;
import entities.InfoCuenta;
import entities.Mensaje;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
//import org.apache.commons.mail.EmailException;
import model.services.MensajeService;
import model.services.UsuarioService;
import model.utils.Seguridad;



@ManagedBean
@RequestScoped
public class CrearUsuarioController implements Serializable{
    
    @ManagedProperty(value="#{mensajeService}")
    private MensajeService mensajeService;
    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{usuarioService}")  
    private UsuarioService usuarioService;
    
    private String login;
    private String password;
    private String passwordAux;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Usuario user;
    private String titulacion;
    private ArrayList<String> listaTitulaciones;
    
    
    public CrearUsuarioController() {
    }
    
    @PostConstruct
    public void init(){
        
        ArrayList<String>aux=new ArrayList<String>();
        aux.add("GEI");
        aux.add("MUEI");
        setListaTitulaciones(aux);
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
    
    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAux() {
        return passwordAux;
    }

    public void setPasswordAux(String passwordAux) {
        this.passwordAux = passwordAux;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public ArrayList<String> getListaTitulaciones() {
        return listaTitulaciones;
    }

    public void setListaTitulaciones(ArrayList<String> listaTitulaciones) {
        this.listaTitulaciones = listaTitulaciones;
    }
    
    
    
    
    public String creaUsuario(){
        
        
        Usuario u=new Usuario();
        u.setLogin(login);
        password=usuarioService.generarPassword();
        password=password.substring(0, 8);
        u.setPassword(Seguridad.md5Password(password));
        short s=1;
        u.setTipoUsuario(s);
        u.setNombre(nombre);
        u.setApellido1(apellido1);
        u.setApellido2(apellido2);
        u.setTitulacion(titulacion);
        u.setBorrado(false);
        u.setInfoCuenta(new InfoCuenta(u, 0));
        
        
        
         
        
        
        try{
        usuarioService.insertarUsuario(u);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            
            sessionController.creaMensaje("ya existe ese login", FacesMessage.SEVERITY_ERROR);
            return null;
            
                }
        Configuracion correoConf;
        
       try{
            correoConf=sessionController.getCorreoConf();
            usuarioService.enviarEmail(login,password,correoConf);
            
        }catch(Exception ex){
            
            usuarioService.eliminarUsuario(u);
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        
        sessionController.creaMensaje("usuario creado. Se ha enviado un correo a la cuenta "+login+"@"+correoConf.getAddTo() +" con la contraseña", FacesMessage.SEVERITY_INFO);
        
        
        Usuario destino;
        try{
            
            destino=usuarioService.buscarUsuario("admin");
            
        
            
       
        Mensaje m=new Mensaje();
        
        m.setUsuarioByDestino(destino);
        m.setEliminadoDestino(false);
        m.setEliminadoOrigen(true);
        m.setFecha(Calendar.getInstance().getTime());
        m.setLeidoDestino(false);
        m.setUsuarioByOrigen(new Usuario(login, password, s, titulacion, nombre, apellido1));
        m.setTema("usuario creado");
        m.setTexto("el usuario "+login+" se ha dado de alta en el sistema");
        mensajeService.enviarMensaje(m);
        }catch(InstanceNotFoundException|RuntimeException ex){
            
            sessionController.creaMensaje("se ha producido un error. Inténtalo más tarde", FacesMessage.SEVERITY_ERROR);
                return null;
        }
        
        login="";
        nombre="";
        apellido1="";
        apellido2="";
        titulacion="";
        password="";
            return null;
        
    }
    
    
    
    
    
    
    
}
