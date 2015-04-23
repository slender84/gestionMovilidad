

package controllers;


import entities.Asignatura;
import entities.ComentarioAsignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.MiembroGrupoAsignaturaA;
import entities.Movilidad;
import entities.Pais;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.services.AsignaturaService;
import model.services.EquivalenciaService;
import model.services.UniversidadService;




@ManagedBean
@ViewScoped
public class EquivalenciasPublicasController implements Serializable{

    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{equivalenciaService}")
    private EquivalenciaService equivalenciaService;
    
    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
   @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
    
    
   
    
    
    private ArrayList<Pais> listaPaises;
    private String paisStr;
    private ArrayList<Universidad> listaUniversidad;
    private String universidadStr;
    private Universidad universidad;
     private ArrayList<ComentarioAsignatura> listaComentariosAsignatura;
    private boolean checkPais;
   private boolean checkUni;
    
    private ArrayList<Equivalencia> listaEquivalencias;
    
    private Equivalencia selectedEquivalencia; 
    
    private boolean mostrarInfo;
    
    private Asignatura selectedAsignatura;
   
    
    public EquivalenciasPublicasController() {
        
    }
    
    @PostConstruct
    public void init(){
       
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public Asignatura getSelectedAsignatura() {
        return selectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura selectedAsignatura) {
        this.selectedAsignatura = selectedAsignatura;
    }

    public ArrayList<ComentarioAsignatura> getListaComentariosAsignatura() {
        return listaComentariosAsignatura;
    }

    public void setListaComentariosAsignatura(ArrayList<ComentarioAsignatura> listaComentariosAsignatura) {
        this.listaComentariosAsignatura = listaComentariosAsignatura;
    }

    
    
    
   
    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    
    
    
    
    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }

    public ArrayList<Equivalencia> getListaEquivalencias() {
        return listaEquivalencias;
    }

    public void setListaEquivalencias(ArrayList<Equivalencia> listaEquivalencias) {
        this.listaEquivalencias = listaEquivalencias;
    }

    public ArrayList<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(ArrayList<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public ArrayList<Universidad> getListaUniversidad() {
        return listaUniversidad;
    }

    public void setListaUniversidad(ArrayList<Universidad> listaUniversidad) {
        this.listaUniversidad = listaUniversidad;
    }

    public String getUniversidadStr() {
        return universidadStr;
    }

    public void setUniversidadStr(String universidadStr) {
        this.universidadStr = universidadStr;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }
    
    

    public boolean isCheckPais() {
        return checkPais;
    }

    public void setCheckPais(boolean checkPais) {
        this.checkPais = checkPais;
    }

    public boolean isCheckUni() {
        return checkUni;
    }

    public void setCheckUni(boolean checkUni) {
        this.checkUni = checkUni;
    }

    public Equivalencia getSelectedEquivalencia() {
        return selectedEquivalencia;
    }

    public void setSelectedEquivalencia(Equivalencia selectedEquivalencia) {
        this.selectedEquivalencia = selectedEquivalencia;
    }

    public boolean isMostrarInfo() {
        return mostrarInfo;
    }

    public void setMostrarInfo(boolean mostrarInfo) {
        this.mostrarInfo = mostrarInfo;
    }

   
    
    
    
    
    public void onChangePais(){
        
        checkPais=true;
        listaUniversidad=(ArrayList<Universidad>)universidadService.listarPorPais(paisStr);
        checkUni=false;
        mostrarInfo=false;
        universidadStr="";
    }
    
    public void onChangeUni(){
        
        checkUni=true;
        mostrarInfo=false;
    }
   
    
    public String buscar(){
        
        
        
        try{
        universidad=universidadService.buscarUniversidad(universidadStr);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("no existe esa universidad", FacesMessage.SEVERITY_ERROR);
            universidadStr="";
            return null;
        }
        listaEquivalencias=(ArrayList < Equivalencia >)equivalenciaService.equivalenciasPublicas(universidadStr);
        listaCompletaEquivalencias=(ArrayList<Equivalencia>)equivalenciaService.equivalenciasPublicas(universidadStr);
        arrayDeListasAsignatura=new ArrayList[listaCompletaEquivalencias.size()];
        mostrarInfo=true;
        String a;
        ArrayList<String> listaAsignaturasArray=new ArrayList();
        int i=0;
        for(Equivalencia e:listaEquivalencias){
            
            Iterator it=e.getMiembroGrupoAsignaturaAs().iterator();
            while(it.hasNext()){
                
                a=((MiembroGrupoAsignaturaA)it.next()).getAsignatura().getNombreAsignatura();
                listaAsignaturasArray.add(a);
                
            }
            
            arrayDeListasAsignatura[i]=listaAsignaturasArray;
            i++;
        }
        
        
        
        return null;
    }
    
    public String verContrato(){
         Movilidad m;
        Contrato c;
        
        try{
        c=equivalenciaService.verContratoPorEquivalencia(selectedEquivalencia);
         //m=equivalenciaService.buscarMovilidadPorContrato(c);
        }catch(InstanceNotFoundException|RuntimeException ex){
           sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
           
            return null;
        }
      
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", c);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad", c.getMovilidad());
       // return "equivalenciasPublicasAdmin.xhtml?faces-redirect=true";
        return "gestionarContrato.xhtml?faces-redirect=true";
    }
    
