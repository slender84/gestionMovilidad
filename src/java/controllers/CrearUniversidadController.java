package controllers;

import entities.Pais;
import entities.Universidad;
import exceptions.PaisException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.UniversidadService;
import model.utils.beanUtilidades;


@ManagedBean
@ViewScoped
public class CrearUniversidadController implements Serializable{

    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    
    
    public CrearUniversidadController() {
    }
    
    @PostConstruct
    public void init(){
    
}

  private String paisStr;
   
    
   
    
    //universidad
    private String codUniversidad;
    private String nombre;
    private String universidadStr;
    private String info;
    private String web;
    private String paisStrEdit;
    
    private ArrayList<Pais> listaPaises;
    private ArrayList<Universidad> listaUniversidades;
    private ArrayList<Universidad> selectedUniversidades;
    private ArrayList<Universidad> filteredUniversidades;
    
    private boolean checkPaisStr;
    private boolean checkUniversidadStr;
    private boolean checkDetalles;
    
    
    private Pais selectedPais;
    private Universidad selectedUniversidad;
   

    
    
    //SERVICES Y UTILIDADES
    
    
    
    
    public UniversidadService getUniversidadService()  {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

   

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
    }

    
    
    // GETTER Y SETTER
    
    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public String getCodUniversidad() {
        return codUniversidad;
    }

    public void setCodUniversidad(String codUniversidad) {
        this.codUniversidad = codUniversidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUniversidadStr() {
        return universidadStr;
    }

    public void setUniversidadStr(String universidadStr) {
        this.universidadStr = universidadStr;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPaisStrEdit() {
        return paisStrEdit;
    }

    public void setPaisStrEdit(String paisStrEdit) {
        this.paisStrEdit = paisStrEdit;
    }
    
    

    public ArrayList<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(ArrayList<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public ArrayList<Universidad> getListaUniversidades() {
        return listaUniversidades;
    }

    public void setListaUniversidades(ArrayList<Universidad> listaUniversidades) {
        this.listaUniversidades = listaUniversidades;
    }

    public ArrayList<Universidad> getSelectedUniversidades() {
        return selectedUniversidades;
    }

    public void setSelectedUniversidades(ArrayList<Universidad> selectedUniversidades) {
        this.selectedUniversidades = selectedUniversidades;
    }

    public ArrayList<Universidad> getFilteredUniversidades() {
        return filteredUniversidades;
    }

    public void setFilteredUniversidades(ArrayList<Universidad> filteredUniversidades) {
        this.filteredUniversidades = filteredUniversidades;
    }
    
    

   //BOOLEAN 

  

    public boolean isCheckPaisStr() {
        return checkPaisStr;
    }

    public void setCheckPaisStr(boolean checkPaisStr) {
        this.checkPaisStr = checkPaisStr;
    }

    public boolean isCheckUniversidadStr() {
        return checkUniversidadStr;
    }

    public void setCheckUniversidadStr(boolean checkUniversidadStr) {
        this.checkUniversidadStr = checkUniversidadStr;
    }

    public boolean isCheckDetalles() {
        return checkDetalles;
    }

    public void setCheckDetalles(boolean checkDetalles) {
        this.checkDetalles = checkDetalles;
    }


    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
    }

    public Universidad getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(Universidad selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }

   
    
    public void onChangePais(){
        
        
        listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(paisStr);
        
    }
    
    
   public String creaUniversidad(){
       
       Pais p;
       try{
        p=universidadService.findPais(paisStr);
       }catch(PaisException ex){
          beanUtilidades.creaMensaje("se ha producido un error,no existe ese país", FacesMessage.SEVERITY_ERROR);
           return "crearUniversidad.xhtml";
       }
        Universidad u=new Universidad();
        u.setInfo(info);
        u.setNombre(nombre);
        u.setPais(p);
        u.setWeb(web);
        u.setCodUniversidad(codUniversidad);
        try{
            
            universidadService.insertarUniversidad(u);
            
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            beanUtilidades.creaMensaje("ya existe esa universidad", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        catch(RuntimeException ex){
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_INFO);
            return null;
        }
        
        beanUtilidades.creaMensaje("universidad creada", FacesMessage.SEVERITY_INFO);
        nombre="";
        web="";
        info="";
        //paisStr="";
        codUniversidad="";
        listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(paisStr);
        return null;
        
    }
    
    
    public void verDetalles(){
        
        codUniversidad=selectedUniversidad.getCodUniversidad();
        paisStr=selectedUniversidad.getPais().getNombre();
        info=selectedUniversidad.getInfo();
        web=selectedUniversidad.getWeb();
        checkDetalles=true;
    }
    
    public String eliminaUniversidadLista(){
        
       
        
        if(selectedUniversidades.isEmpty()==false){
            
            
            for(Universidad u:selectedUniversidades){
               
                try{
                    universidadService.delete(u);
                }catch(RuntimeException ex){
                    listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(paisStr);
                   beanUtilidades.creaMensaje("Error eliminando", FacesMessage.SEVERITY_INFO); 
                   
                    return null;
                }    
            }
            beanUtilidades.creaMensaje("se han eliminado las universidades correctamente", FacesMessage.SEVERITY_INFO);
            listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(paisStr);
            checkDetalles=false;
        }
        return null;
        
    }
    
    public String editar(){
        
        checkDetalles=false;
                 
        try{
        universidadService.actualizar(selectedUniversidad);
        listaUniversidades=(ArrayList<Universidad>)universidadService.listarPorPais(paisStr);
        }catch(RuntimeException ex){
            beanUtilidades.creaMensaje("se ha producido un error ", FacesMessage.SEVERITY_ERROR);
            checkDetalles=false;
            listaPaises=(ArrayList<Pais>)universidadService.listaPaises();
            return "crearUniversidad.xhtml?faces-redirect=true";
        }
        beanUtilidades.creaMensaje("edición correcta", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    public void cerrar(){
        
        checkDetalles=false;
        codUniversidad="";
        nombre="";
        web="";
        info="";
        
        
    }
    
    
    
}
    