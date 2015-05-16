
 

package model.dao;



import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Movilidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;


import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;


@Repository("asignaturaDao")
public class AsignaturaDaoImpl extends GenericDaoHibernate<Asignatura, AsignaturaId> implements AsignaturaDao{
   
    @Override
    public List<Asignatura> listarAsignaturasPorUniversidad(boolean disponible,String nombre){
        
        if(nombre.equalsIgnoreCase("Universidade da Coruña")){
            
            Query q=getSession().createQuery("select a from Asignatura a where a.disponible=:disponible and a.universidad.nombre like '%coruña%' order by a.curso asc,a.id.codAsignatura asc" ).setParameter("disponible", disponible);
        //q.setParameter("nombre", nombre);
        return q.list();
            
        }
        
        
        Query q=getSession().createQuery("select a from Asignatura a where a.disponible=:disponible and a.universidad.nombre=:nombre order by a.curso asc,a.id.codAsignatura asc" );
        q.setParameter("nombre", nombre).setParameter("disponible", disponible);
                
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
  public List<Asignatura> listarAsignaturasPorUniversidadYCurso (boolean disponible, String nombreUniversidad, String curso){
    
      if(disponible==false){
      return getSession().createQuery("SELECT a from Asignatura a where a.universidad.nombre=:nombreUniversidad and a.curso=:curso")
              .setParameter("nombreUniversidad", nombreUniversidad).setParameter("curso", curso).list();
      }
      
      return getSession().createQuery("SELECT a from Asignatura a where a.disponible=1 and a.universidad.nombre=:nombreUniversidad and a.curso=:curso")
              .setParameter("nombreUniversidad", nombreUniversidad).setParameter("curso", curso).list();
  }
  
  @Override
  public List<ComentarioAsignatura> listarComentariosAsignatura(Usuario usuario){
      
      return getSession().createQuery("select c from ComentarioAsignatura c where c.usuario=:user order by c.fecha desc").setParameter("user", usuario).list();
      
      
      
  }
  
  @Override
  public void editarComentario(ComentarioAsignatura c){
      
      getSession().update(c);
      
      
  }
  
  
  @Override
  public List<Asignatura> asignaturasMovilidad(Movilidad m){
  
      
  return getSession().createQuery("SELECT DISTINCT a from Asignatura a where a.universidad.nombre not like '%coruña%' and a.universidad.nombre not like '%udc%' and a.id.codAsignatura in"
          + "(SELECT m.asignatura.id.codAsignatura from MiembroGrupoAsignaturaB m where m.equivalencia in(select e from Equivalencia e JOIN e.contratos c where c.idContrato"
          + " in(select d from Contrato d where d.movilidad=:movilidad))) and a.universidad=:n")
          .setParameter("movilidad", m).setParameter("n", m.getUniversidad()).list();
          
  
  
}
  
  @Override
  public List<ComentarioAsignatura> listarComentariosPorAsignatura(AsignaturaId id){
      
      
      return getSession().createQuery("select c from ComentarioAsignatura c where c.estado='aceptado' and c.asignatura.id=:id").setParameter("id", id).list();
      
  }
  
  @Override
  public ComentarioAsignatura buscarComentarioAsignatura(Integer id) throws InstanceNotFoundException{
      
      Object o=getSession().get(ComentarioAsignatura.class, id);
      if(o==null)
          throw new InstanceNotFoundException();
      
      return ((ComentarioAsignatura)o);
  }
  
  
  
  @Override
  public List<ComentarioAsignatura> listarComentariosAsignaturaPendientes(){
      
      return getSession().createCriteria(ComentarioAsignatura.class)
              .add(Restrictions.like("estado", "pendiente"))
              .addOrder(Order.desc("fecha")).list();
      
  }
  
  
  @Override     
   public List<ComentarioAsignatura> listaLazyComentarioAsignatura(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
       
       String asignatura=null;
       int filtro=0;
       
       String orden=null;
        List<ComentarioAsignatura> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            
            case "usuario.login":campo="usuario.login";
                break;
            case "asignatura.id.nombreUniversidad":campo="asignatura.id.nombreUniversidad";
                break;
            case "asignatura.nombreAsignatura":campo="asignatura.nombreAsignatura";
                break;
            case "fecha":campo="fecha";
                break;
            case "estado":campo="estado";
                break;    
            case "idcomentario":campo="idcomentario";
                break;      
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("usuario.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuario.login"));
        
        }
        
        if(filters.containsKey("asignatura.id.nombreUniversidad")){
            
            getSession().enableFilter("universidad").setParameter("universidadParam", filters.get("asignatura.id.nombreUniversidad"));
            
        }
        
        if(filters.containsKey("estado")){
            getSession().enableFilter("estado").setParameter("estadoParam", filters.get("estado"));
        }
        
         if(filters.containsKey("asignatura.nombreAsignatura")){
            
            //getSession().enableFilter("asignatura").setParameter("asignaturaParam", filters.get("asignatura.nombreAsignatura"));
             
             filtro=1;
             asignatura="%"+filters.get("asignatura.nombreAsignatura").toString()+"%";
             
             
        }
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from ComentarioAsignatura c").setFirstResult(first).setMaxResults(pageSize).list();
            return l;
        }else{
            
            if(filtro==0){
            l=getSession().createQuery("select c from ComentarioAsignatura c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
            return l;
            }
            
            //l=getSession().createQuery("select c from ComentarioAsignatura c where c.asignatura.nombreAsignatura like :asignatura order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("asignatura",asignatura).list();
            l=getSession().createQuery("select c from ComentarioAsignatura c where c.asignatura.nombreAsignatura in(select a.nombreAsignatura from Asignatura a where a.nombreAsignatura like :asignatura) order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("asignatura",asignatura).list();

            return l;
            
        }
        
        
        
       
       
       
   }
   
     @Override   
     public int countComentarioAsignatura(Map<String,Object>filters){
         
       String asignatura=null;
       int filtro=0;
       
       String orden=null;
        List<ComentarioAsignatura> l=null;
         
         
         
        if(filters.containsKey("usuario.login")){
                 
            getSession().enableFilter("login").setParameter("loginParam", filters.get("usuario.login"));
        
        }
        
        if(filters.containsKey("asignatura.id.nombreUniversidad")){
            
            getSession().enableFilter("universidad").setParameter("universidadParam", filters.get("asignatura.id.nombreUniversidad"));
        }
        
        if(filters.containsKey("asignatura.nombreAsignatura")){
            
            //getSession().enableFilter("asignatura").setParameter("asignaturaParam", filters.get("asignatura.nombreAsignatura"));
            filtro=1;
             asignatura="%"+filters.get("asignatura.nombreAsignatura").toString()+"%";
        }
        
        
        if(filters.containsKey("estado")){
            getSession().enableFilter("estado").setParameter("estadoParam", filters.get("estado"));
        }
        
        if(filtro==0){
            l=getSession().createQuery("select c from ComentarioAsignatura c" ).list();
            return l.size();
            }
            
            //l=getSession().createQuery("select c from ComentarioAsignatura c where c.asignatura.nombreAsignatura like :asignatura order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("asignatura",asignatura).list();
            l=getSession().createQuery("select c from ComentarioAsignatura c where c.asignatura.nombreAsignatura in(select a.nombreAsignatura from Asignatura a where a.nombreAsignatura like :asignatura)").setParameter("asignatura",asignatura).list();
            
            return l.size();
        
        
        
         
     }
  
  
  
  
  
}