
package model.dao;

import entities.Configuracion;
import entities.Contrato;
import entities.Movilidad;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;


public interface ContratoDao extends GenericDao<Contrato, Integer>{
    
    public List<Contrato> listarContratos(Movilidad m);
    public List<Contrato> listarContratosPendientes();
    public List<Contrato> listarTodosContratos();
    public List<Contrato> listarContratosPorFiltro(Map<String,String> listaFiltros);
    public List<Contrato> listaLazyContrato(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf);
    public int countContrato(Map<String,Object> filters);
    public List<Contrato> listaLazyContratoPrueba(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf);
    public int countContratoPrueba(Map<String,Object> filters);
    public List<Contrato> listaLazyContratoFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf);
    public int countContratoFilter(Map<String,Object> filters);
    public List<Contrato> listaLazyContratoFilter2(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf);
    public int countContratoFilter2(Map<String,Object> filters);
}
