
package model.dao;

import entities.Pais;
import org.springframework.stereotype.Repository;

@Repository("paisDao")
public class PaisDaoImpl extends GenericDaoHibernate<Pais, String> implements PaisDao {
    
}
