
package model.dao;

import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.List;




public interface GenericDao<T, PK extends Serializable>{ 
    
   
   public void insertar(T entity);
   public T buscar(PK id) throws InstanceNotFoundException;
   public void editar(T entity);
   public List<T> listar();
   public boolean existe(PK id); 
   public void eliminar(T entity);
}
