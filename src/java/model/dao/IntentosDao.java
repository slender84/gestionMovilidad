
package model.dao;

import entities.Intentos;


public interface IntentosDao {
    
    public Intentos recuperarIntentos(String login);
    public void actualizarIntentos(Intentos intentos);
    
}
