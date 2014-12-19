/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

//import antlr.debug.Event;
import entities.Asignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.Mensaje;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import entities.Movilidad;
import entities.Usuario;
import exceptions.ContratoNotFoundException;
import exceptions.UsuarioNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
//import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import model.services.AsignaturaService;
import model.services.EquivalenciaService;
import model.services.MensajeService;
import model.services.MovilidadService;
import model.services.UsuarioService;
import model.utils.beanUtilidades;


/**
 *
 * @author abc
 */
@ManagedBean
@ViewScoped
public class MisEquivalenciasController implements Serializable{

    @ManagedProperty(value="#{beanUtilidades}")
    private beanUtilidades beanUtilidades;
    
    @ManagedProperty(value="#{usuarioService}")
    private transient UsuarioService usuarioService;
    
     @ManagedProperty(value="#{movilidadService}")
    private transient MovilidadService movilidadService;
    
    @ManagedProperty(value="#{asignaturaService}")
    private transient AsignaturaService asignaturaService;
    
    @ManagedProperty(value="#{equivalenciaService}")
    private transient EquivalenciaService equivalenciaService;
    
    @ManagedProperty(value="#{mensajeService}")
    private transient MensajeService mensajeService;
    
   
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
    private int creditosA;
    private int creditosB;
    
    private int creditosComparadoA;
    private int creditosComparadoB;
    
    
    private boolean verInfo;
    private boolean verConfirmar=true;
    
    private boolean ultimo;
    
    
    
    public MisEquivalenciasController() {
        
    }
    
    @PostConstruct
    public void init(){
        context=FacesContext.getCurrentInstance().getExternalContext();
        session=(HttpSession)context.getSession(false);
        
        
       
      
        user=(Usuario)session.getAttribute("user");
        
        
        
        if(context.getSessionMap().containsKey("movilidad")){
        selectedMovilidad=(Movilidad)context.getSessionMap().get("movilidad");
        context.getSessionMap().remove("movilidad");
        listaAsignaturasFic=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad("UDC");
        listaAsignaturasUniversidad=(ArrayList<Asignatura>)asignaturaService.listarAsignaturasPorUniversidad(selectedMovilidad.getUniversidad().getNombre());
        
         if(context.getSessionMap().containsKey("contrato")){
        selectedContrato=(Contrato)context.getSessionMap().get("contrato");
        context.getSessionMap().remove("contrato");
        try{
        c=equivalenciaService.findContrato(selectedContrato.getIdContrato());
        }catch(ContratoNotFoundException ex){
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
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex){
                    
                    }
    }
         
       
    }
    

    public beanUtilidades getBeanUtilidades() {
        return beanUtilidades;
    }

    public void setBeanUtilidades(beanUtilidades beanUtilidades) {
        this.beanUtilidades = beanUtilidades;
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

    public int getCreditosA() {
        return creditosA;
    }

    public void setCreditosA(int creditosA) {
        this.creditosA = creditosA;
    }

    public int getCreditosB() {
        return creditosB;
    }

    public void setCreditosB(int creditosB) {
        this.creditosB = creditosB;
    }

    public int getCreditosComparadoA() {
        return creditosComparadoA;
    }

    public void setCreditosComparadoA(int creditosComparadoA) {
        this.creditosComparadoA = creditosComparadoA;
    }

    public int getCreditosComparadoB() {
        return creditosComparadoB;
    }

    public void setCreditosComparadoB(int creditosComparadoB) {
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
            
            beanUtilidades.creaMensaje("No hay asignaturas de origen", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        if(selectedAsignaturasUni.isEmpty()){
            beanUtilidades.creaMensaje("No hay asignaturas de destino", FacesMessage.SEVERITY_ERROR);
            return null;
        }
       
        
        
        for(Asignatura a:selectedAsignaturasFic){
       
        ma=new MiembroGrupoAsignaturaA(a, equivalencia);
        equivalencia.getMiembroGrupoAsignaturaAs().add(ma);
        
                  creditosA=creditosA+a.getCreditos();                              // con cascade save-update no hace falta salvar el miembro_grupo_asignaturas
        
        }
        
        for(Asignatura a:selectedAsignaturasUni){
            
        mb=new MiembroGrupoAsignaturaB(a, equivalencia);
        equivalencia.getMiembroGrupoAsignaturaBs().add(mb);
                   creditosB=creditosB+a.getCreditos();
            
        }
        equivalencia.setVisible("no");
        equivalencia.setIdequivalencia(j);
        
            listaAuxEquivalencias.add(equivalencia);
            j++;
            
            
            
            return null;      
         
         
         
    }
    
    public String confirmarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
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
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/verMisMovilidades.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
            
        
        beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
        try{
        Mensaje m=new Mensaje(usuarioService.find("admin"), user, Calendar.getInstance().getTime(),"contrato creado", "el usuario "+user.getLogin()+" ha creado un contrato","no","no","no");
        mensajeService.enviarMensaje(m);
        }catch(UsuarioNotFoundException ex){
            beanUtilidades.creaMensaje("Usuario inexistente", FacesMessage.SEVERITY_ERROR);
        }
        verConfirmar=false;
        
        return null;
        
    }
    
    public String editarContrato(){
        if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ArrayList<Equivalencia>listaCopia=null;
         try{
        listaCopia=equivalenciaService.editarContrato(listaAuxEquivalencias, c);
        }catch(RuntimeException ex){
         
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
        
        beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
     Mensaje m=new Mensaje(usuarioService.find("admin"), user, Calendar.getInstance().getTime(),"contrato modificado", "el usuario "+user.getLogin()+" ha modificado un contrato","no","no","no");
     mensajeService.enviarMensaje(m);
     }catch(Exception ex){
         
     }
        
        verConfirmar=false;
        return null;
    
    }
    
    
 
    
     public String  crearContratoDesdeAceptado(){
       if(listaAuxEquivalencias.isEmpty()){
            
            beanUtilidades.creaMensaje("el contrato está vacío", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        
        Contrato cNuevo=new Contrato();
        cNuevo.setFecha(Calendar.getInstance().getTime());
        cNuevo.setMovilidad(selectedMovilidad);
        cNuevo.setEstado("pendiente");
        
         try{
         equivalenciaService.crearContratoDesdeAceptado(listaAuxEquivalencias, c, cNuevo);
        }catch(RuntimeException ex){
         
             try{
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/usuario/error.xhtml");
            }catch(IOException ex2){
                    
                    }
        }
        
        
       
        
     beanUtilidades.creaMensaje("se ha registrado el contrato correctamente", FacesMessage.SEVERITY_INFO);
     try{
     Mensaje m=new Mensaje(usuarioService.find("admin"), user, Calendar.getInstance().getTime(),"contrato creado", "el usuario "+user.getLogin()+" ha modificado un contrato","no","no","no");
     mensajeService.enviarMensaje(m);
     }catch(UsuarioNotFoundException ex){
         
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
    
    public void detallesAsign(){
         
        verInfo=true;
        
    }
    
    public void cerrarDetallesAsign(){
        
        verInfo=false;
    }
   
    
    
    
    public String nuevoAceptado(){
        
       
        
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("movilidad",selectedMovilidad);
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contrato", selectedContrato);
         
         return ("crearContratoDesdeAceptado.xhtml?faces-redirect=true");
         
     
        
    }
     
     
    
}
