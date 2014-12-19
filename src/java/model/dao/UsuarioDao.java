/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao;


import entities.Usuario;
import java.util.List;


public interface UsuarioDao {

    public Usuario find(String name);
    public void delete(Usuario u);
    public List<Usuario> listar();
    public void insertarUsuario(Usuario u)throws org.springframework.dao.DataIntegrityViolationException;
    public void actualizar(Usuario u);
   
    
        
        
    
}
