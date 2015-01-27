/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Mensaje;
import java.util.Date;
import java.util.List;


public interface MensajeDao extends GenericDao<Mensaje, Integer>{

    public void crearMensaje(Mensaje m);
    public List<Mensaje> mensajesEnviados(String origen,String destino);
    public List<Mensaje> mensajesRecibidos(String origen,String destino);
   
    public List<Mensaje> mensajesEnviadosTotal(String origen);
    public List<Mensaje> mensajesRecibidosTotal(String destino);
    
    
}
