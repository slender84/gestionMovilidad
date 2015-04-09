
package model.dao;

import entities.Contrato;
import entities.Movilidad;
import java.util.List;


public interface ContratoDao extends GenericDao<Contrato, Integer>{
    
    public List<Contrato> listarContratos(Movilidad m);
    public List<Contrato> listarContratosPendientes();
}
