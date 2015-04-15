
package model.utils;

import entities.Configuracion;
import entities.Direccion;
import entities.Usuario;
import java.util.Iterator;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class email {

    
    
    
    
     public static void enviarEmailAlta(String login,String password,Configuracion correoConf) throws EmailException{
        
    //String[] direcciones=new String[]{"pedro.olartev@gmail.com","rlcjxc@hotmail.es"};
       
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
        //email.addBcc(direcciones);
        //email.setTLS(true);
        
        email.send();
        
        
    }
    
    public static void enviarEmailAdmin(String login,Configuracion correoConf,String asunto,String mensaje,Usuario... direcciones) throws EmailException{
        
        
        //String[] direcciones=new String[]{"pedro.olartev@gmail.com","rlcjxc@hotmail.es"};
        
        
       
        
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
        email.setSubject(asunto);
        email.setMsg(mensaje);
        email.addTo(login+"@"+correoConf.getAddTo());
        if(direcciones.length>0){
            
            String[] direccionesD=loginADireccion(direcciones, correoConf.getAddTo());
            
            email.addBcc(direccionesD);
            
        }
         
        //email.setTLS(true);
        
        email.send();
        
        
        
    }
     
     
    
    public static void enviarEmailUsuario(Configuracion correoConf,String asunto,String mensaje,int opcion)throws EmailException{
        
        
        
        
        String[] direcciones=llenarArray(correoConf);
        
        
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
        email.setSubject(asunto);
        email.setMsg(mensaje);
        email.addTo(correoConf.getDireccionAdmin());
        //System.out.println(correoConf.getDireccionAdmin());
        if(correoConf.getPermitirCopia()==true&&correoConf.getDireccions().size()>0&&opcion!=0)
         email.addBcc(direcciones);
        
        
        //email.setTLS(true);
        
        email.send();
        
        
    }
     
    
    public static String[] llenarArray(Configuracion correoConf){
        
        int j=0;
        String[] direcciones=new String[correoConf.getDireccions().size()];
        Iterator i=correoConf.getDireccions().iterator();
        while(i.hasNext()){
            
            Direccion aux=(Direccion)i.next();
            direcciones[j]=aux.getDireccion();
            j++;
            
        }
        
        return direcciones;
    }
     
    
    public static String[] loginADireccion(Usuario[] login,String addTo){
        
        
        
        
        String[] direcciones=new String[login.length-1];
        
        for(int i=1;i<login.length;i++){
            
            String aux=login[i].getLogin()+"@"+addTo;
            
            direcciones[i-1]=aux;
            
            
        }
        
        return direcciones;
        
        
    }
     
    
    
    
    
}
