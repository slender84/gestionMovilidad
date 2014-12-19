package controllers;

import entities.CorreoConf;
import entities.Mensaje;
import entities.Usuario;
import exceptions.UsuarioNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.mail.EmailException;
import model.services.MensajeService;
import model.services.UsuarioService;
import model.utils.beanUtilidades;


@ManagedBean
@ViewScoped
public class CrearUsuarioController implements Serializable{
    
    @ManagedProperty(value="#{mensajeService}")
    private MensajeService mensajeService;
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
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
    
    

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
        u.setPassword(usuarioService.md5Password(password));
        short s=1;
        u.setTipoUsuario(s);
        u.setNombre(nombre);
        u.setApellido1(apellido1);
        u.setApellido2(apellido2);
        u.setTitulacion(titulacion);
        
        
         
        
        
        try{
        usuarioService.insertarUsuario(u);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("ya existe ese login", FacesMessage.SEVERITY_ERROR);
            return null;
            
                }
       try{
            CorreoConf correoConf=beanUtilidades.getCorreoConf();
            usuarioService.enviarEmail(login,password,correoConf);
            
        }catch(EmailException ex){
            ex.printStackTrace();
            usuarioService.delete(u);
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        
        beanUtilidades.creaMensaje("usuario creado. Se ha enviado un correo a la cuenta "+login+"@udc.es con la contraseña", FacesMessage.SEVERITY_INFO);
        
        
        Usuario destino;
        try{
            
            destino=usuarioService.find("admin");
            
        
            
       
        Mensaje m=new Mensaje();
        
        m.setUsuarioByDestino(destino);
        m.setEliminadoDestino("no");
        m.setEliminadoOrigen("si");
        m.setFecha(Calendar.getInstance().getTime());
        m.setLeidoDestino("no");
        m.setUsuarioByOrigen(new Usuario(login, password, s, titulacion, nombre, apellido1));
        m.setTema("usuario creado");
        m.setTexto("el usuario "+login+" se ha dado de alta en el sistema");
        mensajeService.enviarMensaje(m);
        }catch(UsuarioNotFoundException|RuntimeException ex){
            
            beanUtilidades.creaMensaje("se ha producido un error. Inténtalo más tarde", FacesMessage.SEVERITY_ERROR);
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
