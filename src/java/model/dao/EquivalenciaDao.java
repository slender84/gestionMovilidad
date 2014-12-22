/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;



import entities.Equivalencia;
import java.util.List;


public interface EquivalenciaDao extends GenericDao<Equivalencia, Integer> {
   public List<Equivalencia> listarEquivalenciasPorContrato(Integer id);
   public List<Equivalencia> equivalenciasPublicas(String universidad);
   
}
