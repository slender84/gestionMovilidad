/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


    
import entities.Universidad;
import java.util.List;


public interface UniversidadDao extends GenericDao<Universidad, String>{
    
    

    

    public List<Universidad> listarPorPais(String pais);
   
   
   
   
}


    

