/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Movilidad;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;


public interface MovilidadDao extends GenericDao<Movilidad, Integer>{
   
    public List<Movilidad> listarMisMovilidades(String user);
   public List<Movilidad> listarMovilidadPorFiltro(Map<String,String> listaFiltros);
    public List<Movilidad> listaLazy(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
     public int count(Map<String,Object>filters);
  
        
        
     
    
    
    
}
