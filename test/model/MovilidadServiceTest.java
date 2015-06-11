
package model;

import entities.Movilidad;
import exceptions.InstanceNotFoundException;
import static junit.framework.Assert.assertEquals;
import static model.EquivalenciasServiceTest.SPRING_CONFIG_FILE;
import model.services.MovilidadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MovilidadServiceTest {
    public static final String SPRING_CONFIG_FILE =
        "classpath:/applicationContext.xml";
    
    
    @Autowired
    private MovilidadService movilidadService;

    public MovilidadService getMovilidadService() {
        return movilidadService;
    }

    public void setMovilidadService(MovilidadService movilidadService) {
        this.movilidadService = movilidadService;
    }
    
    
    
    @Test(expected = InstanceNotFoundException.class)
    public void buscarMovilidadTest()throws InstanceNotFoundException{
        
        
        Movilidad m=movilidadService.buscarMovilidad(-1);
        
     
    }
    
    @Test
    public void actualizarMovilidadTest()throws InstanceNotFoundException{
    
        Integer i=2;
    Movilidad m=movilidadService.buscarMovilidad(i);
   m.setEstado("pendiente");
   movilidadService.editarMovilidad(m);
   Movilidad m2=movilidadService.buscarMovilidad(i);
    
        assert m2.getEstado().equals("pendiente");
    
    
}
    @Test(expected = InstanceNotFoundException.class)
    public void eliminarMovilidad()throws InstanceNotFoundException{
        
        
        Movilidad m=movilidadService.buscarMovilidad(2);
        movilidadService.eliminarMovilidad(m);
        movilidadService.buscarMovilidad(2);
        
    }
    
    
    
}
