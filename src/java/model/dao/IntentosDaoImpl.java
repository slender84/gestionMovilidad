
package model.dao;

import entities.Intentos;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("intentosDao")
public class IntentosDaoImpl implements IntentosDao{
    
    
      @Autowired
      private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
      
      
    @Override
    public Intentos recuperarIntentos(String login){
        
      return (Intentos) getSessionFactory().getCurrentSession().get(Intentos.class, login);
        
        
    }
    
    @Override
    public void actualizarIntentos(Intentos intentos){
        
        getSessionFactory().getCurrentSession().saveOrUpdate(intentos);
    }
    
    
}
