/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.services;

import entities.CorreoConf;
import entities.Usuario;
import exceptions.PasswordIncorrectoException;
import exceptions.UsuarioNotFoundException;
import java.util.List;
import org.apache.commons.mail.EmailException;



public interface UsuarioService {
    
    public Usuario find(String nombre)throws UsuarioNotFoundException;
    public void delete(Usuario u);
    public List<Usuario> listar();
    
    public void insertarUsuario(Usuario u);
    public void actualizar(Usuario u);
    public String md5Password(String password);
    public void autenticarUsuario(String password,Usuario u) throws PasswordIncorrectoException;
    public String generarPassword();
     public void enviarEmail(String login,String password,CorreoConf correoConf) throws EmailException;
}
