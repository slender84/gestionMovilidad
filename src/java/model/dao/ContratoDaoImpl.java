
package model.dao;

import entities.Contrato;
import entities.Movilidad;
import java.util.List;
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
    
}