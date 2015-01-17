
package model.dao;

import entities.Cronica;
import entities.Universidad;
import java.util.List;


public interface CronicaDao extends GenericDao<Cronica,Integer> {
    
    
    public List<Cronica> listarCronicasPorUniversidad(Universidad universidad);
    
    
    
}
