/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;

import entities.Movilidad;
import entities.Usuario;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericDaoHibernate<Usuario, String> implements UsuarioDao{
   
    
    @Override
        public List<Usuario> listar(){
            
            return getSession().createQuery("select u from Usuario u where u.borrado=0").list();
            
        }
        
        
   @Override     
   public List<Usuario> listaLazyUsuario(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
       
       
       String orden=null;
        List<Usuario> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            
            case "login":campo="login";
                break;
            case "titulacion":campo="titulacion";
                break;
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("login"));
        
        }
        
        if(filters.containsKey("titulacion")){
            
            getSession().enableFilter("titulacion").setParameter("titulacionParam", filters.get("titulacion"));
        }
        
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select u from Usuario u").setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
             l=getSession().createQuery("select u from Usuario u order by u."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
              
        }
        
        return l;
        
       
       
       
   }
   
     @Override   
     public int countUsuario(Map<String,Object>filters){
         
         if(filters.containsKey("login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("login"));
        
        }
        
        if(filters.containsKey("titulacion")){
            
            getSession().enableFilter("titulacion").setParameter("titulacionParam", filters.get("titulacion"));
        }
        
        List<Usuario> l=getSession().createQuery("select u from Usuario u").list();
        return l.size();
         
     }
}
