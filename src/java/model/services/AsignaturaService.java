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
    public List<Asignatura> listarAsignaturasPorUniversidadYCurso(String nombreUniversidad, String curso);
    public void eliminarAsignatura(Asignatura a)throws InstanceNotFoundException;
    public void actualizarAsignatura(Asignatura a)throws InstanceNotFoundException;
    public void crearIdioma(Idioma i);
    public void eliminarIdioma(Idioma i);
    public List<Idioma>listarIdiomas();
    public Asignatura buscarAsignatura(AsignaturaId id)throws InstanceNotFoundException;
    public void insertarComentario(ComentarioAsignatura c);
    public void eliminarComentario(ComentarioAsignatura c);
    
}
