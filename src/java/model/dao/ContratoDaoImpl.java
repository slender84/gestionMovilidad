
package model.dao;

import entities.Contrato;
import entities.Movilidad;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("contratoDao")
public class ContratoDaoImpl extends GenericDaoHibernate<Contrato, Integer> implements ContratoDao{
    
    @Override
    public List<Contrato> listaContratos(Movilidad m){
        
        return getSession().createQuery("select c from Contrato c where c.movilidad=:movilidad order by c.fecha desc").setParameter("movilidad", m).list();
    
}
}