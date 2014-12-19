/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Movilidad;
import java.util.List;


public interface MovilidadDao {
    
    public void crearMovilidad(Movilidad m);
    public List<Movilidad> listarMovilidad();
    public void cambiarMovilidad(Movilidad m);
    public void eliminarMovilidad(Movilidad m);
    
    public List<Movilidad> listarPorEstado(String estado);
    public List<Movilidad> listarMisMovilidades(String user);
    public List<Movilidad> listarMisMovilidadesPorEstado(String user, String estado);
    
    public List<Movilidad> listarMovilidadesValidas(String usuario);
    
    public Movilidad findMovilidad(Integer id);
    
    public List<Object> doJoin();
        
        
     
    
    
    
}
