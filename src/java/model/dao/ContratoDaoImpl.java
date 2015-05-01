
package model.dao;

import com.sun.faces.flow.FlowCDIContext;
import entities.Contrato;
import entities.Movilidad;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

@Repository("contratoDao")
public class ContratoDaoImpl extends GenericDaoHibernate<Contrato, Integer> implements ContratoDao{
    
    @Override
    public List<Contrato> listarContratos(Movilidad m){
        
        return getSession().createQuery("select c from Contrato c where c.movilidad=:movilidad order by c.fecha desc").setParameter("movilidad", m).list();
    
}
    
    
    @Override
    public List<Contrato> listarContratosPendientes(){
        
        return getSession().createCriteria(Contrato.class)
                .add(Restrictions.like("estado","pendiente")).list();
               
    }
    
    
    @Override
    public List<Contrato> listarTodosContratos(){
        
        return getSession().createQuery("select c from Contrato c order by c.fecha desc").list();
        
    }
    
    @Override
     public List<Contrato> listarContratosPorFiltro(Map<String,String> listaFiltros){
         
         if(listaFiltros.containsKey("curso"))
            
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", listaFiltros.get("curso"));
        if(listaFiltros.containsKey("estado")){
            
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", listaFiltros.get("estado"));
        }
        if(listaFiltros.containsKey("pais")){
           
            if(listaFiltros.containsKey("universidad")==false){
                
                return getSession().createQuery("select c from Contrato c where c.movilidad.universidad.pais.nombre=:pais order by c.fecha desc").setParameter("pais", listaFiltros.get("pais")).list();
            }else{
                
                getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", listaFiltros.get("universidad"));
                
                
            }
                
            
        }
        
        return listarTodosContratos();
        
    }
         
     @Override    
     public List<Contrato> listaLazyContrato(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
         
         
         String orden=null;
        List<Contrato> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            case "estado":campo="estado";
                break;
            case "fecha":campo="fecha";
            break;
            
            case "movilidad.universidad.nombre":campo="movilidad.universidad";
                break;
            case "movilidad.cursoacademico.cursoAcademico":campo="movilidad.cursoacademico";
                break;
            case "movilidad.universidad.pais.nombre":campo="movilidad.universidad.pais.nombre";
                break;
            case "movilidad.usuario.login":campo="movilidad.usuario.login";
                break;
            case "idContrato":campo="idContrato";
                break;
            
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        
        if(filters.containsKey("movilidad.usuario.login")){
            
            getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
        }
        
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            getSession().enableFilter("paisContrato").setParameter("paisContratoParam", filters.get("movilidad.universidad.pais.nombre"));
        
        }
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         
                  }
        
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from Contrato c ").setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
             l=getSession().createQuery("select c from Contrato c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
              
        }
        
        return l;
        
    }
         
     
    
     
     
     
     @Override
     public int countContrato(Map<String,Object> filters){
         
         
         if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estado").setParameter("estadoParam", filters.get("estado"));
        
        }
        
        if(filters.containsKey("movilidad.usuario.login")){
            
            getSession().enableFilter("login").setParameter("loginParam", filters.get("movilidad.usuario.login"));
        }
        
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            getSession().enableFilter("cursoAcademico").setParameter("cursoAcademicoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            getSession().enableFilter("pais").setParameter("paisParam", filters.get("movilidad.universidad.pais.nombre"));
        
        }
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        getSession().enableFilter("universidad").setParameter("universidadParam", filters.get("movilidad.universidad.nombre"));
         
                  }
        
        
        
        List<Contrato> lista=getSession().createQuery("select c from Contrato c").list();
        return lista.size();
         
         
         
         
         
         
     }
         
     }
    
    
    
    
    
