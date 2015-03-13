
 

package model.dao;



import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


@Repository("asignaturaDao")
public class AsignaturaDaoImpl extends GenericDaoHibernate<Asignatura, AsignaturaId> implements AsignaturaDao{
   
    @Override
    public List<Asignatura> listarAsignaturasPorUniversidad(String nombre){
        
        if(nombre.equalsIgnoreCase("Universidade da Coruña")){
            
            Query q=getSession().createQuery("select a from Asignatura a where a.universidad.nombre like '%coruña%' order by a.nombreAsignatura asc" );
        //q.setParameter("nombre", nombre);
        return q.list();
            
        }
        
        
        Query q=getSession().createQuery("select a from Asignatura a where a.universidad.nombre=:nombre order by a.nombreAsignatura asc" );
        q.setParameter("nombre", nombre);
        return q.list();
        
    }
    
    @Override
    public void insertarComentario(ComentarioAsignatura c){
        
        getSession().save(c);
        
    }
    
    @Override
    public void eliminarComentario(ComentarioAsignatura c){
        
        
        getSession().delete(c);
    }
    
  @Override  
  public List<Asignatura> listarAsignaturasPorUniversidadYCurso ( String nombreUniversidad, String curso){
      
     
      
      
      return getSession().createQuery("SELECT a from Asignatura a where a.universidad.nombre=:nombreUniversidad and a.curso=:curso")
              .setParameter("nombreUniversidad", nombreUniversidad).setParameter("curso", curso).list();
      
      
      
  }
}
