

package model.dao;




import entities.Asignatura;
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
               "(select m.equivalencia.idequivalencia from MiembroGrupoAsignaturaB m where m.asignatura.id.nombreUniversidad=:universidad))").setParameter("universidad", universidad).list();
   }
   
   @Override
   public List<Object> listarAsignaturasMovilidad(){
       
       return getSession().createSQLQuery("select * from Asignatura where codAsignatura in"+
               "(select codAsignatura from miembro_grupo_asignatura_b where idEquivalencia="+
               "(select idEquivalencia from contrato_equivalencia where idContrato=(select idContrato from contrato where idMovilidad=1))) and nombreUniversidad='abc'").list();
       
   }
   
   @Override
   public List<Asignatura> lista2(){
       
       
              return getSession().createQuery("SELECT DISTINCT a from Asignatura a where a.id.codAsignatura in(SELECT m.asignatura.id.codAsignatura from MiembroGrupoAsignaturaB m where m.equivalencia=(select e from Equivalencia e JOIN e.contratos c where c.idContrato=(select d from Contrato d where d.movilidad.codMovilidad=1)))").list();

               
       
   }
   
   
  
              
       
       
       
   
   
   
   
}
