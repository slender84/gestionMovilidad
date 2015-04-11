
package model.dao;

import entities.Direccion;
import org.springframework.stereotype.Repository;

@Repository("direccionDao")
public class DireccionDaoImpl extends GenericDaoHibernate<Direccion, String> implements DireccionDao{
    
}
