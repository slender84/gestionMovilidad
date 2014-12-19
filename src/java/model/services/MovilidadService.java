/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;


import entities.Cursoacademico;
import entities.Movilidad;
import entities.Universidad;
import entities.Usuario;
import exceptions.DuracionException;
import exceptions.NumeroDeMovilidadesException;
import java.util.Date;
import java.util.List;



public interface MovilidadService {
    
    public List<Movilidad> listarTodasMovilidades();
    public List<Movilidad> listarMovilidadesPendientes();
    public Date fechaMin();
    public Date fechaMax();
    public void crearMovilidad(Movilidad m);
    public List<Movilidad> listarMisMovilidades(String user);
    public List<Movilidad> listarMisMovilidadesPorEstado(String user,String estado);
    public void eliminarMovilidad(Movilidad m);
    public List<Object> doJoin();
    public List<Movilidad> listarMovilidadesValidas(String usuario);
    public Movilidad findMovilidad(Integer id);
    public void crearMovilidad(Date fechaInicio,Date fechaFin,Usuario user,Universidad u,Cursoacademico ca) throws DuracionException,NumeroDeMovilidadesException;
   
}
