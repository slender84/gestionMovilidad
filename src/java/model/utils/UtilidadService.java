package model.utils;

import entities.Configuracion;
import entities.Estado;
import entities.EstadoMovilidad;
import java.util.List;


public interface UtilidadService {
    
    public void crearEstado(Estado e);
    public List<Estado> listaEstados();
    public void eliminaEstado(Estado e);
    
    public void crearEstadoMovilidad(EstadoMovilidad e);
    public List<EstadoMovilidad> listaEstadosMovilidad();
    public void eliminaEstadoMovilidad(EstadoMovilidad e);
    
    
    public Configuracion getCorreoConf();
    public void setCorreoConf(Configuracion correoConf);
    
            
}