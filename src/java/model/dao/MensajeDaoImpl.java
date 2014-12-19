/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Mensaje;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mensajeDao")
public class MensajeDaoImpl implements MensajeDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    
    
    @Override
    public void crearMensaje(Mensaje m){
        
        sessionFactory.getCurrentSession().saveOrUpdate(m);
        
    }
    
   
    
    
    @Override
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen  " +
                "and m.usuarioByDestino.login=:destino and m.eliminadoOrigen='no' order by m.fecha desc" );
             
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
      
        
    }
    
    
    @Override
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen  " +
                "and m.usuarioByDestino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc" );
                
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
       
        
    }
            @Override         
       public List<Mensaje> mensajesRecibidosTotal(String destino){
           Query q=sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc");
          q.setParameter("destino", destino);
          return q.list();
       }     
            
            
   @Override
    public List<Mensaje> mensajesEnviadosPorFecha(String origen,String destino,Date d,Date d2){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen " +
                "and m.usuarioByDestino.login=:destino and m.fecha>=:d and m.fecha=<:d2 order by m.fecha desc");
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        q.setParameter("d", d);
        q.setParameter("d2", d2);
        
               
        
        
        return q.list();
    }
   
    @Override
    public void modificarEstado(Mensaje m){
        
        sessionFactory.getCurrentSession().saveOrUpdate(m);
        
    }
    
    
    
    public List<Object[]> join(String user){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select u from Usuario u join u.mensajesForOrigen ");
        return q.list();
        
    }
      @Override          
      public List<Mensaje> mensajesEnviadosTotal(String origen){
          
          Query q=sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen and m.eliminadoOrigen='no' order by m.fecha desc");
          q.setParameter("origen", origen);
          return q.list();
      }
      
                
             @Override   
             public void eliminarMensaje(Mensaje m){
                 
                 sessionFactory.getCurrentSession().delete(m);
                 
             }   
                
     @Override   
     public Mensaje find(Integer msgId){
         return(Mensaje)sessionFactory.getCurrentSession().createQuery("select m from Mensaje m where m.idmensaje=:msgId").setParameter("msgId", msgId).uniqueResult();
         
         
     }
    
    
}
