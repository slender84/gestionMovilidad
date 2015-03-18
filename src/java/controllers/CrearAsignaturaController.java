

package controllers;

import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Curso;

import entities.Pais;
import entities.Universidad;

import exceptions.InstanceNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import model.services.AsignaturaService;

import model.services.UniversidadService;



@ManagedBean
@ViewScoped
public class CrearAsignaturaController implements Serializable{
    
    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty (value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
    
    @ManagedProperty (value="#{sessionController}")
    private SessionController sessionController;
    
    
    
    
    
    
    
    
    
    private String paisStr;
    private String universidadStr;
    
   
    
    private String seleccionCurso;
   private ArrayList<Curso> listaCursos;
    
    //asignatura
    
    private String codAsignatura;
    private String nombreAsignatura;
    private Float creditosAsignatura;
    private String periodoAsignatura;
    private String comentario;
    private String webAsignatura;
    private String facultadAsignatura;
    private String titulacionAsignatura;
    private String curso;
    private String idioma;
    private boolean disponible;
    
    private String cursoEdicion;
    
    private ArrayList<Pais> listaPaises;
    private ArrayList<Universidad> listaUniversidades;
    private ArrayList<Asignatura> listaAsignaturas;
    
    private boolean checkPaisStr;
    private boolean checkUniversidadStr;
    private boolean checkDetalles;
    private boolean checkTabla;
    
    private boolean checkComentario;
    private boolean checkComentarios;
    private ComentarioAsignatura selectedComentario;
    private ArrayList<ComentarioAsignatura> listaComentarios;
    private ArrayList<ComentarioAsignatura> selectedComentarios;
    private ArrayList<ComentarioAsignatura> filteredComentarios;
    
    private boolean estadoComentario;
    
    private Pais selectedPais;
    private Universidad selectedUniversidad;
    private ArrayList<Asignatura> selectedAsignaturas;
    private Asignatura SelectedAsignatura;
    private ArrayList<Asignatura>filteredAsignaturas;

    public String getSeleccionCurso() {
        return seleccionCurso;
    }

    public void setSeleccionCurso(String seleccionCurso) {
        this.seleccionCurso = seleccionCurso;
    }

    public ArrayList<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(ArrayList<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public String getCursoEdicion() {
        return cursoEdicion;
    }

    public void setCursoEdicion(String cursoEdicion) {
        this.cursoEdicion = cursoEdicion;
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

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    
    
    
    
    @PostConstruct
    public void init(){
        
        setListaPaises((ArrayList<Pais>)universidadService.listarPaises());
        setListaCursos((ArrayList<Curso>)universidadService.listarCursos());
    }
    
    
    public CrearAsignaturaController() {
        
        
    }

    public String getPaisStr() {
        return paisStr;
    }

    public void setPaisStr(String paisStr) {
        this.paisStr = paisStr;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public ArrayList<ComentarioAsignatura> getListaComentarios() {
        return listaComentarios;
    }

    public ComentarioAsignatura getSelectedComentario() {
        return selectedComentario;
    }

    public void setSelectedComentario(ComentarioAsignatura selectedComentario) {
        this.selectedComentario = selectedComentario;
    }

    public ArrayList<ComentarioAsignatura> getSelectedComentarios() {
        return selectedComentarios;
    }

    public void setSelectedComentarios(ArrayList<ComentarioAsignatura> selectedComentarios) {
        this.selectedComentarios = selectedComentarios;
    }

    public ArrayList<ComentarioAsignatura> getFilteredComentarios() {
        return filteredComentarios;
    }

    public void setFilteredComentarios(ArrayList<ComentarioAsignatura> filteredComentarios) {
        this.filteredComentarios = filteredComentarios;
    }
    
    

    public void setListaComentarios(ArrayList<ComentarioAsignatura> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public boolean isCheckComentarios() {
        return checkComentarios;
    }

    public void setCheckComentarios(boolean checkComentarios) {
        this.checkComentarios = checkComentarios;
    }

    public boolean isCheckComentario() {
        return checkComentario;
    }

    public void setCheckComentario(boolean checkComentario) {
        this.checkComentario = checkComentario;
    }

    public boolean isEstadoComentario() {
        
        if(selectedComentario!=null){
            if(selectedComentario.getEstado().equals("aceptado"))
                estadoComentario=true;
            
        }
        return estadoComentario;
    }

    public void setEstadoComentario(boolean estadoComentario) {
        this.estadoComentario = estadoComentario;
    }

    

   

    public String getUniversidadStr() {
        return universidadStr;
    }

    public void setUniversidadStr(String universidadStr) {
        this.universidadStr = universidadStr;
    }

    
    
    
    public Pais getSelectedPais() {
        return selectedPais;
    }

    public void setSelectedPais(Pais selectedPais) {
        this.selectedPais = selectedPais;
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

    public ArrayList<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(ArrayList<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }
   
    
    
    
    
     public Universidad getSelectedUniversidad() {
        return selectedUniversidad;
    }

    public void setSelectedUniversidad(Universidad selectedUniversidad) {
        this.selectedUniversidad = selectedUniversidad;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    public String getCodAsignatura() {
        return codAsignatura;
    }

    public void setCodAsignatura(String codAsignatura) {
        this.codAsignatura = codAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public Float getCreditosAsignatura() {
        return creditosAsignatura;
    }

    public void setCreditosAsignatura(Float creditosAsignatura) {
        this.creditosAsignatura = creditosAsignatura;
    }

    public String getPeriodoAsignatura() {
        return periodoAsignatura;
    }

    public void setPeriodoAsignatura(String periodoAsignatura) {
        this.periodoAsignatura = periodoAsignatura;
    }

    

    public String getWebAsignatura() {
        return webAsignatura;
    }

    public void setWebAsignatura(String webAsignatura) {
        this.webAsignatura = webAsignatura;
    }

    public String getFacultadAsignatura() {
        return facultadAsignatura;
    }

    public void setFacultadAsignatura(String facultadAsignatura) {
        this.facultadAsignatura = facultadAsignatura;
    }

    public String getTitulacionAsignatura() {
        return titulacionAsignatura;
    }

    public void setTitulacionAsignatura(String titulacionAsignatura) {
        this.titulacionAsignatura = titulacionAsignatura;
    }

    public ArrayList<Asignatura> getSelectedAsignaturas() {
        return selectedAsignaturas;
    }

    public void setSelectedAsignaturas(ArrayList<Asignatura> selectedAsignaturas) {
        this.selectedAsignaturas = selectedAsignaturas;
    }

    public Asignatura getSelectedAsignatura() {
        return SelectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura SelectedAsignatura) {
        this.SelectedAsignatura = SelectedAsignatura;
    }

    public ArrayList<Asignatura> getFilteredAsignaturas() {
        return filteredAsignaturas;
    }

    public void setFilteredAsignaturas(ArrayList<Asignatura> filteredAsignaturas) {
        this.filteredAsignaturas = filteredAsignaturas;
    }
    
   public void onChangePais(){
       
       
       checkPaisStr=true;
       listaUniversidades=(ArrayList < Universidad >)universidadService.listarPorPais(paisStr);
       universidadStr="";
       checkDetalles=false;
       checkUniversidadStr=false;
       checkTabla=false;
       codAsignatura=null;
      nombreAsignatura="";
     creditosAsignatura=null;
      periodoAsignatura="";
      comentario="";
     webAsignatura="";
      facultadAsignatura="";
      titulacionAsignatura="";
      checkDetalles=false;
      checkComentario=false;
      checkComentarios=false;
       
   }

    public void onChangeUniversidad(){
        
        checkUniversidadStr=true;
        checkTabla=true;
        listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,universidadService.listarCursos().get(0).getCurso());
       checkDetalles=false;
       codAsignatura=null;
      nombreAsignatura=null;
     creditosAsignatura=null;
      periodoAsignatura="";
      comentario=null;
     webAsignatura=null;
      facultadAsignatura=null;
      titulacionAsignatura=null;
      disponible=true;
      curso="";
      checkDetalles=false;
      checkComentario=false;
      checkComentarios=false;
        
    }
    
    public String creaAsignatura(){
        Universidad uni;
        try{
        uni=universidadService.buscarUniversidad(universidadStr);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("no existe la universidad", FacesMessage.SEVERITY_ERROR);
            universidadStr="";
            listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,seleccionCurso);
            return null;
        }
            
            
        AsignaturaId id=new AsignaturaId(codAsignatura,universidadStr);
        Asignatura a=new Asignatura(id, uni);
        a.setCreditos(creditosAsignatura);
        a.setCurso(curso);
        a.setIdioma(idioma);
        a.setDisponible(disponible);
        a.setFacultad(facultadAsignatura);
        a.setNombreAsignatura(nombreAsignatura);
        a.setWebAsignatura(webAsignatura);
        a.setTitulacion(titulacionAsignatura);
        a.setPeriodo(periodoAsignatura);
        
        if(comentario.equals("")==false){
        ComentarioAsignatura cm=new ComentarioAsignatura();
        cm.setAsignatura(a);
        cm.setEstado("aceptado");
        cm.setFecha(Calendar.getInstance().getTime());
        cm.setTexto(comentario);
        cm.setUsuario(sessionController.getUser());
        a.getComentarioAsignaturas().add(cm);
        }
        
        try{
        asignaturaService.crearAsignatura(a);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            sessionController.creaMensaje("Ya existe la asignatura", FacesMessage.SEVERITY_ERROR);
            codAsignatura="";
            nombreAsignatura="";
            return null;
        }
        if(a.getCurso().equals(seleccionCurso))
        listaAsignaturas.add(a);
         sessionController.creaMensaje("asignatura creada correctamente", FacesMessage.SEVERITY_INFO);
         nombreAsignatura="";
         codAsignatura=null;
        //creditosAsignatura=null;
        //periodoAsignatura="";
        //titulacionAsignatura=null;
        //facultadAsignatura=null;
        comentario=null;
        webAsignatura=null;
        //idioma="";
        //curso="";
        //listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(universidadStr);
        
        return null;
    }
    
    public void verDetalles(){
        
       
        checkDetalles=true;
        checkComentarios=false;
        //checkUniversidadStr=false;
        
    }
    public String editar(){
        
        
        if(cursoEdicion.equals(SelectedAsignatura.getCurso())==false){
            listaAsignaturas.remove(SelectedAsignatura);
            SelectedAsignatura.setCurso(cursoEdicion);
        }
        
        
        if(comentario.equals("")==false){
            
            
            
            
            try{
                
                asignaturaService.actualizarAsignatura(SelectedAsignatura);
                
                
            }catch(InstanceNotFoundException ex){
                sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
           checkDetalles=false;
        checkUniversidadStr=false;
        checkTabla=false;
        checkPaisStr=false;
          listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,universidadService.listarCursos().get(0).getCurso());
          disponible=true;
          curso="";
          idioma="";
          comentario="";
        return null;
          
            }
            
            
            
            ComentarioAsignatura cm=new ComentarioAsignatura();
            cm.setAsignatura(SelectedAsignatura);
            cm.setEstado("aceptado");
            cm.setFecha(Calendar.getInstance().getTime());
            cm.setTexto(comentario);
            cm.setUsuario(sessionController.getUser());
            
            
            
            try{
                SelectedAsignatura=asignaturaService.buscarAsignatura(SelectedAsignatura.getId());
                SelectedAsignatura.getComentarioAsignaturas().add(cm);
                asignaturaService.actualizarAsignatura(SelectedAsignatura);
                
                
            }catch(InstanceNotFoundException ex){
              
            }
          sessionController.creaMensaje("Se ha editado correctamente", FacesMessage.SEVERITY_INFO);
          comentario=null;
          return null;
        }else{
            
            
        
            
        try{
            asignaturaService.actualizarAsignatura(SelectedAsignatura);
            //listaAsignaturas=(ArrayList < Asignatura >)asignaturaService.listarAsignaturasPorUniversidad(universidadStr);
        }catch(InstanceNotFoundException|RuntimeException ex){
            
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
           checkDetalles=false;
        checkUniversidadStr=false;
        checkTabla=false;
        checkPaisStr=false;
          listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,seleccionCurso);
          disponible=true;
          curso="";
          idioma="";
          comentario="";
          
        //paisStr="";
        //universidadStr="";
        return null;
        }
        
        
        
            checkDetalles=false;
            codAsignatura=null;
            creditosAsignatura=null;
            facultadAsignatura=null;
            comentario=null;
            nombreAsignatura=null;
            curso="";
            idioma="";
            disponible=true;
            sessionController.creaMensaje("Edición correcta", FacesMessage.SEVERITY_INFO);
           
        
    
       return null; 
        }
    }
    
    public void cerrar(){
        
        checkDetalles=false;
        
        comentario="";
        
    }
    
    
    public String eliminaAsignaturasLista(){
        
        if(selectedAsignaturas.isEmpty()==true){
            return null;
        }
            
        for (Asignatura a:selectedAsignaturas){
            try{
                asignaturaService.eliminarAsignatura(a);
            }catch(InstanceNotFoundException|RuntimeException ex){
              
           
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
           checkDetalles=false;
        checkTabla=false;
        checkUniversidadStr=false;
        checkPaisStr=false;
          listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,seleccionCurso);
          checkComentario=false;
          checkComentarios=false;
          
          
        //paisStr="";
        //universidadStr="";
        return null;
                 
                 
                 
                 
            }
            listaAsignaturas.remove(a);
        }
        
        sessionController.creaMensaje("se han eliminado correctamente las asignaturas",FacesMessage.SEVERITY_INFO);
        //listaAsignaturas=(ArrayList < Asignatura >)asignaturaService.listarAsignaturasPorUniversidad(universidadStr);
        //checkUniversidadStr=false;
        checkDetalles=false;
        checkComentarios=false;
        checkComentario=false;
        return null;
    }
        
     
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public boolean isCheckPaisStr() {
        return checkPaisStr;
    }

    public void setCheckPais(boolean checkPaisStr) {
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

    public boolean isCheckTabla() {
        return checkTabla;
    }

    public void setCheckTabla(boolean checkTabla) {
        this.checkTabla = checkTabla;
    }

    public String verComentarios(){
        
       
        
        checkComentarios=true;
        checkDetalles=false;
        try{
            
            SelectedAsignatura=asignaturaService.buscarAsignatura(SelectedAsignatura.getId());
        }catch(InstanceNotFoundException ex){
            
            sessionController.creaMensaje("No existe la asignatura", FacesMessage.SEVERITY_ERROR);
            listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr,seleccionCurso);
            checkComentario=false;
            return null;
            
        }
        listaComentarios=new ArrayList<>(SelectedAsignatura.getComentarioAsignaturas());
        
        return null;
        
    }
    
    public String eliminarComentarios(){
        
        //System.out.println("id "+ selectedComentario.getIdcomentario());
        
        
        
        if(selectedComentarios.isEmpty())
            return null;
        
        for(ComentarioAsignatura c:selectedComentarios){
            
            listaComentarios.remove(c);
            //c.setAsignatura(null);
            
            try{
                
                //SelectedAsignatura.getComentarioAsignaturas().remove(c);
                
                asignaturaService.eliminarComentario(c);
                
            }catch(RuntimeException ex){
                
               
               
                checkComentario=false;
                
                
                //checkComentarios=false;
                sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            
            
            if(selectedComentario!=null&&selectedComentario.getIdcomentario().equals(c.getIdcomentario()))
                checkComentario=false;
            
            
        }
         
        sessionController.creaMensaje("Comentarios eliminados", FacesMessage.SEVERITY_INFO);
            return null;
        
    }
    
    public void cerrarComentarios(){
        
        checkComentarios=false;
        checkComentario=false;
    }
    
    
    
    public void verComentario(){
        
        checkComentario=true;
        
        
    }
    
    
    public void cerrarComentario(){
        
        
        checkComentario=false;
    }
    
    
    public String aprobarOrechazar(){
        
        
        if(selectedComentario.getEstado().equals("aceptado")){
            selectedComentario.setEstado("pendiente");
        }else{
            selectedComentario.setEstado("aceptado");
            
            
            
        }
        
             try{
        asignaturaService.actualizarAsignatura(SelectedAsignatura);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
           checkComentario=false;
           checkComentarios=false;
           return null;
            
        }
            
        sessionController.creaMensaje("Comentario editado ", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
    
    public void cambioCurso(){
        
        
        listaAsignaturas=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidadYCurso(false,universidadStr, seleccionCurso);
        checkComentario=false;
        checkComentarios=false;
        checkDetalles=false;
        comentario="";
        facultadAsignatura="";
        titulacionAsignatura="";
        curso="";
        creditosAsignatura=null;
        cursoEdicion=null;
        periodoAsignatura="";
    }
    
    
}
