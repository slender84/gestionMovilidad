

package model.services;

import entities.Contrato;
import entities.Equivalencia;
import entities.Movilidad;
import exceptions.ContratoNotFoundException;
import exceptions.FechaIncorrectaException;
import exceptions.MovilidadNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.utils.EquivalenciaRevisada;



public interface EquivalenciaService {
    
    
    public void crearEquivalencia(Equivalencia e);
    public void eliminarEquivalencia(Equivalencia e);
    public void actualizarEquivalencia(Equivalencia e);
    public List<Equivalencia> listarEquivalencias();
    public List<Equivalencia> listarEquivalenciasPorContrato(Integer id);
    public void creaContrato(Contrato c);
    public void modificaContrato(Contrato c);
    public List<Contrato> listaContratos(Movilidad m);
    public void eliminaContrato(Contrato c);
    public Contrato findContrato(Integer id) throws ContratoNotFoundException;
   public List<Equivalencia> equivalenciasPublicas(String universidad);
   public int[] totalCreditos(ArrayList<Equivalencia> lista);
   public void confirmarContrato(ArrayList<Equivalencia> lista,Contrato c);
   public ArrayList<Equivalencia> editarContrato(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c);
   public void crearContratoDesdeAceptado(ArrayList<Equivalencia>listaAuxEquivalencias,Contrato c, Contrato cNuevo);
   public void compruebaFechaCrearContrato(Contrato c,Date aux)throws FechaIncorrectaException;
   public ArrayList<EquivalenciaRevisada> compararEquivalencias(ArrayList<Equivalencia> listaAuxEquivalencias,ArrayList<Equivalencia> listaAuxEquivalenciasComparado);
    public Contrato verContratoPorEquivalencia(Equivalencia e)throws ContratoNotFoundException;
    public Movilidad buscarMovilidadPorContrato(Contrato c)throws ContratoNotFoundException,MovilidadNotFoundException;
}


