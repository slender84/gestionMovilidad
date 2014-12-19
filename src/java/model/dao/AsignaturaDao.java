/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;

 
import entities.Asignatura;
import java.util.List;


public interface AsignaturaDao{
    
    public void crearAsignatura(Asignatura a);
    public List<Asignatura> listarAsignaturas();
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad);
    public void eliminaAsignatura(Asignatura a);
    public void actualizarAsignatura(Asignatura a);
    public List<Asignatura> listarPorCriterio();
}
