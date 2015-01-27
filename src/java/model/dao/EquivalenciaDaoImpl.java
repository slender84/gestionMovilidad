

package model.dao;




import entities.Equivalencia;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository("equivalenciaDao")
public class EquivalenciaDaoImpl extends GenericDaoHibernate<Equivalencia, Integer> implements EquivalenciaDao {
    
  

    
    
    
   
    
   
    @Override
    public List<Equivalencia> listarEquivalenciasPorContrato(Integer id){
        
        return getSession().createQuery("select e from Equivalencia e where e.contratos.idContrato").setParameter("id", id).list();
    }
    
  
    @Override
   public List<Equivalencia> equivalenciasPublicas(String universidad){
       
      
       
       return getSession().createQuery("select e from Equivalencia e where e.visible=1 and e.idequivalencia in" +
               "(select m.equivalencia from MiembroGrupoAsignaturaB m where m.asignatura.id.nombreUniversidad=:universidad))").setParameter("universidad", universidad).list();
   }
   
}
