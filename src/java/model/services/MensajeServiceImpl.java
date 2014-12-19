/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;


import entities.Mensaje;
import java.util.List;
import model.dao.MensajeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("mensajeService")
public class MensajeServiceImpl implements MensajeService{
    
    @Autowired
    private MensajeDao mensajeDao;

    public MensajeDao getMensajeDao() {
        return mensajeDao;
    }

    public void setMensajeDao(MensajeDao mensajeDao) {
        this.mensajeDao = mensajeDao;
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> mensajesEnviados(String origen,String destino){
        
        return mensajeDao.mensajesEnviados(origen, destino);
    }
    
     @Override
     @Transactional(readOnly = true)
    public List<Mensaje> mensajesRecibidos(String origen,String destino){
        
        return mensajeDao.mensajesRecibidos(origen, destino);
    }
    
    @Override
    public void enviarMensaje(Mensaje m){
        mensajeDao.crearMensaje(m);
        
    }
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> mensajesEnviadosTotal(String destino){
        
        return mensajeDao.mensajesEnviadosTotal(destino);
        
        
    }
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> mensajesRecibidosTotal(String origen){
        
        return mensajeDao.mensajesRecibidosTotal(origen);
    }
    
    
    @Override
    public void eliminarMensaje(Mensaje m,String accion){
        
        
         Mensaje aux=find(m.getIdmensaje());
            
            if(aux!=null){
                
                
                if(accion.equals("recibido")){
            aux.setEliminadoDestino("si");
                
             
                if(aux.getEliminadoOrigen().equals("si")){
                    
                    mensajeDao.eliminarMensaje(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }else{
                    if(accion.equals("enviado")){
            aux.setEliminadoOrigen("si");
                
             
                if(aux.getEliminadoDestino().equals("si")){
                    
                    mensajeDao.eliminarMensaje(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }
                }
            }
                
            
    }
    
    @Override
    @Transactional(readOnly = true)
    public Mensaje find(Integer msgId){
        
        return mensajeDao.find(msgId);
    }
    


    @Override
    public void leerMensajeRecibido(Mensaje selectedMensajeRecibido){

            Mensaje aux=find(selectedMensajeRecibido.getIdmensaje());
            
            
            if(aux!=null){
            aux.setLeidoDestino("si");
                enviarMensaje(aux);
            
        
            }
      
}
    
    
    
}