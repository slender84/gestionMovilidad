

package model.services;


import entities.CorreoConf;
import entities.InfoCuenta;
import entities.InfoCuenta;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import exceptions.PasswordIncorrectoException;
import java.util.ArrayList;
import java.util.List;
import model.dao.InfoCuentaDao;
import model.dao.UsuarioDao;
import model.utils.Seguridad;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService{
    
    @Autowired
    private UsuarioDao usuarioDao;

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }
    
    @Autowired
    private InfoCuentaDao intentosDao;

    public InfoCuentaDao getIntentosDao() {
        return intentosDao;
    }

    public void setIntentosDao(InfoCuentaDao intentosDao) {
        this.intentosDao = intentosDao;
    }
    
    
    
    
    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuario(String nombre)throws InstanceNotFoundException{
        
        return usuarioDao.buscar(nombre);
    }
    
    @Override
    public void eliminarUsuario(Usuario u){
        if(usuarioDao.existe(u.getLogin()))
        //usuarioDao.eliminar(u);
       usuarioDao.editar(u);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario>listarUsuarios(){
        
        
        
        List<Usuario> lista= (ArrayList<Usuario>)usuarioDao.listar();
        Usuario u=null;
        try{
        u=usuarioDao.buscar("admin");
        }catch(InstanceNotFoundException ex){
            
        }
        lista.remove(u);
        return lista;
        
        
    }
    
    @Override
    public void editarUsuario(Usuario u) throws InstanceNotFoundException{
        
        if(usuarioDao.existe(u.getLogin())==false){
            
            throw new InstanceNotFoundException();
        }else {
            
            usuarioDao.editar(u);
        }
        
    }
    
    
    @Override
    public void insertarUsuario(Usuario u){
        
        
            
            usuarioDao.insertar(u);
}
    @Override
    public void actualizarUsuario(Usuario u){
        
        if(usuarioDao.existe(u.getLogin()))
        usuarioDao.editar(u);
        
        
    }
    

    
    @Override
    public void autenticarUsuario(String password,Usuario u)throws PasswordIncorrectoException{
        
        password=Seguridad.md5Password(password);
            String pass=u.getPassword();
            if((pass.equals(password)==false)){
                
               throw new PasswordIncorrectoException();
            }
        
    
}

   @Override
   public String generarPassword(){
       
       //return UUID.randomUUID().toString();
       
       return Seguridad.generarPassword();
   }
   
   
   /*@Override
    public String md5Password(String password){
        
        
        try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
        
        
        
    }*/
   
   @Override
    public void enviarEmail(String login,String password,CorreoConf correoConf) throws EmailException{
        
    
       
        String mensaje="La contraseña es "+password+"\n Puedes cambiarla en la aplicación\n se te pedirá esta constraseña para hacerlo";
        Email email = new SimpleEmail();
        //email.setHostName("smtp.googlemail.com");
        //email.setHostName("smtp.gmail.com");
        email.setHostName(correoConf.getHostName());
        //email.setSmtpPort(489);    
        //email.setSmtpPort(465);
        email.setSmtpPort(correoConf.getSmtpPort());
        //email.setAuthenticator(new DefaultAuthenticator("registroerasmus@gmail.com", "registrousers"));
        email.setAuthenticator(new DefaultAuthenticator(correoConf.getDireccion(), correoConf.getPassword()));
        email.setSSLOnConnect(true);
        //email.setFrom("registro.erasmus@gmail.com");
        email.setFrom(correoConf.getDireccion());
        email.setSubject("Usuario creado");
        email.setMsg(mensaje);
        email.addTo(login+"@"+correoConf.getAddTo());
        //email.setTLS(true);
        
        email.send();
        
        
    }
    
    @Override
    public InfoCuenta recuperarIntentos(String login){
        
        return intentosDao.recuperarIntentos(login);
        
    }
    
    @Override
    public void actualizarIntentos(InfoCuenta intentos){
        
        intentosDao.actualizarIntentos(intentos);
        
    }
    
}

    
    
