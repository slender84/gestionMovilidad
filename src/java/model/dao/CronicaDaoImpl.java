/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import entities.Cronica;
import entities.Usuario;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository("cronicaDao")
public class CronicaDaoImpl extends GenericDaoHibernate<Cronica, Integer> implements CronicaDao{
    
    @Override
    public List<Cronica> listarCronicasPorUniversidad(String universidad){
        
        return getSession().createQuery("select c from Cronica c where c.universidad.nombre=:universidad order by c.fecha desc").setParameter("universidad", universidad).list();
        
        
    }
    
    @Override
    public List<Cronica> listarMisCronicas(Usuario usuario){
        
    return getSession().createQuery("select c from Cronica c where c.usuario=:usuario").setParameter("usuario", usuario).list();
    
        
    }
    @Override
    public List<Cronica> listarCronicasPublicas(String universidad){
        
        return getSession().createQuery("select c from Cronica c where c.universidad.nombre=:universidad and c.estado='aceptado' order by c.fecha desc").setParameter("universidad", universidad).list();
        
        
    }
    
    @Override
    public List<Cronica> listar(){
        
        return getSession().createQuery("select c from Cronica c order by c.fecha desc").list();
    }
    
   
    
    
}
