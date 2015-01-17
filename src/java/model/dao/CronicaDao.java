
package model.dao;

import entities.Cronica;
import entities.Universidad;
import entities.Usuario;
import java.util.List;


public interface CronicaDao extends GenericDao<Cronica,Integer> {
    
    
    public List<Cronica> listarCronicasPorUniversidad(Universidad universidad);
    public List<Cronica> listarMisCronicas(Usuario u);
    
    
}
