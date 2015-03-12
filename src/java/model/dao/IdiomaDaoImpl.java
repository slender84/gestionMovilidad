
package model.dao;

import entities.Idioma;
import org.springframework.stereotype.Repository;

@Repository("idiomaDao")
public class IdiomaDaoImpl extends GenericDaoHibernate<Idioma, String> implements IdiomaDao {
    
}
