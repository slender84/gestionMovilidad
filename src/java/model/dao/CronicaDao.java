
package model.dao;

import entities.Cronica;
import entities.Usuario;
import java.util.List;


public interface CronicaDao extends GenericDao<Cronica,Integer> {
    
    
    public List<Cronica> listarCronicasPorUniversidad(String universidad);
    public List<Cronica> listarMisCronicas(Usuario u);
    
    
}
