/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;


import entities.Mensaje;
import exceptions.InstanceNotFoundException;
import java.util.List;
import java.util.Map;
import model.dao.MensajeDao;
import org.primefaces.model.SortOrder;
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
    public void eliminarMensaje(Mensaje m,String accion)throws InstanceNotFoundException{
        
        
         Mensaje aux=buscarMensaje(m.getIdmensaje());
            
            if(aux!=null){
                
                
                if(accion.equals("recibido")){
            aux.setEliminadoDestino(true);
                
             
                if(aux.getEliminadoOrigen()==(true)){
                    
                    mensajeDao.eliminar(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }else{
                    if(accion.equals("enviado")){
            aux.setEliminadoOrigen(true);
                
             
                if(aux.getEliminadoDestino()==true){
                    
                    mensajeDao.eliminar(aux);
                }else{
                    
                     enviarMensaje(aux);
                }
              }
                }
            }
                
            
    }
    
    @Override
    @Transactional(readOnly = true)
    public Mensaje buscarMensaje(Integer msgId) throws InstanceNotFoundException{
        
        return mensajeDao.buscar(msgId);
    }
    


    @Override
    public void leerMensajeRecibido(Mensaje selectedMensajeRecibido) throws InstanceNotFoundException{

            Mensaje aux=buscarMensaje(selectedMensajeRecibido.getIdmensaje());
            
            
            if(aux!=null){
            aux.setLeidoDestino(true);
                enviarMensaje(aux);
            
        
            }
      
}
    
    @Override
    public List<Mensaje> listaLazyMensajeRecibido(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,String destino){
        
        return mensajeDao.listaLazyMensajeRecibido(first, pageSize, sortField, sortOrder, filters,destino);
        
        
    }
    
    @Override
    public int countMensajeRecibido(Map<String,Object>filters,String destino){
        
        return mensajeDao.countMensajeRecibido(filters,destino);
        
    }
    
    
    
    
}