
package model.dao;

import entities.Contrato;
import entities.Movilidad;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Restrictions;
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
         
         
         
     }
    
    
    
    
    
