
package entities;

public class CorreoConf  implements java.io.Serializable {


     private Integer id;
     private String direccion;
     private String password;
     private Integer smtpPort;
     private String hostName;
     private String addTo;

    public CorreoConf() {
    }

    public CorreoConf(String direccion, String password, Integer smtpPort, String hostName, String addTo) {
       this.direccion = direccion;
       this.password = password;
       this.smtpPort = smtpPort;
       this.hostName = hostName;
       this.addTo = addTo;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getSmtpPort() {
        return this.smtpPort;
    }
    
    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }
    public String getHostName() {
        return this.hostName;
    }
    
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public String getAddTo() {
        return this.addTo;
    }
    
    public void setAddTo(String addTo) {
        this.addTo = addTo;
    }




}
