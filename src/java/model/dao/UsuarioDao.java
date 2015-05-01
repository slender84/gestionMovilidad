/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;



import entities.Usuario;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;



public interface UsuarioDao extends GenericDao<Usuario, String>{

    
   public List<Usuario> listaLazyUsuario(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
     public int countUsuario(Map<String,Object>filters);
    
        
        
    
}
