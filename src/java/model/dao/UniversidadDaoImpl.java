package model.dao;

import entities.Cronica;
import entities.Universidad;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;








@Repository("universidadDao")
public class UniversidadDaoImpl extends GenericDaoHibernate<Universidad, String> implements UniversidadDao{
    
    
    @Override
     public List<Universidad> listarPorPais(String pais){
    
    Query q=getSession().createQuery("select u from Universidad u where u.pais.nombre=:pais");
    q.setParameter("pais",pais);
    return(q.list());
}
    
    
   
       
       @Override
       public List<Cronica> listaLazyCronica(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
       
       
       String orden=null;
        List<Cronica> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            
            case "usuario.login":campo="usuario.login";
                break;
            case "universidad.nombre":campo="universidad.nombre";
                break;
            
            case "fecha":campo="fecha";
                break;
            case "estado":campo="estado";
                break;    
            case "idcronica":campo="idcronica";
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
        
        if(filters.containsKey("universidad.nombre")){
            
            getSession().enableFilter("universidad").setParameter("universidadParam", filters.get("universidad.nombre"));
        }
        
        if(filters.containsKey("estado")){
            getSession().enableFilter("estado").setParameter("estadoParam", filters.get("estado"));
        }
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from Cronica c").setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
             l=getSession().createQuery("select c from Cronica c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
              
        }
        
        return l;
        
   }
       
       
    @Override   
    public int countCronica(Map<String,Object>filters){
    
    
    if(filters.containsKey("usuario.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuario.login"));
        
        }
        
        if(filters.containsKey("universidad.nombre")){
            
            getSession().enableFilter("universidad").setParameter("universidadParam", filters.get("universidad.nombre"));
        }
        
        if(filters.containsKey("estado")){
            getSession().enableFilter("estado").setParameter("estadoParam", filters.get("estado"));
        }
        
        List<Cronica> lista=getSession().createQuery("select c from Cronica c").list();
        return lista.size();
    
    
    
}
    
}
