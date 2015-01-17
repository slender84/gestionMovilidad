package model.services;

import entities.Cronica;
import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import java.util.Date;
import java.util.List;


/**
 *
 * @author cba
 */
public interface UniversidadService {
    
    
    public List<Pais>listarPaises();
    public List<Universidad>listarUniversidades();
    public Pais buscarPais(String pais) throws InstanceNotFoundException;
    public void insertarPais(Pais pais);
    public void eliminarPais(Pais p);
    public void eliminarUniversidad(Universidad u) throws InstanceNotFoundException ;
    public void insertarUniversidad(Universidad u);
    public void actualizarUniversidad(Universidad u) throws InstanceNotFoundException;
    public List<Universidad> listarPorPais(String pais);
    public void crearCursoAcademico(Cursoacademico cursoAcademico);
    public void eliminarCursoAcademico(Cursoacademico c);
    public List<Cursoacademico> listarCursosAcademicos();
    public Universidad buscarUniversidad(String universidad) throws InstanceNotFoundException;
    public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin);
    public List<Cronica>listarCronicasPorUniversidad(Universidad universidad);
    public void enviarCronica(Cronica c);
}
