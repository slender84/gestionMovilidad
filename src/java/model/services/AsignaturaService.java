/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;

import entities.Asignatura;
import exceptions.InstanceNotFoundException;
import java.util.List;

/**
 *
 * @author abc
 */
public interface AsignaturaService {
    
    public void crearAsignatura(Asignatura a);
    public List<Asignatura> listarAsignaturas();
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad);
    public void eliminarAsignatura(Asignatura a)throws InstanceNotFoundException;
    public void actualizarAsignatura(Asignatura a)throws InstanceNotFoundException;
    
}
