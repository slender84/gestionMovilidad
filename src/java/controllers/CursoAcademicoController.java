
package controllers;


import entities.Curso;
import entities.Cursoacademico;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import model.services.UniversidadService;



@ManagedBean
@RequestScoped
public class CursoAcademicoController implements Serializable{

    
    @ManagedProperty(value = "#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   

    
    
    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
    
    
    
    public CursoAcademicoController() {
    }
    
     private String cursoAcademico;
    private ArrayList<Cursoacademico> listaCursoAcademico;
    

    

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {
        this.cursoAcademico = cursoAcademico;
    }

    public ArrayList<Cursoacademico> getListaCursoAcademico() {
        return listaCursoAcademico;
    }

    public void setListaCursoAcademico(ArrayList<Cursoacademico> listaCursoAcademico) {
        this.listaCursoAcademico = listaCursoAcademico;
    }
    
    @PostConstruct
    public void init(){
        
        HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(request.getRequestURI().equals(request.getContextPath()+"/admin/crearCurso.xhtml"))
            setListaCursos((ArrayList<Curso>)universidadService.listarCursos());
        
        setListaCursoAcademico((ArrayList < Cursoacademico >)universidadService.listarCursosAcademicos());
        
    }
    
    
    public String creaCursoAcademico(){
      
      if(cursoAcademico.substring(0, 4).compareTo(cursoAcademico.substring(5, 9))!=-1){
          
          sessionController.creaMensaje("el curso académico no puede empezar más tarde de lo que acaba",FacesMessage.SEVERITY_ERROR);
          return null;
      }
      
      try{
          Cursoacademico c=new Cursoacademico(cursoAcademico);
          cursoAcademico="";
          universidadService.crearCursoAcademico(c);
          listaCursoAcademico.add(c);
      }catch(org.springframework.dao.DataIntegrityViolationException ex){
          
          sessionController.creaMensaje("El año "+cursoAcademico+" ya existe", FacesMessage.SEVERITY_ERROR);
          return null;
      }
        sessionController.creaMensaje("curso creado correctamente", FacesMessage.SEVERITY_INFO);
        cursoAcademico="";
        //setListaCursoAcademico((ArrayList < Cursoacademico >)universidadService.listaCursosAcademicos());
        
      return null;
    }
      
    
    
    public String eliminaCursoAcademico(){
        
        Cursoacademico c=new Cursoacademico(cursoAcademico);
         try{
        universidadService.eliminarCursoAcademico(c);
        listaCursoAcademico.remove(c);
    }catch(RuntimeException ex){
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
            
    }
         cursoAcademico="";
         setListaCursoAcademico((ArrayList < Cursoacademico >)universidadService.listarCursosAcademicos());
        sessionController.creaMensaje("curso académico eliminado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    private String curso;
    private ArrayList<Curso> listaCursos;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

   

    public ArrayList<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(ArrayList<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }
    
    public String crearCurso(){
        Curso c=new Curso(curso);
        try{
            
            universidadService.crearCurso(c);
            listaCursos.add(c);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
        
        sessionController.creaMensaje("el curso ya existe", FacesMessage.SEVERITY_ERROR);
        return null;
        
    }
        sessionController.creaMensaje("curso creado", FacesMessage.SEVERITY_INFO);
        curso="";
        return null;
        
    }
    
    public String eliminarCurso(){
        Curso c=new Curso(curso);
        try{
            
            universidadService.eliminarCurso(c);
            listaCursos.remove(c);
        }catch(RuntimeException ex){
            
            sessionController.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            
        }
        listaCursos=(ArrayList<Curso>)universidadService.listarCursos();
        sessionController.creaMensaje("curso eliminado", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
}
