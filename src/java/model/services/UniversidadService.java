package model.services;

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
    
    
    public List<Pais>listaPaises();
    public List<Universidad>listaUniversidades();
    public Pais findPais(String pais) throws InstanceNotFoundException;
    public void insertarPais(String pais);
    public void deletePais(Pais p);
    public void delete(Universidad u) throws InstanceNotFoundException ;
    public void insertarUniversidad(Universidad u);
    public void actualizar(Universidad u) throws InstanceNotFoundException;
    public List<Universidad> listarPorPais(String pais);
    public void crearCursoAcademico(Cursoacademico cursoAcademico);
    public void eliminarCursoAcademico(Cursoacademico c);
    public List<Cursoacademico> listaCursosAcademicos();
    public Universidad findUniversidad(String universidad) throws InstanceNotFoundException;
    public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin);
}
