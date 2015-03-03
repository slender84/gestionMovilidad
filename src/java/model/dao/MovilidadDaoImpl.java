/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;



import entities.Movilidad;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
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
    
    
   
    
}
