

package model.services;


import entities.Asignatura;
import entities.Contrato;
import entities.Equivalencia;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import entities.Movilidad;
import exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import model.dao.ContratoDao;
import model.dao.EquivalenciaDao;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import model.utils.EquivalenciaRevisada;



@Transactional
@Service("equivalenciaService")
public class EquivalenciaServiceImpl implements EquivalenciaService{
 
    
    @Autowired
    private EquivalenciaDao equivalenciaDao;
    
    @Autowired
    private ContratoDao contratoDao;

    public ContratoDao getContratoDao() {
        return contratoDao;
    }

    public void setContratoDao(ContratoDao contratoDao) {
        this.contratoDao = contratoDao;
    }
   
    

    public EquivalenciaDao getEquivalenciaDao() {
        return equivalenciaDao;
    }

    public void setEquivalenciaDao(EquivalenciaDao equivalenciaDao) {
        this.equivalenciaDao = equivalenciaDao;
    }

    @Override
    public void crearEquivalencia(Equivalencia e){
        
        equivalenciaDao.insertar(e);
    }
    
    @Override
    public void eliminarEquivalencia(Equivalencia e){
        
        equivalenciaDao.eliminar(e);
        
    }
    @Override
    public void actualizarEquivalencia(Equivalencia e){
        
        equivalenciaDao.editar(e);
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Equivalencia> listarEquivalencias(){
        
        return equivalenciaDao.listar();
    }
    
   
    @Override
    public void crearContrato(Contrato c){
        contratoDao.insertar(c);
        
    }
    
    @Override
    public void modificarContrato(Contrato c) throws InstanceNotFoundException{
        
        if(contratoDao.existe(c.getIdContrato())==false){
            throw new InstanceNotFoundException();
        }
        contratoDao.editar(c);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Contrato> listarContratos(Movilidad m){
        return contratoDao.listarContratos(m);
    }
    @Override
    public void eliminarContrato(Contrato c){
        
        
       contratoDao.eliminar(c);
       
    }
    @Override
    @Transactional(readOnly = true)
    public List<Equivalencia> listarEquivalenciasPorContrato(Integer id){
        List<Equivalencia> listaEquivalenciasPorcontrato=equivalenciaDao.listarEquivalenciasPorContrato(id);
        
        for(Equivalencia e:listaEquivalenciasPorcontrato){ 
            
        Hibernate.initialize(e.getMiembroGrupoAsignaturaBs());
        Iterator i=e.getMiembroGrupoAsignaturaBs().iterator();
        while(i.hasNext()){
            MiembroGrupoAsignaturaB m=(MiembroGrupoAsignaturaB)i.next();
            Hibernate.initialize(m.getAsignatura());
        }
        Hibernate.initialize(e.getMiembroGrupoAsignaturaAs());
        Iterator j=e.getMiembroGrupoAsignaturaAs().iterator();
        while(j.hasNext()){
            MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)j.next();
            Hibernate.initialize(m.getAsignatura());
        }
        
        }
        return listaEquivalenciasPorcontrato;
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Contrato buscarContrato(Integer id) throws InstanceNotFoundException{
        
        Contrato c=contratoDao.buscar(id);
        if (c==null)
                throw new InstanceNotFoundException();
        Hibernate.initialize(c.getEquivalencias());
        for(Equivalencia e:c.getEquivalencias()){ 
            
        Hibernate.initialize(e.getMiembroGrupoAsignaturaBs());
        Iterator i=e.getMiembroGrupoAsignaturaBs().iterator();
        while(i.hasNext()){
            MiembroGrupoAsignaturaB m=(MiembroGrupoAsignaturaB)i.next();
            Hibernate.initialize(m.getAsignatura());
        }
        Hibernate.initialize(e.getMiembroGrupoAsignaturaAs());
        Iterator j=e.getMiembroGrupoAsignaturaAs().iterator();
        while(j.hasNext()){
            MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)j.next();
            Hibernate.initialize(m.getAsignatura());
            
        }
        
        
        
        
    }
           return c; 
}       
  
      @Override
      @Transactional(readOnly = true)
    public List<Equivalencia> equivalenciasPublicas(String Universidad){
        
        ArrayList<Equivalencia> listaEquivalencias=(ArrayList < Equivalencia >)equivalenciaDao.equivalenciasPublicas(Universidad);
        Iterator i;
        for(Equivalencia e:listaEquivalencias){
            Hibernate.initialize(e.getMiembroGrupoAsignaturaAs());
            i=e.getMiembroGrupoAsignaturaAs().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                Hibernate.initialize(m.getAsignatura());
            }
            
             Hibernate.initialize(e.getMiembroGrupoAsignaturaBs());
            i=e.getMiembroGrupoAsignaturaBs().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaB m=(MiembroGrupoAsignaturaB)i.next();
                Hibernate.initialize(m.getAsignatura());
            }
        }
        return listaEquivalencias;
    }
    
    
    
    @Override
    public int[] totalCreditos(ArrayList<Equivalencia> lista){
        
         int a=0;
         int b=0;
         
        for(Equivalencia e:lista){
            Iterator i=e.getMiembroGrupoAsignaturaAs().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA mA=(MiembroGrupoAsignaturaA)i.next();
                a=a+mA.getAsignatura().getCreditos();
            }
        }
        
        for(Equivalencia e:lista){
            Iterator i=e.getMiembroGrupoAsignaturaBs().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaB mB=(MiembroGrupoAsignaturaB)i.next();
                b=b+mB.getAsignatura().getCreditos();
            }
        }
        
        return new int[]{a,b};
    }
    
    @Override
    public void confirmarContrato(ArrayList<Equivalencia> listaAuxEquivalencias,Contrato c){
        
        for(Equivalencia e:listaAuxEquivalencias){
          
            c.getEquivalencias().add(e);
            
            
            crearEquivalencia(e);
           
        }
        
        crearContrato(c);
        
    }
    
    @Override
    public ArrayList<Equivalencia> editarContrato(ArrayList<Equivalencia> listaAuxEquivalencias,Contrato c)throws InstanceNotFoundException{
        
        if(contratoDao.existe(c.getIdContrato())==false){
            
            throw new InstanceNotFoundException();
            
        }
        
        
        ArrayList<Equivalencia> listaCopia=new ArrayList<Equivalencia>();
               
        
        for(Equivalencia e:c.getEquivalencias()){
            
            if(listaAuxEquivalencias.contains(e)==false){
              
                   listaCopia.add(e);
          
        }
        }
        
        for(Equivalencia e:listaCopia){
           
            c.getEquivalencias().remove(e);
           
            modificarContrato(c);
            
            
        
        }
        
        
        for(Equivalencia e2:listaAuxEquivalencias){
            
         if(c.getEquivalencias().contains(e2)==false){   
           
            c.getEquivalencias().add(e2);
            crearEquivalencia(e2);
            
             
        }
       
    }
       
            c.setEstado("pendiente");
            c.setFecha(Calendar.getInstance().getTime());
            modificarContrato(c);
            return listaCopia;
        
    }
    
    
 
    @Override
     public void crearContratoDesdeAceptado(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c,Contrato cNuevo){
       
        for(Equivalencia e:listaAuxEquivalencias){
            
         if(c.getEquivalencias().contains(e)==true){   
            
            cNuevo.getEquivalencias().add(e);
           
        }else{
             
            crearEquivalencia(e);
             cNuevo.getEquivalencias().add(e);
         }
       
    }
        
        crearContrato(cNuevo);
        
    }
     
    
    
    @Override 
    public ArrayList<EquivalenciaRevisada> compararEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias,ArrayList<Equivalencia> listaAuxEquivalenciasComparado){
        
        ArrayList<EquivalenciaRevisada> listaRevisada=new ArrayList<EquivalenciaRevisada>();
        
        loop:
        for(Equivalencia e:listaAuxEquivalencias){
            EquivalenciaRevisada er=new EquivalenciaRevisada(e);
            er.setIgual(true);
            ArrayList<Asignatura> listaAsignaturas=new ArrayList<Asignatura>();
            ArrayList<Asignatura> listaAsignaturasB=new ArrayList<Asignatura>();
            
            
            Iterator i=e.getMiembroGrupoAsignaturaAs().iterator();
            while(i.hasNext()){
                MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                listaAsignaturas.add(m.getAsignatura());   
            }
            
            
            Iterator j=e.getMiembroGrupoAsignaturaBs().iterator();
            while(j.hasNext()){
                MiembroGrupoAsignaturaB mb=(MiembroGrupoAsignaturaB)j.next();
                listaAsignaturasB.add(mb.getAsignatura());
            }     
            
            
            loopB: 
                  for(Equivalencia eComp:listaAuxEquivalenciasComparado){
                      
                       ArrayList<Asignatura> listaAsignaturasComp=new ArrayList<Asignatura>();
                       i=eComp.getMiembroGrupoAsignaturaAs().iterator();
                            while(i.hasNext()){
                            MiembroGrupoAsignaturaA m=(MiembroGrupoAsignaturaA)i.next();
                            listaAsignaturasComp.add(m.getAsignatura());
                
                        }
                             
                           if(listaAsignaturas.size()==listaAsignaturasComp.size()){
                                 if(contiene(listaAsignaturas, listaAsignaturasComp)){
                              
                                   
                                   ArrayList<Asignatura> listaAsignaturasCompB=new ArrayList<Asignatura>();
                                      j=eComp.getMiembroGrupoAsignaturaBs().iterator();
                                      
                                       while(j.hasNext()){
                                       MiembroGrupoAsignaturaB mb=(MiembroGrupoAsignaturaB)j.next();
                                       listaAsignaturasCompB.add(mb.getAsignatura());
                           }     
                                      
                                      if(listaAsignaturasB.size()==listaAsignaturasCompB.size()){
                                          if(contiene(listaAsignaturasB, listaAsignaturasCompB)){
                                                er.setIgual(false);
                                                listaRevisada.add(er);
                                                continue loop;
                                          }
                                      }
                               
                               
                           }
                           
                  }
                           
               }
                listaRevisada.add(er);
            }
            return listaRevisada;
        }
       
     
 
     
 
     
     
     public boolean contiene(ArrayList<Asignatura> listaA, ArrayList<Asignatura> listaB){
        
         
         loopA:
         for(Asignatura a:listaA){
             for(Asignatura b:listaB){
                 if(a.getNombreAsignatura().equals(b.getNombreAsignatura()))
                 continue loopA;    
             }
             return false;
         }
         
         return true;
     }
     
     @Override
     public Contrato verContratoPorEquivalencia(Equivalencia e) throws InstanceNotFoundException{
         
         Contrato c=null;
         
         Hibernate.initialize(e.getContratos());
         Iterator i=e.getContratos().iterator();
         while(i.hasNext()){
             if(c==null){
                 c=(Contrato)i.next();
             }else{
                 Contrato aux=(Contrato)i.next();
                 if (c.getFecha().compareTo(aux.getFecha())<0){
                     
                 c=aux;
                     
                 
             }
             
         }
         
     }
    if(c==null)
        throw new InstanceNotFoundException();
         
     return c;
     
}
     
     @Override
     public Movilidad buscarMovilidadPorContrato(Contrato c)throws InstanceNotFoundException{
        c=buscarContrato(c.getIdContrato());
        Hibernate.initialize(c.getMovilidad());
        if(c.getMovilidad()==null)
            throw new InstanceNotFoundException();
        Hibernate.initialize(c.getMovilidad().getUniversidad());
        return c.getMovilidad();
     }
     
}        
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
          
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
