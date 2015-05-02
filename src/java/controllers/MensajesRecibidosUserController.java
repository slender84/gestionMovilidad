package controllers;

import entities.Mensaje;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.MensajeService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;



@ManagedBean
@ViewScoped
public class MensajesRecibidosUserController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{mensajeService}")
    private MensajeService mensajeService;
    
    private Usuario user;
    
     private boolean activaRecibido;
    private String textAreaRecibido;
    private String temaRecibido;
    
    private boolean activaTexto;
    
    private Mensaje selectedMensajeRecibido;
    private ArrayList<Mensaje> listaMensajesRecibidos;
    private ArrayList<Mensaje> selectedMensajesRecibidos;
    private ArrayList<Mensaje> filteredMensajesRecibidos;
    
    private LazyDataModel<Mensaje> model;
    private List<Mensaje> result;
    
    
    
    public MensajesRecibidosUserController() {
    }
    
    @PostConstruct
    public void init(){
        
        user=sessionController.getUser();
       
        //setListaMensajesRecibidos((ArrayList<Mensaje>)mensajeService.mensajesRecibidos("admin", user.getLogin()));
        model=new LazyDataModel<Mensaje>(){
          
            @Override
            public List<Mensaje> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
    
                
                
             result=mensajeService.listaLazyMensajeRecibido(first,pageSize,sortField,sortOrder,filters,user.getLogin());
            
                //setRowCount(10);
                setRowCount(mensajeService.countMensajeRecibido(filters,user.getLogin()));
              return result;
              
              
            }
            
            
            @Override
            public Object getRowKey(Mensaje m){
                  
                  return m.getIdmensaje();
                  
              }
            
           
            public Mensaje getRowData(int rowKey){
                
                for(Mensaje m:result){
                    
                    if(m.getIdmensaje()==rowKey)
                        return m;
                    
                }
                return null;
            }
            
        
    };      
        
        
        
        
        
        
        
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public LazyDataModel<Mensaje> getModel() {
        return model;
    }

    public void setModel(LazyDataModel<Mensaje> model) {
        this.model = model;
    }

    public List<Mensaje> getResult() {
        return result;
    }

    public void setResult(List<Mensaje> result) {
        this.result = result;
    }

    

    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public boolean isActivaRecibido() {
        return activaRecibido;
    }

    public void setActivaRecibido(boolean activaRecibido) {
        this.activaRecibido = activaRecibido;
    }

    public String getTextAreaRecibido() {
        return textAreaRecibido;
    }

    public void setTextAreaRecibido(String textAreaRecibido) {
        this.textAreaRecibido = textAreaRecibido;
    }

    public String getTemaRecibido() {
        return temaRecibido;
    }

    public void setTemaRecibido(String temaRecibido) {
        this.temaRecibido = temaRecibido;
    }

    public boolean isActivaTexto() {
        return activaTexto;
    }

    public void setActivaTexto(boolean activaTexto) {
        this.activaTexto = activaTexto;
    }

    public Mensaje getSelectedMensajeRecibido() {
        return selectedMensajeRecibido;
    }

    public void setSelectedMensajeRecibido(Mensaje selectedMensajeRecibido) {
        this.selectedMensajeRecibido = selectedMensajeRecibido;
    }

    public ArrayList<Mensaje> getListaMensajesRecibidos() {
        return listaMensajesRecibidos;
    }

    public void setListaMensajesRecibidos(ArrayList<Mensaje> listaMensajesRecibidos) {
        this.listaMensajesRecibidos = listaMensajesRecibidos;
    }

    public ArrayList<Mensaje> getSelectedMensajesRecibidos() {
        return selectedMensajesRecibidos;
    }

    public void setSelectedMensajesRecibidos(ArrayList<Mensaje> selectedMensajesRecibidos) {
        this.selectedMensajesRecibidos = selectedMensajesRecibidos;
    }

    public ArrayList<Mensaje> getFilteredMensajesRecibidos() {
        return filteredMensajesRecibidos;
    }

    public void setFilteredMensajesRecibidos(ArrayList<Mensaje> filteredMensajesRecibidos) {
        this.filteredMensajesRecibidos = filteredMensajesRecibidos;
    }

   

    

    
    
    
     public String leerMensajeRecibido(){
        
        activaRecibido=true;
        temaRecibido=selectedMensajeRecibido.getTema();
        textAreaRecibido=selectedMensajeRecibido.getTexto();
         selectedMensajeRecibido.setLeidoDestino(true);
         try{
        mensajeService.leerMensajeRecibido(selectedMensajeRecibido);
         }catch(InstanceNotFoundException ex){
             
         }
         return null;
    }
     
     public void actualizarRecibidos(){
     
       //setListaMensajesRecibidos((ArrayList<Mensaje>)mensajeService.mensajesRecibidos("admin", user.getLogin()));
        for(Mensaje m:selectedMensajesRecibidos){
            
            if(selectedMensajeRecibido!=null&&m.getIdmensaje().equals(selectedMensajeRecibido.getIdmensaje()))
             
            activaRecibido=false;
         
        }     
        
        result.removeAll(selectedMensajesRecibidos);
            
        }    
     
    public String eliminarMensajesRecibidos(){
        
        if(selectedMensajesRecibidos.isEmpty()){
            return null;
        }
        
        for(Mensaje m:selectedMensajesRecibidos){
         try{
            mensajeService.eliminarMensaje(m,"recibido");
         }catch(InstanceNotFoundException ex){
             
         }  
            
        actualizarRecibidos();
        
    
    }
        return null;
    }
    
        public void cerrarMensajeRecibido(){
        
        textAreaRecibido="";
        temaRecibido="";
        activaRecibido=false;
        
    }   
        
        
              
   
    
    
    
}