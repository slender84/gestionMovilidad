package controllers;

import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.UsuarioService;



@ManagedBean
@ViewScoped
public class EliminarUsuarioController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{usuarioService}")  
    private transient UsuarioService usuarioService;
    
    
    private ArrayList<Usuario> selectedUsuarios;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Usuario> filteredUsuarios;
    
    
    public EliminarUsuarioController() {
    }
    @PostConstruct
    public void init(){
        
        setListaUsuarios((ArrayList < Usuario >)usuarioService.listarUsuarios());
        
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

    public ArrayList<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(ArrayList<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public ArrayList<Usuario> getFilteredUsuarios() {
        return filteredUsuarios;
    }

    public void setFilteredUsuarios(ArrayList<Usuario> filteredUsuarios) {
        this.filteredUsuarios = filteredUsuarios;
    }
    
    
    
    public String eliminaUsuariosLista(){
        
        if(selectedUsuarios.isEmpty()){
            return null;
        }
        
        for(Usuario u:selectedUsuarios){
            
            if(u.getTipoUsuario()==2){
                try{
                usuarioService.eliminarUsuario(u);
                listaUsuarios.remove(u);
                }catch(RuntimeException ex){
                    return null;
                }
            }else{
                
                
        
            
            
            u.setBorrado(true);
            try{
        usuarioService.editarUsuario(u);
            }catch(InstanceNotFoundException ex){
                
                
            }
        listaUsuarios.remove(u);
        }
        
        }
         sessionController.creaMensaje("usuarios borrados ", FacesMessage.SEVERITY_INFO);
        //setListaUsuarios((ArrayList < Usuario >)usuarioService.listar());
        return null;
          
    }
    
    public String borrarUsuario(){
        
        
        
         if(selectedUsuarios.isEmpty()){
            return null;
        }
         
         for(Usuario u:selectedUsuarios){
             
             try{
                 
                 usuarioService.eliminarUsuario(u);
                  
             }catch(RuntimeException ex){
                 
                 sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
                 listaUsuarios=(ArrayList < Usuario >)usuarioService.listarUsuarios();
                 return null;
             }
             
             listaUsuarios.remove(u);
             
             
         }
         sessionController.creaMensaje("usuarios eliminados correctamente", FacesMessage.SEVERITY_INFO);
                 
         
         return null;
        
    }
    
    
    
    
    
    
    
}
