

package controllers;

//import antlr.debug.Event;
import entities.Asignatura;
import entities.ComentarioAsignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.Mensaje;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import model.services.AsignaturaService;
import model.services.EquivalenciaService;
import model.services.MensajeService;
import model.utils.EquivalenciaRevisada;
import model.utils.ReportBean;
import model.utils.email;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.mail.EmailException;



/**
 *
 * @author abc
 */
@ManagedBean
@ViewScoped
public class EquivalenciasController implements Serializable{

    
    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
   
    @ManagedProperty(value="#{equivalenciaService}")
    private  EquivalenciaService equivalenciaService;
    
    @ManagedProperty(value="#{mensajeService}")
    private  MensajeService mensajeService;
    
    @ManagedProperty(value = "#{reportBean}")
    private ReportBean reportBean;

    @ManagedProperty(value="#{asignaturaService}")
    private AsignaturaService asignaturaService;
    
    
   
    private ExternalContext context;
    private HttpSession session;
    private Movilidad selectedMovilidad;
    private Contrato selectedContrato;
    private Contrato contratoComparado;
    private Usuario user;
    
    private Float creditosA;
    private Float creditosB;
    private Float creditosComparadoA;
    private Float creditosComparadoB;
    
    Equivalencia equivalencia;
    
    private Asignatura selectedAsignatura;
    
    
    private ArrayList<ComentarioAsignatura> listaComentariosAsignatura;
    
    private ArrayList<Equivalencia> listaAuxEquivalencias=new ArrayList<Equivalencia>();
    private ArrayList<Equivalencia> listaAuxEquivalenciasComparado=new ArrayList<Equivalencia>();
    
    private ArrayList<EquivalenciaRevisada> equivalenciasRevisadas;
    private ArrayList<EquivalenciaRevisada> equivalenciasRevisadasComparado;
    
    
    private ArrayList<EquivalenciaRevisada>selectedEquivalencias;
    private ArrayList<Equivalencia> selectedEquivalenciasSimples;
    
    private static int j=0;
    
    
    private String apruebaOrechaza;
    
    public EquivalenciasController() {
        
    }
    
