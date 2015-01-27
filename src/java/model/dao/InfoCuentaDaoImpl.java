
package model.dao;

import entities.InfoCuenta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("infoCuentaDao")
public class InfoCuentaDaoImpl implements InfoCuentaDao{
    
    
      @Autowired
      private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
      
      
    @Override
    public InfoCuenta recuperarIntentos(String login){
        
      return (InfoCuenta) getSessionFactory().getCurrentSession().get(InfoCuenta.class, login);
        
        
    }
    
    @Override
    public void actualizarIntentos(InfoCuenta intentos){
        
        getSessionFactory().getCurrentSession().saveOrUpdate(intentos);
    }
    
    
}

