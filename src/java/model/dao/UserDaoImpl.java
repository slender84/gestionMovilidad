
package model.dao;

import entities.Usuario;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoHibernate<Usuario, String> implements UserDao{
    
    
  /* @Override
   public List<Usuario> list(){
       
       return getSession().createCriteria(Usuario.class).list();
   } */
}
