/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;

 
import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;

import java.util.List;


public interface AsignaturaDao extends GenericDao<Asignatura, AsignaturaId>{
    
    
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad);
    public void insertarComentario(ComentarioAsignatura c);
    public void eliminarComentario(ComentarioAsignatura c);
    public List<Asignatura> listarAsignaturasPorUniversidadYCurso (String nombreUniversidad, String curso);
    
}
