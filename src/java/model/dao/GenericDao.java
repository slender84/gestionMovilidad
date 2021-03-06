
package model.dao;

import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.List;




public interface GenericDao<T, PK extends Serializable>{ 
    
   
   public void insert(T entity);
   public T find(PK id) throws InstanceNotFoundException;
   public List<T> list();
   public boolean exists(PK id); 
}
