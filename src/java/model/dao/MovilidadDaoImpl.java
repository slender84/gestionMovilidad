/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Movilidad;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("movilidadDao") 
public class MovilidadDaoImpl extends GenericDaoHibernate<Movilidad, Integer> implements MovilidadDao{
    
    
    
   
    @Override
    public List<Movilidad> listarMisMovilidades(String user){
        
        Query q= getSession().createQuery("select m from Movilidad m where m.usuario.login=:user");
        q.setParameter("user", user);
        return q.list();
        
        
    }
   
   
    
    
    
}
