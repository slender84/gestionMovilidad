/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;



import entities.Movilidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;


@Repository("movilidadDao") 
public class MovilidadDaoImpl extends GenericDaoHibernate<Movilidad, Integer> implements MovilidadDao,Serializable{
    
    
    
   
    @Override
    public List<Movilidad> listarMisMovilidades(String user){
        
        
        Query q= getSession().createQuery("select m from Movilidad m where m.usuario.login=:user order by m.fechaInicio desc");
        q.setParameter("user", user);
        return q.list();
        
    }
   
    
    @Override
    public List<Movilidad> listar(){
        
       //getSession().enableFilter("universidad").setParameter("universidadParam", "Universidade da Coruña");
       //getSession().enableFilter("cursoAcademico").setParameter("cursoAcademicoParam", "2015/2016");
       
        
       
        return getSession().createQuery("select m from Movilidad m order by m.fechaInicio desc").list();
    }
   
    
    @Override
    public List<Movilidad> listarMovilidadPorFiltro(Map<String,String> listaFiltros){
        
        if(listaFiltros.containsKey("curso"))
            
            getSession().enableFilter("cursoAcademico").setParameter("cursoAcademicoParam", listaFiltros.get("curso"));
        if(listaFiltros.containsKey("estado")){
            
            getSession().enableFilter("estado").setParameter("estadoParam", listaFiltros.get("estado"));
        }
        if(listaFiltros.containsKey("pais")){
           
            if(listaFiltros.containsKey("universidad")==false){
                
                return getSession().createQuery("select m from Movilidad m where m.universidad.pais.nombre=:pais order by m.fechaInicio desc").setParameter("pais", listaFiltros.get("pais")).list();
            }else{
                
                getSession().enableFilter("universidad").setParameter("universidadParam", listaFiltros.get("universidad"));
                
                
            }
                
            
        }
        
        return listar();
        
    }
    
    @Override
    public List<Movilidad> listaLazy(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
        
        String orden=null;
        
        String s;
        Iterator i=filters.keySet().iterator();
        while(i.hasNext()){
            s=(String)i.next();
            System.out.println("el filters hay: "+s);
        }
        
        System.out.println("sortField: "+sortField);
        System.out.println("SortOrder: "+sortOrder.toString());
        System.out.println("pagesize :"+pageSize);
        System.out.println("first: "+first);
        
        String campo=null;
        
        
        if(sortField!=null){
            
         if(sortField.equals("estado")){
            
            campo="estado";
            
         }else if(sortField.equals("universidad.nombre")){
              
                campo="universidad";
         }else if(sortField.equals("cursoacademico.cursoAcademico")){
           
                    campo="cursoacademico";
                    
         }else if(sortField.equals("universidad.pais.nombre")){
                        campo="universidad.pais.nombre";
                        
         }else if(sortField.equals("usuario.login")){
             campo="usuario.login";
         }
           
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("estado")){
            getSession().enableFilter("filtroEstado").setParameter("filtroEstadoParam", filters.get("estado"));
        
        }
        
        
        if(filters.containsKey("universidad.nombre")){
            //System.out.println("enable filtroUni");
            getSession().enableFilter("filtroUniversidad").setParameter("filtroUniversidadParam", filters.get("universidad.nombre"));
                    }
        
        if(filters.containsKey("cursoacademico.cursoAcademico")){
            
            getSession().enableFilter("cursoAcademico").setParameter("cursoAcademicoParam", filters.get("cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("universidad.pais.nombre")){
            
            getSession().enableFilter("pais").setParameter("paisParam", filters.get("universidad.pais.nombre"));
        }
        
        if(filters.containsKey("usuario.login")){
            
            getSession().enableFilter("usuario").setParameter("usuarioParam", filters.get("usuario.login"));
        }
        
        
        
       List<Movilidad> l=new ArrayList<Movilidad>();
        
        if(sortField==null){
            
             l=getSession().createQuery("select m from Movilidad m").setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
            
            
             
             l=getSession().createQuery("select m. from Movilidad m order by m."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
              
            
        }
        
        return l;
        
        
    }
    
    @Override
     public int count(Map<String,Object>filters){
         
         
        if(filters.containsKey("estado")){
            getSession().enableFilter("filtroEstado").setParameter("filtroEstadoParam", filters.get("estado"));
        
        }
        
        
        if(filters.containsKey("universidad.nombre")){
            //System.out.println("enable filtroUni");
            getSession().enableFilter("filtroUniversidad").setParameter("filtroUniversidadParam", filters.get("universidad.nombre"));
                    }
        
        if(filters.containsKey("cursoacademico.cursoAcademico")){
            
            getSession().enableFilter("cursoAcademico").setParameter("cursoAcademicoParam", filters.get("cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("universidad.pais.nombre")){
            
            getSession().enableFilter("pais").setParameter("paisParam", filters.get("universidad.pais.nombre"));
        }
        
        if(filters.containsKey("usuario.login")){
            
            getSession().enableFilter("usuario").setParameter("usuarioParam", filters.get("usuario.login"));
        }
        
        
        
       List<Movilidad> l=new ArrayList<Movilidad>();
         
         l=getSession().createQuery("select m from Movilidad m").list();
         return l.size();
         
     }
    
    
   
    
}
