/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import com.sun.faces.flow.FlowCDIContext;
import entities.Movilidad;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository("movilidadDao") 
public class MovilidadDaoImpl extends GenericDaoHibernate<Movilidad, Integer> implements MovilidadDao{
    
    
    
   
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
   
   
    
}
