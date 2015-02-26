
 

package model.dao;


import entities.Asignatura;
import entities.AsignaturaId;
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
    
    
    
}
