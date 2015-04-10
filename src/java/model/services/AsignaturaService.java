/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;

import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Idioma;
import entities.Movilidad;
import entities.Usuario;

import exceptions.InstanceNotFoundException;
import java.util.List;

/**
 *
 * @author abc
 */
public interface AsignaturaService {
    
    public void crearAsignatura(Asignatura a);
    public List<Asignatura> listarAsignaturas();
    public List<Asignatura> listarAsignaturasPorUniversidad(boolean disponible,String codUniversidad);
    public List<Asignatura> listarAsignaturasPorUniversidadYCurso(boolean disponible,String nombreUniversidad, String curso);
    public void eliminarAsignatura(Asignatura a)throws InstanceNotFoundException;
    public void actualizarAsignatura(Asignatura a)throws InstanceNotFoundException;
    public void crearIdioma(Idioma i);
    public void eliminarIdioma(Idioma i);
    public List<Idioma>listarIdiomas();
    public Asignatura buscarAsignatura(AsignaturaId id)throws InstanceNotFoundException;
    public void insertarComentario(ComentarioAsignatura c);
    public void eliminarComentario(ComentarioAsignatura c);
    public List<ComentarioAsignatura> listarComentariosAsignatura(Usuario usuario);
    public void editarComentario(ComentarioAsignatura c) ;
    public List<Asignatura> asignaturasMovilidad(Movilidad m);
    public List<ComentarioAsignatura> listarComentariosPorAsignatura(AsignaturaId id);
    public ComentarioAsignatura buscarComentarioAsignatura(Integer id) throws InstanceNotFoundException;
    public List<ComentarioAsignatura> listarComentariosAsignaturaPendientes();
}
