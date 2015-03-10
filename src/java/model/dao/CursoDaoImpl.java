
package model.dao;

import entities.Curso;
import org.springframework.stereotype.Repository;


@Repository("cursoDao")
public class CursoDaoImpl extends GenericDaoHibernate<Curso, String> implements CursoDao{
    
}
