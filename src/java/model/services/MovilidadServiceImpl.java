/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;


import entities.Cursoacademico;
import entities.Movilidad;
import entities.Universidad;
import entities.Usuario;
import exceptions.DuracionException;
import exceptions.NumeroDeMovilidadesException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.dao.MovilidadDao;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("movilidadService")
@Transactional
public class MovilidadServiceImpl implements MovilidadService,Serializable{
    
    
    @Autowired
    private MovilidadDao movilidadDao;
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

    public MovilidadDao getMovilidadDao() {
        return movilidadDao;
    }

    public void setMovilidadDao(MovilidadDao movilidadDao) {
        this.movilidadDao = movilidadDao;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Movilidad> listarTodasMovilidades(){
        
       List<Movilidad> aux=movilidadDao.listarMovilidad();
        for(Movilidad m:aux){
            Hibernate.initialize(m.getUniversidad());
        }
        return aux;
        
    }
    @Override
    @Transactional(readOnly = true)
    public List<Movilidad> listarMovilidadesPendientes(){
        
        String estado="pendiente";
        return movilidadDao.listarPorEstado(estado);
        
        
    }
    
    @Override
    public Date fechaMin(){
        
        Calendar calendario=Calendar.getInstance();
        Date d=calendario.getTime();
        return d;
    }
    
    @Override
    public Date fechaMax(){
        Calendar calendario=Calendar.getInstance();
        calendario.add(1, 1);// máximo tiempo para empezar la movilidad
        Date d=calendario.getTime();
        return d;
        
    }
    
    @Override
    public void crearMovilidad(Movilidad m){
        
        movilidadDao.crearMovilidad(m);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Movilidad> listarMisMovilidades(String user){
        
        List<Movilidad> aux= movilidadDao.listarMisMovilidades(user);
        for(Movilidad m:aux){
            
            
            if(m.getFechaFin().compareTo(new Date())==-1){
                m.setEstado("terminada");
                crearMovilidad(m);
                
            }
            
            
        }
        return aux;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Movilidad> listarMisMovilidadesPorEstado(String user, String estado){
        return movilidadDao.listarMisMovilidadesPorEstado(user, estado);
    }
    @Override
    public void eliminarMovilidad(Movilidad m){
        
        movilidadDao.eliminarMovilidad(m);
    }
    
    @Override
    public List<Object> doJoin(){
        
        return movilidadDao.doJoin();
        
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Movilidad> listarMovilidadesValidas(String user){
        
        return movilidadDao.listarMovilidadesValidas(user);
        
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Movilidad findMovilidad(Integer id){
        
        Movilidad m=movilidadDao.findMovilidad(id);
        Hibernate.initialize(m.getUniversidad());
        return m;
    }
    
    
    @Override
    public void crearMovilidad(Date fechaInicio,Date fechaFin,Usuario user,Universidad u,Cursoacademico ca)throws DuracionException,NumeroDeMovilidadesException{
        
        Calendar cal1=Calendar.getInstance();
        Calendar cal2=Calendar.getInstance();
                cal1.setTime(fechaInicio);
                cal2.setTime(fechaFin);
                if (cal2.compareTo(cal1)<1){
                    throw new DuracionException("la fecha de inicio es igual o posterior a la fecha de fin");
                }
                
                Calendar calAux=Calendar.getInstance();
                calAux.setTime(fechaInicio);
                calAux.add(2, 3);
                if(cal2.compareTo(calAux)<0){
                    
                    throw new DuracionException("la duración mínima de una movilidad son 3 meses");
                }
                
                calAux.setTime(fechaInicio);
                calAux.add(2, 12);
                if(cal2.compareTo(calAux)>0){
                    throw new DuracionException("la duración máxima es de un año");
                }
                
                 
                
                   ArrayList<Movilidad> aux;
                   aux=(ArrayList < Movilidad >)listarMisMovilidades(user.getLogin());
                      
                    
                    int i=0;
                    Movilidad enCurso=null;
                    
                    if(aux.size()>0){
                    for(Movilidad mov:aux){
                        
                        if(mov.getEstado().equalsIgnoreCase("pendiente")){
                            
                            if(mov.getEstado().equalsIgnoreCase("pendiente")){
                            
                            throw new NumeroDeMovilidadesException("hay una movilidad pendiente que debe ser aceptada por el coordinador o eliminada");
                            
                        }
                                    
                        
                        }
                        
                       if(mov.getEstado().equalsIgnoreCase("aceptada")){
                           i=i+1;
                           enCurso=mov;
                           if(i>1){
                               
                               throw new NumeroDeMovilidadesException("Como máximo se pueden tener dos movilidades");
                           }
                           
                       }
                  
                    }     
                    
                       if(i==1){
                           
                            if( (fechaInicio.compareTo(enCurso.getFechaInicio())>-1 && fechaInicio.compareTo(enCurso.getFechaFin())<1)||(fechaFin.compareTo(enCurso.getFechaInicio())>-1  && fechaFin.compareTo(enCurso.getFechaFin())<1)||(fechaInicio.compareTo(enCurso.getFechaInicio())<1  && fechaFin.compareTo(enCurso.getFechaFin())>-1)){
                                //creaMensaje(Boolean.toString((fechaInicio.compareTo(enCurso.getFechaInicio())<1  && fechaFin.compareTo(enCurso.getFechaFin())<1)), FacesMessage.SEVERITY_INFO);
                                throw new NumeroDeMovilidadesException("las fechas se solapan");
                       }
                       }
                    }
                    
              
              String estado="pendiente";
              
              
             
              Movilidad m=new Movilidad(ca,u, user, fechaInicio, fechaFin, estado,null);
              
              crearMovilidad(m);
              
    }
    
    
}
