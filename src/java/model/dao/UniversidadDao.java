/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


    
import entities.Cronica;
import entities.Universidad;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;


public interface UniversidadDao extends GenericDao<Universidad, String>{
    
    

    

    public List<Universidad> listarPorPais(String pais);
   public List<Cronica> listaLazyCronica(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
    public int countCronica(Map<String,Object>filters);
   
   
   
}


    

