
package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import model.services.UniversidadService;



@ManagedBean
@ViewScoped
public class CronicasController {

    @ManagedProperty(value="#{universidadService}")
    private UniversidadService universidadService;

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
    
    
    
    
    public CronicasController() {
    }
    
}