    public void detallesAsign(){
        
       
        listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosPorAsignatura(selectedAsignatura.getId());
        
    }
    
    private ArrayList<Asignatura> listaAsignaturas;
    private ArrayList<Asignatura> filteredAsignaturas;

    public boolean mostrarListaAsignaturas;

    public boolean isMostrarListaAsignaturas() {
        return mostrarListaAsignaturas;
    }

    public void setMostrarListaAsignaturas(boolean mostrarListaAsignaturas) {
        this.mostrarListaAsignaturas = mostrarListaAsignaturas;
    }
    
    
    
    
    
    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public ArrayList<Asignatura> getFilteredAsignaturas() {
        return filteredAsignaturas;
    }

    public void setFilteredAsignaturas(ArrayList<Asignatura> filteredAsignaturas) {
        this.filteredAsignaturas = filteredAsignaturas;
    }
    
  
    public void mostrar(){
        
        mostrarListaAsignaturas=true;
        
    }
    
    public void cerrar(){
        
        mostrarListaAsignaturas=false;
        
        
        
    }
    
    
    
    
    
  public void buscarPorAsignatura(){
      
      listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(true, universidadStr);
      mostrar();
      
  }
    
  
  
    private String valorAjax;
    private ArrayList<Asignatura> listaAsignaturasAjax;
    private ArrayList<Equivalencia> listaCompletaEquivalencias;
    private ArrayList<String>[] arrayDeListasAsignatura;

    public ArrayList<Asignatura> getListaAsignaturasAjax() {
        return listaAsignaturasAjax;
    }

    public ArrayList<String>[] getArrayDeListasAsignatura() {
        return arrayDeListasAsignatura;
    }

    public void setArrayDeListasAsignatura(ArrayList<String>[] arrayDeListasAsignatura) {
        this.arrayDeListasAsignatura = arrayDeListasAsignatura;
    }
    
    
    

    public void setListaAsignaturasAjax(ArrayList<Asignatura> listaAsignaturasAjax) {
        this.listaAsignaturasAjax = listaAsignaturasAjax;
    }

    public ArrayList<Equivalencia> getListaCompletaEquivalencias() {
        return listaCompletaEquivalencias;
    }

    public void setListaCompletaEquivalencias(ArrayList<Equivalencia> listaCompletaEquivalencias) {
        this.listaCompletaEquivalencias = listaCompletaEquivalencias;
    }
    
    
    
    

    public String getValorAjax() {
        return valorAjax;
    }

    public void setValorAjax(String valorAjax) {
        this.valorAjax = valorAjax;
    }
    
    
    
    public void cambioAjax(){
        
        if(valorAjax.equals("")==false){
        
        listaEquivalencias=new ArrayList<Equivalencia>();
        loopA:
        for(int i=0;i<arrayDeListasAsignatura.length;i++){
            for(int j=0;j<arrayDeListasAsignatura[i].size();j++){
                
                if(valorAjax.equals(arrayDeListasAsignatura[i].get(j))){
                    
                    listaEquivalencias.add(listaCompletaEquivalencias.get(i));
                    continue loopA;
                    
                    
                }
            
                
                
            }
            
            
            
            
        }
        
        }else{
            
            
                listaEquivalencias.addAll(listaCompletaEquivalencias);
            
            
        }
       
        
        
    }
    
    
    
    
    
    
    
    
   
}
