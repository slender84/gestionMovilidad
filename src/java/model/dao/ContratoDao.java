
package model.dao;

import entities.Contrato;
import entities.Movilidad;
import java.util.List;
import java.util.Map;


public interface ContratoDao extends GenericDao<Contrato, Integer>{
    
    public List<Contrato> listarContratos(Movilidad m);
    public List<Contrato> listarContratosPendientes();
    public List<Contrato> listarTodosContratos();
    public List<Contrato> listarContratosPorFiltro(Map<String,String> listaFiltros);
}
