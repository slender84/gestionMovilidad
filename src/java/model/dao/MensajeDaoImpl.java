/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Mensaje;

import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.primefaces.model.SortOrder;

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
                "and m.usuarioByDestino.login=:destino and m.eliminadoOrigen=0 order by m.fecha desc" );
             
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
      
        
    }
    
    
    @Override
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen  " +
                "and m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 order by m.fecha desc" );
                
        q.setParameter("origen", origen);
        q.setParameter("destino", destino);
        return q.list();
       
        
    }
            @Override         
       public List<Mensaje> mensajesRecibidosTotal(String destino){
           Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 order by m.fecha desc");
          q.setParameter("destino", destino);
          return q.list();
       }     
            
    
      @Override          
      public List<Mensaje> mensajesEnviadosTotal(String origen){
          
          Query q=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen and m.eliminadoOrigen=0 order by m.fecha desc");
          q.setParameter("origen", origen);
          return q.list();
      }
      
    
      @Override
      public List<Mensaje> listaLazyMensajeRecibido(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String destino){
          
          String orden=null;
        List<Mensaje> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            
            case "usuario.login":campo="usuario.login";
                break;
            
            case "fecha":campo="fecha";
                break;
            case "leidoDestino":campo="leidoDestino";
                break;    
            case "idmensaje":campo="idmensaje";
                break;      
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("usuario.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuario.login"));
        
        }
        
       
        
        if(filters.containsKey("leidoDestino")){
            getSession().enableFilter("leido").setParameter("leidoParam", filters.get("leidoDestino"));
        }
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0").setParameter("destino", destino).setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
             l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
              
        }
        
        return l;
          
          
      }
    @Override  
    public int countMensajeRecibido(Map<String,Object>filters,String destino){
        
        
        if(filters.containsKey("usuario.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuario.login"));
        
        }
        
       
        
        if(filters.containsKey("leidoDestino")){
            getSession().enableFilter("leido").setParameter("leidoParam", filters.get("leidoDestino"));
        }
        
        List<Mensaje> lista=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0").setParameter("destino", destino).list();
        return lista.size();
        
        
    }
      
      
      
      
}
