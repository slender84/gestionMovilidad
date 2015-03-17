/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;

 
import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Movilidad;
import entities.Usuario;


import java.util.List;


public interface AsignaturaDao extends GenericDao<Asignatura, AsignaturaId>{
    
    
    public List<Asignatura> listarAsignaturasPorUniversidad(boolean disponible,String codUniversidad);
    public void insertarComentario(ComentarioAsignatura c);
    public void eliminarComentario(ComentarioAsignatura c);
    public void editarComentario(ComentarioAsignatura c);
    public List<Asignatura> listarAsignaturasPorUniversidadYCurso (boolean disponible,String nombreUniversidad, String curso);
    public List<ComentarioAsignatura> listarComentariosAsignatura(Usuario usuario);
    public List<Asignatura> asignaturasMovilidad(Movilidad m);
    public List<ComentarioAsignatura> listarComentariosPorAsignatura(AsignaturaId id);
}
