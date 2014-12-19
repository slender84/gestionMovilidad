/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;

import entities.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao{
    @Autowired
    private SessionFactory sessionFactory;
    
    
        
        
        
        
    

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory SessionFactory) {
        this.sessionFactory = SessionFactory;
    }
    
    
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    
    @Override
    public Usuario find(String nombre){
        
        Session session=sessionFactory.getCurrentSession();
        Query q=session.createQuery("select u from Usuario u where u.login=:nombre");
        q.setParameter("nombre", nombre);
        Usuario u=(Usuario)q.uniqueResult();
        return u;
        
        
        
    }
    
    @Override
    public void delete(Usuario u) {
        
        sessionFactory.getCurrentSession().delete(u);
        
        
    }
    
    @Override
    public List<Usuario> listar(){
        
        
        return((List<Usuario>)sessionFactory.getCurrentSession().createQuery("select u from Usuario u").list());
        
        
    }
    
    
    
    @Override
    public void insertarUsuario(Usuario u){ //throws org.springframework.dao.DataIntegrityViolationException{
        
            
            sessionFactory.getCurrentSession().save(u);
            
        
    }
    @Override
    public void actualizar(Usuario u){
        
        sessionFactory.getCurrentSession().saveOrUpdate(u);
        
    }
    
   
    
}
