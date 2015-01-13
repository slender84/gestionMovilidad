
package controllers;


import entities.Cursoacademico;
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
public class CursoAcademicoController implements Serializable{

    
    @ManagedProperty(value = "#{universidadService}")
    private UniversidadService universidadService;
    
    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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
        
        setListaCursoAcademico((ArrayList < Cursoacademico >)universidadService.listarCursosAcademicos());
        
    }
    
    
    public String creaCursoAcademico(){
      
      if(cursoAcademico.substring(0, 4).compareTo(cursoAcademico.substring(5, 9))!=-1){
          
          beanUtilidades.creaMensaje("el curso académico no puede empezar más tarde de lo que acaba",FacesMessage.SEVERITY_ERROR);
          return null;
      }
      
      try{
          Cursoacademico c=new Cursoacademico(cursoAcademico);
          cursoAcademico="";
          universidadService.crearCursoAcademico(c);
          listaCursoAcademico.add(c);
      }catch(org.springframework.dao.DataIntegrityViolationException ex){
          
          beanUtilidades.creaMensaje("El año "+cursoAcademico+" ya existe", FacesMessage.SEVERITY_ERROR);
          return null;
      }
        beanUtilidades.creaMensaje("curso creado correctamente", FacesMessage.SEVERITY_INFO);
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
            beanUtilidades.creaMensaje("se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
            
    }
         cursoAcademico="";
         setListaCursoAcademico((ArrayList < Cursoacademico >)universidadService.listarCursosAcademicos());
        beanUtilidades.creaMensaje("curso académico eliminado correctamente", FacesMessage.SEVERITY_INFO);
        return null;
    }
    
    
    
    
}
