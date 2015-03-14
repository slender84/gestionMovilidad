
 

package model.dao;



import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Movilidad;
import entities.Usuario;

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
  
  @Override
  public List<ComentarioAsignatura> listarComentariosAsignatura(Usuario usuario){
      
      return getSession().createQuery("select c from ComentarioAsignatura c where c.usuario=:user").setParameter("user", usuario).list();
      
      
      
  }
  
  @Override
  public void editarComentario(ComentarioAsignatura c){
      
      getSession().update(c);
      
      
  }
  
  
  @Override
  public List<Asignatura> asignaturasMovilidad(Movilidad m){
  
  return getSession().createQuery("SELECT DISTINCT a from Asignatura a where a.universidad.nombre not like '%coruña%' and a.universidad.nombre not like '%udc%' and a.id.codAsignatura in"
          + "(SELECT m.asignatura.id.codAsignatura from MiembroGrupoAsignaturaB m where m.equivalencia=(select e from Equivalencia e JOIN e.contratos c where c.idContrato"
          + "=(select d from Contrato d where d.movilidad=:movilidad)))")
          .setParameter("movilidad", m).list();
  
  
}
}