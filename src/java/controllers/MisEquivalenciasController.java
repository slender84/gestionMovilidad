/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

//import antlr.debug.Event;
import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.Mensaje;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import entities.Movilidad;
import entities.Universidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;
//import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import model.services.AsignaturaService;
import model.services.EquivalenciaService;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UniversidadService;
import model.services.UsuarioService;
import model.utils.ReportBean;
import net.sf.jasperreports.engine.JRException;





/**
 *
 * @author abc
 */
@ManagedBean
@ViewScoped
public class MisEquivalenciasController implements Serializable{

    @ManagedProperty(value="#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value="#{usuarioService}")
    private  UsuarioService usuarioService;
    
     @ManagedProperty(value="#{movilidadService}")
    private MovilidadService movilidadService;
    
    @ManagedProperty(value="#{asignaturaService}")
    private  AsignaturaService asignaturaService;
    
     @ManagedProperty(value="#{universidadService}")
    private  UniversidadService universidadService;
    
    @ManagedProperty(value="#{equivalenciaService}")
    private  EquivalenciaService equivalenciaService;
    
    @ManagedProperty(value="#{mensajeService}")
    private  MensajeService mensajeService;
    
    @ManagedProperty(value="#{reportBean}")
    private ReportBean reportBean;
    
   
    private ExternalContext context;
    private HttpSession session;
    private Movilidad selectedMovilidad;
    private Contrato selectedContrato;
    Contrato c;
    
    private Usuario user;
    
    Equivalencia equivalencia;
    
    
    private ArrayList<Asignatura> listaAsignaturasFic;
    private ArrayList<Asignatura>listaAsignaturasUniversidad;
   
    
    private ArrayList<Equivalencia> listaAuxEquivalencias=new ArrayList<Equivalencia>();
    private ArrayList<Equivalencia> listaAuxEquivalenciasComparado=new ArrayList<Equivalencia>();
    
    
    private Asignatura selectedAsignatura;
    private ArrayList<Asignatura>selectedAsignaturasFic;
    private ArrayList<Asignatura> selectedAsignaturasUni;
    
    private ArrayList<Asignatura>filteredAsignaturasFic;
    private ArrayList<Asignatura>filteredAsignaturasUni;
    
    private ArrayList<Equivalencia>selectedEquivalencias;
    
    private int j=0;
    private Float creditosA=new Float(0);
    private Float creditosB=new Float(0);
    
    private Float creditosComparadoA;
    private Float creditosComparadoB;
    
    
    private boolean verInfo;
    private boolean verConfirmar=true;
    
    private boolean ultimo;
    
    
    
    public MisEquivalenciasController() {
        
    }
    
    @PostConstruct
    public void init(){
        context=FacesContext.getCurrentInstance().getExternalContext();
        
        
       
       
      
        user=sessionController.getUser();
        
        
         
        if(context.getSessionMap().containsKey("movilidad")){
           
        selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
        context.getSessionMap().remove("movilidad");
            
        
        
        
        listaAsignaturasFic=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(true,"UDC");
        if(listaAsignaturasFic.isEmpty())
            listaAsignaturasFic=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(true,"Universidade da Coruña");
        listaAsignaturasUniversidad=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(true,selectedMovilidad.getUniversidad().getNombre());
            
        
           
        
         if(context.getSessionMap().containsKey("contrato")){
              
        selectedContrato=(Contrato)context.getSessionMap().get("contrato");
        context.getSessionMap().remove("contrato");
            
        try{
        c=equivalenciaService.buscarContrato(selectedContrato.getIdContrato());
        }catch(InstanceNotFoundException ex){
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        listaAuxEquivalencias.addAll(c.getEquivalencias());   //(ArrayList<Equivalencia>)equivalenciaService.listarEquivalenciasPorContrato(selectedContrato.getIdContrato());
        listaAuxEquivalenciasComparado.addAll(c.getEquivalencias()); //=(ArrayList<Equivalencia>)equivalenciaService.listarEquivalenciasPorContrato(selectedContrato.getIdContrato());
        //lo comparamos igual en la version b
              creditosA=equivalenciaService.totalCreditos(listaAuxEquivalencias)[0];
              creditosB=equivalenciaService.totalCreditos(listaAuxEquivalencias)[1];
             creditosComparadoA=creditosA;
             creditosComparadoB=creditosB;
             
        if(context.getSessionMap().containsKey("ultimo")){
           ultimo=true;
        context.getSessionMap().remove("ultimo");
        }
        
         }
        
        }
        
        
        else{
             try{
                  
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex){
                    
                    }
    }
         
       
    }

    public ReportBean getReportBean() {
        return reportBean;
    }

    public void setReportBean(ReportBean reportBean) {
        this.reportBean = reportBean;
    }

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
    
    
    

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   
    public MensajeService getMensajeService() {
        return mensajeService;
    }

    public void setMensajeService(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }


    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Asignatura> getSelectedAsignaturasFic() {
        return selectedAsignaturasFic;
    }

    public Asignatura getSelectedAsignatura() {
        return selectedAsignatura;
    }

    public void setSelectedAsignatura(Asignatura selectedAsignatura) {
        this.selectedAsignatura = selectedAsignatura;
    }

   

    public void setSelectedAsignaturasFic(ArrayList<Asignatura> selectedAsignaturasFic) {
        this.selectedAsignaturasFic = selectedAsignaturasFic;
    }

    public ArrayList<Asignatura> getFilteredAsignaturasFic() {
        return filteredAsignaturasFic;
    }

    public void setFilteredAsignaturasFic(ArrayList<Asignatura> filteredAsignaturasFic) {
        this.filteredAsignaturasFic = filteredAsignaturasFic;
    }

    public ArrayList<Asignatura> getFilteredAsignaturasUni() {
        return filteredAsignaturasUni;
    }

    public void setFilteredAsignaturasUni(ArrayList<Asignatura> filteredAsignaturasUni) {
        this.filteredAsignaturasUni = filteredAsignaturasUni;
    }

    
    
    public ArrayList<Equivalencia> getSelectedEquivalencias() {
        return selectedEquivalencias;
    }

    public void setSelectedEquivalencias(ArrayList<Equivalencia> selectedEquivalencias) {
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

    

    public ArrayList<Asignatura> getSelectedAsignaturasUni() {
        return selectedAsignaturasUni;
    }

    public void setSelectedAsignaturasUni(ArrayList<Asignatura> selectedAsignaturasUni) {
        this.selectedAsignaturasUni = selectedAsignaturasUni;
    }

    public ArrayList<Asignatura> getListaAsignaturasFic() {
        return listaAsignaturasFic;
    }

    public void setListaAsignaturasFic(ArrayList<Asignatura> listaAsignaturasFic) {
        this.listaAsignaturasFic = listaAsignaturasFic;
    }

    public ArrayList<Asignatura> getListaAsignaturasUniversidad() {
        return listaAsignaturasUniversidad;
    }

    public void setListaAsignaturasUniversidad(ArrayList<Asignatura> listaAsignaturasUniversidad) {
        this.listaAsignaturasUniversidad = listaAsignaturasUniversidad;
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

    public boolean isUltimo() {
        return ultimo;
    }

    public void setUltimo(boolean ultimo) {
        this.ultimo = ultimo;
    }

  
    

    
    
   

    public boolean isVerInfo() {
        return verInfo;
    }

    public void setVerInfo(boolean verInfo) {
        this.verInfo = verInfo;
    }

    public boolean isVerConfirmar() {
        return verConfirmar;
    }

    public void setVerConfirmar(boolean verConfirmar) {
        this.verConfirmar = verConfirmar;
    }

    
     public String asignaturasTotales(){
       
          equivalencia=new Equivalencia();
          MiembroGrupoAsignaturaA ma;
          MiembroGrupoAsignaturaB mb;
        
        DataTable dataTable=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("formEquivalenciaFic:tablaFic");
        DataTable dataTable2=(DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("formEquivalenciaFic:tablaUniversidad");
        
        
        selectedAsignaturasFic=(ArrayList < Asignatura >)dataTable.getSelection();
        selectedAsignaturasUni=(ArrayList<Asignatura>)dataTable2.getSelection();
        
        if(selectedAsignaturasFic.isEmpty()){
            
            sessionController.creaMensaje("No hay asignaturas de origen", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(selectedAsignaturasUni.isEmpty()){
            sessionController.creaMensaje("No hay asignaturas de destino", FacesMessage.SEVERITY_ERROR);
            return null;
        }
       
        Float creditosAaux=new Float(0);
        Float creditosBaux=new Float(0);
        
        for(Asignatura a:selectedAsignaturasFic){
       
        ma=new MiembroGrupoAsignaturaA(a, equivalencia);
        equivalencia.getMiembroGrupoAsignaturaAs().add(ma);
        
                  creditosAaux=creditosAaux+a.getCreditos();                             // con cascade save-update no hace falta salvar el miembro_grupo_asignaturas
        
        }
        
        for(Asignatura a:selectedAsignaturasUni){
            
        mb=new MiembroGrupoAsignaturaB(a, equivalencia);
        equivalencia.getMiembroGrupoAsignaturaBs().add(mb);
                   //creditosB=creditosB+a.getCreditos();
                     creditosBaux=creditosBaux+a.getCreditos();
        }
         //System.out.println("tamaño lista "+ listaAuxEquivalencias.size()); 
        
        
        
        
         if(equivalenciaService.equivalenciaRepetida(equivalencia, listaAuxEquivalencias)==true){
            sessionController.creaMensaje("Equivalencia repetida: descartada", FacesMessage.SEVERITY_ERROR);
        return null;
        }
         creditosA=creditosAaux+creditosA;
         creditosB=creditosBaux+creditosB;
        
        equivalencia.setVisible(false);
        equivalencia.setIdequivalencia(j);
        
       
        
        
            listaAuxEquivalencias.add(equivalencia);
            j++;
            
            
            
            return null;      
         
         
         
    }
    
    public String confirmarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            sessionController.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        Contrato c=new Contrato();
        c.setMovilidad(selectedMovilidad);
        c.setFecha(Calendar.getInstance().getTime());
        //Set<Equivalencia>setE=new HashSet<Equivalencia>(listaAuxEquivalencias); no hace falta ya que se van a añadir luego
        //c.setEquivalencias(setE);
        c.setEstado("pendiente");
        
        try{
        equivalenciaService.confirmarContrato(listaAuxEquivalencias, c);
        }catch(RuntimeException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
            
        
        sessionController.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
        try{
        Mensaje m=new Mensaje(usuarioService.buscarUsuario("admin"), user, Calendar.getInstance().getTime(),"contrato creado", "el usuario "+user.getLogin()+" ha creado un contrato",false,true,false);
        mensajeService.enviarMensaje(m);
        }catch(InstanceNotFoundException ex){
            sessionController.creaMensaje("Usuario inexistente", FacesMessage.SEVERITY_ERROR);
        }
        verConfirmar=false;
        
        return null;
        
    }
    
    public String editarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            sessionController.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ArrayList<Equivalencia>listaCopia=null;
         try{
        listaCopia=equivalenciaService.editarContrato(listaAuxEquivalencias, c);
        }catch(InstanceNotFoundException|RuntimeException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        
        
        for(Equivalencia e:listaCopia){
            try{
                equivalenciaService.eliminarEquivalencia(e);
            }catch(org.springframework.dao.DataIntegrityViolationException ex){
                
            }
        }
        
        sessionController.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
     Mensaje m=new Mensaje(usuarioService.buscarUsuario("admin"), user, Calendar.getInstance().getTime(),"contrato modificado", "el usuario "+user.getLogin()+" ha modificado un contrato",false,true,false);
     mensajeService.enviarMensaje(m);
     }catch(Exception ex){
         
     }
        
        verConfirmar=false;
        return null;
    
    }
    
    
 
    
     public String  crearContratoDesdeAceptado(){
       if(listaAuxEquivalencias.isEmpty()){
            
            sessionController.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        Contrato cNuevo=new Contrato();
        cNuevo.setFecha(Calendar.getInstance().getTime());
        cNuevo.setMovilidad(selectedMovilidad);
        cNuevo.setEstado("pendiente");
        
         try{
             
         equivalenciaService.crearContratoDesdeAceptado(listaAuxEquivalencias, c, cNuevo);
             
        }catch(InstanceNotFoundException|RuntimeException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        
        
       
        
     sessionController.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
     Mensaje m=new Mensaje(usuarioService.buscarUsuario("admin"), user, Calendar.getInstance().getTime(),"contrato creado", "el usuario "+user.getLogin()+" ha creado un contrato",false,true,false);
     mensajeService.enviarMensaje(m);
     }catch(InstanceNotFoundException ex){
         
     }
        verConfirmar=false;
        return null;
    
  }
    
    
    public String eliminaEquivalenciasLista(){
        if(selectedEquivalencias.isEmpty()==false){
            
           for(int i=0;i<selectedEquivalencias.size();i++){
          
               Iterator it=selectedEquivalencias.get(i).getMiembroGrupoAsignaturaAs().iterator();
               while(it.hasNext()){
                   MiembroGrupoAsignaturaA mA=(MiembroGrupoAsignaturaA)it.next();
                   creditosA=creditosA-mA.getAsignatura().getCreditos();
               }
               
               it=selectedEquivalencias.get(i).getMiembroGrupoAsignaturaBs().iterator();
               while(it.hasNext()){
                   MiembroGrupoAsignaturaB mB=(MiembroGrupoAsignaturaB)it.next();
                   creditosB=creditosB-mB.getAsignatura().getCreditos();
               }
               
          listaAuxEquivalencias.remove(selectedEquivalencias.get(i));
          
          
          
        }
        }
        
        return null;
        
    }
    
    
    
    public void cerrarDetallesAsign(){
        
        //verInfo=false;
    }
   
  /* @PreDestroy 
   public void destroy(){
       if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("ultimo")){
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("ultimo"); 
       }
   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("movilidad");
   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("contrato");
       System.out.println("destruyendo");     
        
        
    }
    */
    
    public String nuevoAceptado(){
        
       
        
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
         
         return ("crearContratoDesdeAceptado.xhtml?faces-redirect=true");
         
     
        
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
    
   
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private String seleccionCurso;
    private ArrayList<ComentarioAsignatura> listaComentariosAsignatura;
    private String seleccionCursoFic;
    
    
    private boolean checkDialog2;
    private boolean checkDialog3;
    
    private boolean checkDialog2Fic;
    private boolean checkFic;
    
    
    private String codAsignatura;
    private String nombreAsignatura;
    private String curso;
    private String idioma;
    private Float creditos;
    private String facultad;
    private String titulacion;
    private boolean disponible=true;
    private String periodo;
    private String webAsignatura;
    
    
    private Asignatura selectedAsignatura2;
    
    
    private ArrayList<Asignatura> selectedAsignaturas=new ArrayList<>();

    public ArrayList<Asignatura> getSelectedAsignaturas() {
        return selectedAsignaturas;
    }

    public void setSelectedAsignaturas(ArrayList<Asignatura> selectedAsignaturas) {
        this.selectedAsignaturas = selectedAsignaturas;
    }

    public Asignatura getSelectedAsignatura2() {
        return selectedAsignatura2;
    }

    public void setSelectedAsignatura2(Asignatura selectedAsignatura2) {
        this.selectedAsignatura2 = selectedAsignatura2;
    }

    public boolean isCheckDialog2() {
        return checkDialog2;
    }

    public void setCheckDialog2(boolean checkDialog2) {
        this.checkDialog2 = checkDialog2;
    }

    public boolean isCheckDialog3() {
        return checkDialog3;
    }

    public void setCheckDialog3(boolean checkDialog3) {
        this.checkDialog3 = checkDialog3;
    }

    public boolean isCheckDialog2Fic() {
        return checkDialog2Fic;
    }

    public boolean isCheckFic() {
        return checkFic;
    }

    public void setCheckFic(boolean checkFic) {
        this.checkFic = checkFic;
    }
    
    

    public void setCheckDialog2Fic(boolean checkDialog2Fic) {
        this.checkDialog2Fic = checkDialog2Fic;
    }

    
    
    
    
    
    
    
    public String getSeleccionCurso() {
        return seleccionCurso;
    }

    public void setSeleccionCurso(String seleccionCurso) {
        this.seleccionCurso = seleccionCurso;
    }

    public ArrayList<ComentarioAsignatura> getListaComentariosAsignatura() {
        return listaComentariosAsignatura;
    }

    public void setListaComentariosAsignatura(ArrayList<ComentarioAsignatura> listaComentariosAsignatura) {
        this.listaComentariosAsignatura = listaComentariosAsignatura;
    }

    public String getSeleccionCursoFic() {
        return seleccionCursoFic;
    }

    public void setSeleccionCursoFic(String seleccionCursoFic) {
        this.seleccionCursoFic = seleccionCursoFic;
    }

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

    public Float getCreditos() {
        return creditos;
    }

    public void setCreditos(Float creditos) {
        this.creditos = creditos;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getWebAsignatura() {
        return webAsignatura;
    }

    public void setWebAsignatura(String webAsignatura) {
        this.webAsignatura = webAsignatura;
    }
    
    
    
    public void crearFic(){
        
        checkFic=true;
        crearAsignatura();
        
    }
    
    public void crearUni(){
        
        checkFic=false;
       
        crearAsignatura();
    }
    
    public void editarFic(){
        
        checkFic=true;
        editarAsignatura();
    }
    
    public void editarUni(){
        
        checkFic=false;
        editarAsignatura();
    }
    
    public void crearAsignatura(){
        checkDialog2=true;
        checkDialog2Fic=false;
        
        checkDialog3=false;
         nombreAsignatura="";
         codAsignatura=null;
        creditos=null;
        periodo="";
        titulacion=null;
        facultad=null;
        idioma="";
        webAsignatura=null;
        curso="";
    }
    
    
    
    public void detallesAsign(){
         
        
        listaComentariosAsignatura=(ArrayList<ComentarioAsignatura>)asignaturaService.listarComentariosPorAsignatura(selectedAsignatura.getId());
        checkDialog2Fic=false;
       checkDialog2=false;
        checkDialog3=false;
        
    }
    
    
    public String editarAsignatura(){
        
        
        if(checkFic==false){
        
        if (selectedAsignaturasUni==null){
            
            checkDialog2=false;
            checkDialog3=true;
            selectedAsignatura2=null;
            //resetAsignatura(selectedAsignatura2);
            return null;
        }
        
        if (selectedAsignaturasUni.size()>1||selectedAsignaturasUni.isEmpty()){
            checkDialog2Fic=false;
            checkDialog2=false;
            checkDialog3=true;
             //resetAsignatura(selectedAsignatura2);
            selectedAsignatura2=null;
            return null;
        }
        
        
        selectedAsignatura2=selectedAsignaturasUni.get(0);
        checkDialog2=false;
        checkDialog3=true;
        return null;
        }
        else{
            
            
            if (selectedAsignaturasFic==null){
            
            checkDialog2=false;
            checkDialog3=true;
            selectedAsignatura2=null;
            //resetAsignatura(selectedAsignatura2);
            return null;
        }
        
        if (selectedAsignaturasFic.size()>1||selectedAsignaturasFic.isEmpty()){
           
            checkDialog2=false;
            checkDialog3=true;
             //resetAsignatura(selectedAsignatura2);
            selectedAsignatura2=null;
            return null;
        }
        
        
        selectedAsignatura2=selectedAsignaturasFic.get(0);
        checkDialog2=false;
        checkDialog3=true;
        return null;
         
        }
    }
    
    
    public String enviarAsignatura(){
        
        Asignatura a=new Asignatura();
        
        if(checkFic==false){
            
            AsignaturaId id=new AsignaturaId(codAsignatura,selectedMovilidad.getUniversidad().getNombre());
            a=new Asignatura(id, selectedMovilidad.getUniversidad());
            
        }else{
            
            
        Universidad uniFic=null;
        try{
         uniFic=universidadService.buscarUniversidad("Universidade da Coruña");
         
         a.setUniversidad(uniFic);
         AsignaturaId id=new AsignaturaId(codAsignatura, uniFic.getNombre());
         a.setId(id);
        }catch(InstanceNotFoundException ex){
            try{
            uniFic=universidadService.buscarUniversidad("UDC");
            a.setUniversidad(uniFic);
            AsignaturaId id2=new AsignaturaId(codAsignatura,uniFic.getNombre());
            a.setId(id2);
            }catch(InstanceNotFoundException ex2){
                
            }
        }
        }
            
        System.out.println("curso es "+curso);
        a.setCreditos(creditos);
        a.setCurso(curso);
        a.setIdioma(idioma);
        a.setDisponible(disponible);
        a.setFacultad(facultad);
        a.setNombreAsignatura(nombreAsignatura);
        a.setWebAsignatura(webAsignatura);
        a.setTitulacion(titulacion);
        a.setPeriodo(periodo);
        
        
        
        try{
        asignaturaService.crearAsignatura(a);
        }catch(org.springframework.dao.DataIntegrityViolationException ex){
            sessionController.creaMensaje("Ya existe la asignatura", FacesMessage.SEVERITY_ERROR);
            codAsignatura="";
            nombreAsignatura="";
            return null;
        }
       
        
         sessionController.creaMensaje("asignatura creada correctamente", FacesMessage.SEVERITY_INFO);
         nombreAsignatura="";
         codAsignatura=null;
        creditos=null;
        periodo="";
        titulacion=null;
        facultad=null;
        idioma="";
        webAsignatura=null;
        
        if(checkFic==false){
        //listaAsignaturasUniversidad=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(true,selectedMovilidad.getUniversidad().getNombre(),seleccionCurso);
        curso="";
        listaAsignaturasUniversidad.add(a);
        return null;
        }
        else listaAsignaturasFic.add(a);
        curso="";
    
        return null;
    }
    
    public String confirmarEdicion(){
        
        try{
            
            
            
            asignaturaService.actualizarAsignatura(selectedAsignatura2);
            
            
        }catch(InstanceNotFoundException ex){
            
            sessionController.creaMensaje("Se ha producido un error", FacesMessage.SEVERITY_ERROR);
            return null;
            
        }
        
       
        sessionController.creaMensaje("Asignatura editada", FacesMessage.SEVERITY_INFO);
        //selectedAsignatura2=null;
        return null;
       
    }
    
    
    
    
    
    
    
    public void resetAsignatura(Asignatura a){
        
        if(a!=null){
        a.setCreditos(null);
        a.setCurso("");
        a.setFacultad("");
        a.getId().setCodAsignatura("");
        a.setIdioma("");
        a.setNombreAsignatura("");
        a.setPeriodo("");
        a.setTitulacion("");
        a.setUniversidad(null);
        }
        
        
        
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     
    
}
