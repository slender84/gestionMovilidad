package model.dao;

import entities.InfoCuenta;


public interface InfoCuentaDao {
    
    public InfoCuenta recuperarIntentos(String login);
    public void actualizarIntentos(InfoCuenta intentos);
    
}