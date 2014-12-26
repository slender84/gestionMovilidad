

package model.services;


import entities.CorreoConf;
import entities.Intentos;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import exceptions.PasswordIncorrectoException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.dao.IntentosDao;
import model.dao.UsuarioDao;
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
    private IntentosDao intentosDao;

    public IntentosDao getIntentosDao() {
        return intentosDao;
    }

    public void setIntentosDao(IntentosDao intentosDao) {
        this.intentosDao = intentosDao;
    }
    
    
    
    
    @Override
    @Transactional(readOnly = true)
    public Usuario find(String nombre)throws InstanceNotFoundException{
        
        return usuarioDao.find(nombre);
    }
    
    @Override
    public void delete(Usuario u){
        if(usuarioDao.exists(u.getLogin()))
        usuarioDao.delete(u);
       
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario>listar(){
        
        
        
        List<Usuario> lista= (ArrayList<Usuario>)usuarioDao.list();
        Usuario u=null;
        try{
        u=usuarioDao.find("admin");
        }catch(InstanceNotFoundException ex){
            
        }
        lista.remove(u);
        return lista;
        
        
    }
    
    
    
    
    @Override
    public void insertarUsuario(Usuario u){
        
        
            
            usuarioDao.insert(u);
}
    @Override
    public void actualizar(Usuario u){
        
        if(usuarioDao.exists(u.getLogin()))
        usuarioDao.edit(u);
        
        
    }
    

    
    @Override
    public void autenticarUsuario(String password,Usuario u)throws PasswordIncorrectoException{
        
        password=md5Password(password);
            String pass=u.getPassword();
            if((pass.equals(password)==false)){
                
               throw new PasswordIncorrectoException();
            }
        
    
}

   @Override
   public String generarPassword(){
       
       return UUID.randomUUID().toString();
       
       
   }
   
   
   @Override
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
        
        
        
    }
   
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
    public Intentos recuperarIntentos(String login){
        
        return intentosDao.recuperarIntentos(login);
        
    }
    
    @Override
    public void actualizarIntentos(Intentos intentos){
        
        intentosDao.actualizarIntentos(intentos);
        
    }
    
}

    
    
