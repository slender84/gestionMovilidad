/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Mensaje;

import java.util.List;
import org.hibernate.Query;

import org.springframework.stereotype.Repository;

@Repository("mensajeDao")
public class MensajeDaoImpl extends GenericDaoHibernate<Mensaje, Integer> implements MensajeDao{
    
    
    
    
    
    
    @Override
    public void crearMensaje(Mensaje m){
        
        getSession().saveOrUpdate(m);
        
    }
    
   
    
    
    @Override
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen  " +
                "and m.usuarioByDestino.login=:destino and m.eliminadoOrigen='no' order by m.fecha desc" );
             
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
      
        
    }
    
    
    @Override
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen  " +
                "and m.usuarioByDestino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc" );
                
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
       
        
    }
            @Override         
       public List<Mensaje> mensajesRecibidosTotal(String destino){
           Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino='no' order by m.fecha desc");
          q.setParameter("destino", destino);
          return q.list();
       }     
            
    
      @Override          
      public List<Mensaje> mensajesEnviadosTotal(String origen){
          
          Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen and m.eliminadoOrigen='no' order by m.fecha desc");
          q.setParameter("origen", origen);
          return q.list();
      }
      
    
}
