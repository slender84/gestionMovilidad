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
            
            
            case "usuarioByOrigen.login":campo="usuarioByOrigen.login";
                break;
            
            case "fecha":campo="fecha";
                break;
            case "leidoDestino":campo="leidoDestino";
                break;    
            case "idmensaje":campo="idmensaje";
                break;
            case "tema":campo="tema";
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("usuarioByOrigen.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuarioByOrigen.login"));
        
        }
        
        if(sortField==null){
                          
                      if(filters.containsKey("leidoDestino")){
            
            
                              if(filters.get("leidoDestino").equals("true")){
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=1").setParameter("destino", destino).list();
                              }else if(filters.get("leidoDestino").equals("false")) {
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=0").setParameter("destino", destino).list();
                              }
                      }
                      
                      else{
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0").setParameter("destino", destino).list();
                              }
            
        }else{
            
                      if(filters.containsKey("leidoDestino")){
                              
                              if(filters.get("leidoDestino").equals("true")){
                                 l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=1 order by  m."+campo+"  "+orden ).setParameter("destino", destino).list();
                              }else if (filters.get("leidoDestino").equals("false")){
                                 l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=0 order by m."+campo+"  "+orden).setParameter("destino", destino).setFirstResult(first).setMaxResults(pageSize).list();
                              }
                              
                      
                
                      }else{
                                 l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 order by m."+campo+"  "+orden).setParameter("destino", destino).setFirstResult(first).setMaxResults(pageSize).list(); 
                              }
                
        }
        
        return l;
          
          
      }
    @Override  
    public int countMensajeRecibido(Map<String,Object>filters,String destino){
        
        
        if(filters.containsKey("usuarioByOrigen.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuarioByOrigen.login"));
        
        }
        
       List<Mensaje> l=null;
        
        if(filters.containsKey("leidoDestino")){
            
            
                              if(filters.get("leidoDestino").equals("true")){
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=1").setParameter("destino", destino).list();
                              }else if(filters.get("leidoDestino").equals("false")) {
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0 and m.leidoDestino=0").setParameter("destino", destino).list();
                              }
                      }
                      
                      else{
            
                                  l=getSession().createQuery("select m from Mensaje m where m.usuarioByDestino.login=:destino and m.eliminadoDestino=0").setParameter("destino", destino).list();
                              }
        return l.size();
        
        //return 10;
        
    }
      
    @Override
      public List<Mensaje> listaLazyMensajeEnviado(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String origen){
          
          
          String orden=null;
        List<Mensaje> l=null;
        
        
        String campo=null;
        
        
        if(filters.containsKey("usuarioByDestino.login")){
                 
            getSession().enableFilter("destino").setParameter("destinoParam", filters.get("usuarioByDestino.login"));
        
        }
        
        if(sortField!=null){
          
        switch(sortField){
            
            
            case "usuarioByDestino.login":campo="usuarioByDestino.login";
                break;
            
            case "fecha":campo="fecha";
                break;
              
            case "idmensaje":campo="idmensaje";
                break;
            case "tema":campo="tema";
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
            
             l=getSession().createQuery("select m from Mensaje m where m.usuarioByOrigen.login=:origen and m.eliminadoOrigen=0 order by m."+campo+"  "+orden).setParameter("origen", origen).setFirstResult(first).setMaxResults(pageSize).list(); 
        
        }else{
            
            l=getSession().createQuery("select m FROM Mensaje m where m.eliminadoOrigen=0 and m.usuarioByOrigen.login=:origen").setParameter("origen", origen).list();
            
        }
            
            return l;
            
        }
    
    @Override
    public int countMensajeEnviado(Map<String,Object>filters,String origen){
        
         if(filters.containsKey("usuarioByDestino.login")){
                 
            getSession().enableFilter("destino").setParameter("destinoParam", filters.get("usuarioByDestino.login"));
        
        }
        
        List<Mensaje> lista=getSession().createQuery("select m from Mensaje m where m.eliminadoOrigen=0 and m.usuarioByDestino.login=:origen").setParameter("origen", origen).list();
        //return lista.size();
        return 10;
        
        
    }
      
      
}
