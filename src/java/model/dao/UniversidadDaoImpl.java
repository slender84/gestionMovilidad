package model.dao;


import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;








@Repository("universidadDao")
public class UniversidadDaoImpl implements UniversidadDao,Serializable{
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Pais> listaPaises(){
        
        return sessionFactory.getCurrentSession().createQuery("select p from Pais p order by p.nombre asc").list();
        
        
    }
    @Override
    public List<Universidad> listaUniversidades(){
        
        return sessionFactory.getCurrentSession().createQuery("select u from Universidad u order by u.nombre asc").list();
    }
    
    
    @Override
    public Pais findPais(String pais){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select p from Pais p where p.nombre=:pais order by p.nombre asc");
        q.setParameter("pais", pais);
        return (Pais)q.uniqueResult();
    }
    
    
   @Override
    public void insertarPais(Pais p){
        
        sessionFactory.getCurrentSession().save(p);
        
    }
        
    @Override
    public void deletePais(Pais p){
        sessionFactory.getCurrentSession().delete(p);
    }
    
    
    @Override
    public void delete(Universidad u){
        
        sessionFactory.getCurrentSession().delete(u);
    }
    @Override
    public List<Universidad> listarUniversidades(){
        
        return(sessionFactory.getCurrentSession().createQuery("select u from Universidad u").list());
        
    }
    
    @Override
    public void insertarCarrera(Universidad u){
        
        sessionFactory.getCurrentSession().save(u);
        
    }
    
    @Override
    public void actualizar(Universidad u){
        
        sessionFactory.getCurrentSession().update(u);
    }
    
    
    @Override
    public List<Universidad> listarPorUniversidad(String universidad){
    
    Query q=sessionFactory.getCurrentSession().createQuery("select u from Universidad u where u.universidad=:universidad");
    q.setParameter("universidad",universidad);
    return(q.list());
}
    
    @Override
    public List<String> listarPorUniversidadStr(String universidad){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select distinct c.id.nombre from Universidad c where c.id.universidad=:universidad");
    q.setParameter("universidad",universidad);
    return(q.list());
    }
    
    @Override
     public List<Universidad> listarPorPais(String pais){
    
    Query q=sessionFactory.getCurrentSession().createQuery("select u from Universidad u where u.pais.nombre=:pais");
    q.setParameter("pais",pais);
    return(q.list());
}
     @Override
     public List<String> listarPorPaisStr(String pais){
         
         Query q=sessionFactory.getCurrentSession().createQuery("select distinct c.id.universidad from Carrera c where c.pais=:pais");
    q.setParameter("pais",pais);
    return(q.list());
         
         
         
     }
    
     @Override
     public void crearCursoAcademico(Cursoacademico c){
         
         sessionFactory.getCurrentSession().saveOrUpdate(c);
         
     }
     
     @Override
     public void eliminarCursoAcademico(Cursoacademico c){
         
         sessionFactory.getCurrentSession().delete(c);
         
     }
     @Override
    public List<Cursoacademico> listaCursosAcademicos(){
        
        return sessionFactory.getCurrentSession().createQuery("select c from Cursoacademico c order by c.cursoAcademico").list();
        
    }
    
    @Override
    public Universidad findUniversidad(String universidad){
        
        Query q=sessionFactory.getCurrentSession().createQuery("select u from Universidad u where u.nombre=:universidad");
        q.setParameter("universidad", universidad);
        return (Universidad)q.uniqueResult();
        
    }
}
