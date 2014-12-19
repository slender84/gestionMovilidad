/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;

import entities.Mensaje;
import java.util.List;


public interface MensajeService {
    
public List<Mensaje> mensajesEnviados(String origen,String destino);
public List<Mensaje> mensajesRecibidos(String origen,String destino);
public void enviarMensaje(Mensaje m);
public List<Mensaje> mensajesRecibidosTotal(String destino);
public List<Mensaje> mensajesEnviadosTotal(String origen);
public void eliminarMensaje(Mensaje m,String accion);
public Mensaje find(Integer msgId);
public void leerMensajeRecibido(Mensaje m);
}
