
 

package model.dao;


import entities.Asignatura;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("asignaturaDao")
public class AsignaturaDaoImpl implements AsignaturaDao,Serializable{
    
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void crearAsignatura(Asignatura a){
        
        sessionFactory.getCurrentSession().save(a);
        
        
    }
    @Override
    public void actualizarAsignatura(Asignatura a){
        
        sessionFactory.getCurrentSession().update(a);
    }
    
    @Override
    public List<Asignatura> listarAsignaturas(){
        
        return sessionFactory.getCurrentSession().createQuery("select a from Asignatura a").list();
    }
    
    
    @Override
    public List<Asignatura> listarAsignaturasPorUniversidad(String nombre){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select a from Asignatura a where a.universidad.nombre=:nombre order by a.universidad.nombre desc" );
        q.setParameter("nombre", nombre);
        return q.list();
        
    }
    
    @Override
    public void eliminaAsignatura(Asignatura a){
        
        sessionFactory.getCurrentSession().delete(a);
        
    }
    
    @Override
    public List<Asignatura> listarPorCriterio(){
        
        return(sessionFactory.getCurrentSession().createQuery("select a from Asignatura a where a."+"periodo=:nombre").setParameter("nombre", "anual").list());
    }
    
}
