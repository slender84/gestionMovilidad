package model.dao;
import entities.Universidad;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;








@Repository("universidadDao")
public class UniversidadDaoImpl extends GenericDaoHibernate<Universidad, String> implements UniversidadDao{
    
    
    @Override
     public List<Universidad> listarPorPais(String pais){
    
    Query q=getSession().createQuery("select u from Universidad u where u.pais.nombre=:pais");
    q.setParameter("pais",pais);
    return(q.list());
}
    
    
   
    
    
}
