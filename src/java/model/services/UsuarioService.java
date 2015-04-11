/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;

import entities.Configuracion;
import entities.Direccion;
import entities.InfoCuenta;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import exceptions.PasswordIncorrectoException;
import java.util.List;
import org.apache.commons.mail.EmailException;



public interface UsuarioService {
    
    public Usuario buscarUsuario(String nombre)throws InstanceNotFoundException;
    public void eliminarUsuario(Usuario u);
    public List<Usuario> listarUsuarios();
     public void insertarUsuario(Usuario u);
    public void actualizarUsuario(Usuario u);
    public void autenticarUsuario(String password,Usuario u) throws PasswordIncorrectoException;
    public String generarPassword();
     public void enviarEmail(String login,String password,Configuracion correoConf) throws EmailException;
     public InfoCuenta recuperarIntentos(String login);
     public void actualizarIntentos(InfoCuenta intentos);
     public void editarUsuario(Usuario u)throws InstanceNotFoundException;
     public void eliminarDireccion(Direccion d);
     public void crearDireccion(Direccion d);
     public List<Direccion> listaDirecciones();
}
