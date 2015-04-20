/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;


import entities.Contrato;
import entities.Cursoacademico;
import entities.Movilidad;
import entities.Universidad;
import entities.Usuario;
import exceptions.DuracionException;
import exceptions.InstanceNotFoundException;
import exceptions.NumeroDeMovilidadesException;
import java.util.Date;
import java.util.List;
import java.util.Map;



public interface MovilidadService {
    
    public List<Movilidad> listarTodasMovilidades();
  
    public Date fechaMin();
    public Date fechaMax();
    public void crearMovilidad(Movilidad m);
    public List<Movilidad> listarMisMovilidades(String user);
    public void eliminarMovilidad(Movilidad m);
    public Movilidad buscarMovilidad(Integer id) throws InstanceNotFoundException;
    public void crearMovilidad(Date fechaInicio,Date fechaFin,Usuario user,Universidad u,Cursoacademico ca) throws DuracionException,NumeroDeMovilidadesException;
    public void editarMovilidad(Movilidad m);
    public List<Movilidad> listarMovilidadPorFiltro(Map<String,String> listaFiltros);
    public List<Contrato> listarContratosPendientes();
    public List<Contrato> listarTodosContratos();
    public List<Contrato> listarContratosPorFiltro(Map<String,String> listaFiltros);
}