    @PostConstruct
    public void init(){
        
        
        
        context=FacesContext.getCurrentInstance().getExternalContext();
        
        
           
          
           if(context.getSessionMap().containsKey("movilidad")&&context.getSessionMap().containsKey("contrato")){
           user=sessionController.getUser();
           selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
           selectedContrato=(Contrato)context.getSessionMap().get("contrato");
           //context.getSessionMap().remove("contrato");
           //context.getSessionMap().remove("movilidad");
              
                
           try{
               Integer iC=selectedContrato.getIdContrato();
               selectedContrato=null;
           selectedContrato=equivalenciaService.buscarContrato(iC);
           }catch(InstanceNotFoundException ex){
               
           }
           
           
           if(selectedContrato!=null){
              
           listaAuxEquivalencias.addAll(selectedContrato.getEquivalencias());
            creditosA=equivalenciaService.totalCreditos(listaAuxEquivalencias)[0];
            creditosB=equivalenciaService.totalCreditos(listaAuxEquivalencias)[1];
             
            
            
            
           if(context.getSessionMap().containsKey("contratoComparado")){
        contratoComparado=(Contrato)context.getSessionMap().get("contratoComparado");
        //context.getSessionMap().remove("contratoComparado");
             try{
                 Integer iC2=contratoComparado.getIdContrato();
                 contratoComparado=null;
        contratoComparado=equivalenciaService.buscarContrato(iC2);
             }catch(InstanceNotFoundException ex3){
                 
             }
             
             if(contratoComparado!=null){
        listaAuxEquivalenciasComparado.addAll(contratoComparado.getEquivalencias());
        equivalenciasRevisadas=equivalenciaService.compararEquivalencias(listaAuxEquivalencias, listaAuxEquivalenciasComparado);
        equivalenciasRevisadasComparado=equivalenciaService.compararEquivalencias(listaAuxEquivalenciasComparado, listaAuxEquivalencias);
        creditosComparadoA=equivalenciaService.totalCreditos(listaAuxEquivalenciasComparado)[0];
        creditosComparadoB=equivalenciaService.totalCreditos(listaAuxEquivalenciasComparado)[1];
           }else{
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
             
           }
               
           }
         
           }else{
           
           try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
           
           
           }
           
           
        }
           
       else{
               
               
            try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/verMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
    }
       } 

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    
    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }

    public ReportBean getReportBean() {
        return reportBean;
    }

    public void setReportBean(ReportBean reportBean) {
        this.reportBean = reportBean;
    }
    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<EquivalenciaRevisada> getEquivalenciasRevisadas() {
        return equivalenciasRevisadas;
    }

    public void setEquivalenciasRevisadas(ArrayList<EquivalenciaRevisada> equivalenciasRevisadas) {
        this.equivalenciasRevisadas = equivalenciasRevisadas;
    }

    public ArrayList<EquivalenciaRevisada> getEquivalenciasRevisadasComparado() {
        return equivalenciasRevisadasComparado;
    }

    public void setEquivalenciasRevisadasComparado(ArrayList<EquivalenciaRevisada> equivalenciasRevisadasComparado) {
        this.equivalenciasRevisadasComparado = equivalenciasRevisadasComparado;
    }

    public ArrayList<ComentarioAsignatura> getListaComentariosAsignatura() {
        return listaComentariosAsignatura;
    }

    public void setListaComentariosAsignatura(ArrayList<ComentarioAsignatura> listaComentariosAsignatura) {
        this.listaComentariosAsignatura = listaComentariosAsignatura;
    }
    
    
    
   
    public ArrayList<EquivalenciaRevisada> getSelectedEquivalencias() {
        return selectedEquivalencias;
    }

    public void setSelectedEquivalencias(ArrayList<EquivalenciaRevisada> selectedEquivalencias) {
        this.selectedEquivalencias = selectedEquivalencias;
    }

    public ArrayList<Equivalencia> getListaAuxEquivalencias() {
        return listaAuxEquivalencias;
    }

    public void setListaAuxEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias) {
        this.listaAuxEquivalencias = listaAuxEquivalencias;
    }

    public ArrayList<Equivalencia> getListaAuxEquivalenciasComparado() {
        return listaAuxEquivalenciasComparado;
    }

    public void setListaAuxEquivalenciasComparado(ArrayList<Equivalencia> listaAuxEquivalenciasComparado) {
        this.listaAuxEquivalenciasComparado = listaAuxEquivalenciasComparado;
    }

    public Movilidad getSelectedMovilidad() {
        return selectedMovilidad;
    }

    public void setSelectedMovilidad(Movilidad selectedMovilidad) {
        this.selectedMovilidad = selectedMovilidad;
    }

    public Contrato getSelectedContrato() {
        return selectedContrato;
    }

    public void setSelectedContrato(Contrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }

    public Contrato getContratoComparado() {
        return contratoComparado;
    }

    public void setContratoComparado(Contrato contratoComparado) {
        this.contratoComparado = contratoComparado;
    }

    public String getApruebaOrechaza() {
        return apruebaOrechaza;
    }

    public void setApruebaOrechaza(String apruebaOrechaza) {
        this.apruebaOrechaza = apruebaOrechaza;
    }

    public Float getCreditosA() {
        return creditosA;
    }

    public void setCreditosA(Float creditosA) {
        this.creditosA = creditosA;
    }

    public Float getCreditosB() {
        return creditosB;
    }

    public void setCreditosB(Float creditosB) {
        this.creditosB = creditosB;
    }

    public Float getCreditosComparadoA() {
        return creditosComparadoA;
    }

    public void setCreditosComparadoA(Float creditosComparadoA) {
        this.creditosComparadoA = creditosComparadoA;
    }

    public Float getCreditosComparadoB() {
        return creditosComparadoB;
    }

    public void setCreditosComparadoB(Float creditosComparadoB) {
        this.creditosComparadoB = creditosComparadoB;
    }

    public Asignatura getSelectedAsignatura() {
        return selectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura selectedAsignatura) {
        this.selectedAsignatura = selectedAsignatura;
    }
    
    
    

    public ArrayList<Equivalencia> getSelectedEquivalenciasSimples() {
        return selectedEquivalenciasSimples;
    }

    public void setSelectedEquivalenciasSimples(ArrayList<Equivalencia> selectedEquivalenciasSimples) {
        this.selectedEquivalenciasSimples = selectedEquivalenciasSimples;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
    
    public String publicarEquivalencia(){
        
        if(selectedEquivalencias.isEmpty()){
            return null;
        }
        
        for(EquivalenciaRevisada e:selectedEquivalencias){
            e.getEquivalencia().setVisible(true);
           
                try{
                equivalenciaService.actualizarEquivalencia(e.getEquivalencia());
            }catch(RuntimeException ex){
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        sessionController.creaMensaje("Las equivalencias han sido publicadas", FacesMessage.SEVERITY_INFO);
        
        
        return null;
    }
    

   
    public String publicarEquivalenciaSimple(){
        
        if(selectedEquivalenciasSimples.isEmpty()){
            return null;
        }
        
        
        
        
        for(Equivalencia e:selectedEquivalenciasSimples){
            e.setVisible(true);
           
                 try{
                equivalenciaService.actualizarEquivalencia(e);
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        sessionController.creaMensaje("Las equivalencias han sido publicadas", FacesMessage.SEVERITY_INFO);
        
        
        return null;
    }
    
    
    public String noPublicar(){
        
         if(selectedEquivalencias.isEmpty()){
            return null;
        }
        
        for(EquivalenciaRevisada e:selectedEquivalencias){
            e.getEquivalencia().setVisible(false);
            
                 try{
                equivalenciaService.actualizarEquivalencia(e.getEquivalencia());
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        sessionController.creaMensaje("Las equivalencias seleccionadas ya no son públicas", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
     public String noPublicarSimple(){
        
         if(selectedEquivalenciasSimples.isEmpty()){
            return null;
        }
        
        for(Equivalencia e:selectedEquivalenciasSimples){
            e.setVisible(false);
            
            try{
                equivalenciaService.actualizarEquivalencia(e);
            }catch(RuntimeException ex){
                 try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
            }
            
        }
        sessionController.creaMensaje("Las equivalencias seleccionadas ya no son públicas", FacesMessage.SEVERITY_INFO);
        return null;
        
        
    }
    
    
    public String cambiarEstadoContrato(){
        
        //try{
        //selectedContrato=equivalenciaService.buscarContrato(selectedContrato.getIdContrato());
        //}catch(InstanceNotFoundException ex){
          //  try{
            //FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            //}catch(IOException ex2){
                    
              //      }
        //}
        if(apruebaOrechaza.equals(selectedContrato.getEstado()))
            return null;
        
        selectedContrato.setEstado(apruebaOrechaza);
        
            try{
            equivalenciaService.modificarContrato(selectedContrato);
            }catch(InstanceNotFoundException|RuntimeException ex){
                
                try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/admin/error.xhtml");
            }catch(IOException ex2){
                    
                    }
                
            }
        sessionController.creaMensaje("contrato modificado correctamente, se le ha enviado un mensaje al usuario", FacesMessage.SEVERITY_INFO);
        Mensaje m=new Mensaje(selectedMovilidad.getUsuario(), user, Calendar.getInstance().getTime(), "cambio de estado de contrato", "El estado de un contrato ahora es:"+apruebaOrechaza, false,true,false);
        mensajeService.enviarMensaje(m);
        
        try{
            
            email.enviarEmailAdmin(selectedMovilidad.getUsuario().getLogin(), sessionController.getCorreoConf(),"cambio de estado de contrato" , "El estado de un contrato ahora es:"+apruebaOrechaza);
            
        }catch (EmailException ex){
            
            sessionController.creaMensaje("error al enviar el correo", FacesMessage.SEVERITY_ERROR);
            
        }
        
        
        
        return null;
    }
    
     public void PDF(ActionEvent event)throws JRException,IOException{
        
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd-MM-yyyy HH:mm");
        
        Map parametros= new HashMap();
        parametros.put("pais",selectedMovilidad.getUniversidad().getPais().getNombre());
        parametros.put("universidad", selectedMovilidad.getUniversidad().getNombre());
        
        parametros.put("Inicio",sdf.format(selectedMovilidad.getFechaInicio()) );
        parametros.put("Fin",sdf.format(selectedMovilidad.getFechaFin()));
        parametros.put("Estado", selectedContrato.getEstado());
        parametros.put("creditosA", creditosA.toString());
        parametros.put("creditosB", creditosB.toString());
        parametros.put("usuario", selectedMovilidad.getUsuario().getLogin());
        parametros.put("curso",selectedMovilidad.getCursoacademico().getCursoAcademico());
        parametros.put("fecha",sdf2.format(selectedContrato.getFecha()));
        reportBean.PDF(event,reportBean.crearEquivalenciasJasper(listaAuxEquivalencias),parametros);
        
    }
    
     
     public void detallesAsign(){
         
        
        listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosPorAsignatura(selectedAsignatura.getId());
        
        
    }
     
}
     
    
