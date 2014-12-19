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
public class MovilidadDaoImpl implements MovilidadDao, Serializable{
    
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    
    
    
    @Override
    public void crearMovilidad(Movilidad m){
        
        sessionFactory.getCurrentSession().saveOrUpdate(m);
        
        
    }
    @Override
    public List<Movilidad> listarMovilidad(){
        
        return(sessionFactory.getCurrentSession().createQuery("select m from Movilidad m").list());
        
    }
    @Override
    public void cambiarMovilidad(Movilidad m){
        
        sessionFactory.getCurrentSession().saveOrUpdate(m);
        
    }
    
    
    @Override
    public void eliminarMovilidad(Movilidad m){
        
        sessionFactory.getCurrentSession().delete(m);
        
    }
    @Override
    public List<Movilidad> listarPorEstado(String estado){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select m from Movilidad m where m.estado=:estado");
        q.setParameter("estado", estado);
        return(q.list());
    }
    @Override
    public List<Movilidad> listarMisMovilidades(String user){
        
        Query q= sessionFactory.getCurrentSession().createQuery("select m from Movilidad m where m.usuario.login=:user");
        q.setParameter("user", user);
        return q.list();
        
        
    }
    @Override
    public List<Movilidad> listarMisMovilidadesPorEstado(String user,String estado){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select m from Movilidad m where m.usuario.login=:user and m.estado=:estado");
        q.setParameter("user", user);
        q.setParameter("estado", estado);
        return q.list();
        
    }
    
    @Override
    public List<Object> doJoin(){
        
        String s="user1";
        Query q=sessionFactory.getCurrentSession().createQuery("select m,u from Movilidad m,Universidad u where m.usuario.login=:s");
        q.setParameter("s", s);
        return q.list();
    }
    
    @Override
    public List<Movilidad> listarMovilidadesValidas(String usuario){
        
        String encurso="en curso";
        String aceptada="aceptada";
        
        Query q= sessionFactory.getCurrentSession().createQuery("select m from Movilidad m where m.usuario.login=:usuario and (m.estado=:encurso or m.estado=:aceptada)");
        q.setParameter("usuario", usuario);
        q.setParameter("encurso", encurso);
        q.setParameter("aceptada", aceptada);
        
        return q.list();
        
    }
    @Override
    public Movilidad findMovilidad(Integer id){
        
        return (Movilidad)sessionFactory.getCurrentSession().createQuery("select m from Movilidad m where m.codMovilidad=:id").setParameter("id", id).uniqueResult();
    }
    
    
    
}
