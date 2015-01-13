/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Pais;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import static junit.framework.Assert.assertEquals;
import static model.UniversidadServiceTest.SPRING_CONFIG_FILE;
import model.services.UniversidadService;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UniversidadServiceTest {
    public static final String SPRING_CONFIG_FILE =
        "classpath:/applicationContext.xml";
   private final long NON_EXISTENT_UNI_ID = -1;
    private final String NON_EXISTENT_PAIS = "NonExistentPais";
    private final long NON_EXISTENT_PAIS_ID2 = -2;
    
    private final String NON_EXISTENT_UNI="NonExistentUni";
    
    
   @Autowired 
   UniversidadService universidadService;

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
   
   @Test
    public void testCrearPaisYBuscarPais()throws InstanceNotFoundException{
        
        Pais pais=new Pais("pais1");
        universidadService.insertarPais(pais);
        Pais pais2=universidadService.buscarPais(pais.getNombre());
        assertEquals(pais2, pais);
        
    }
    
    
     @Test(expected = InstanceNotFoundException.class)
    public void testBuscarPaisNoExistente() throws InstanceNotFoundException {

        universidadService.buscarPais(NON_EXISTENT_PAIS);

    }
    
   
   
    @Test
    public void testCrearUniversidadYBuscarUniversidad()
        throws InstanceNotFoundException {

        Universidad universidad=new Universidad("uni1", "1");
        universidadService.insertarUniversidad(universidad);
        Universidad universidad2 = universidadService.buscarUniversidad("uni1");

        assertEquals(universidad, universidad2);

    }
    
    
    @Test(expected = InstanceNotFoundException.class)
    public void testBuscarUniversidadNoExistente() throws InstanceNotFoundException {

        universidadService.buscarUniversidad(NON_EXISTENT_UNI);

    }
    
    
    @Test(expected = InstanceNotFoundException.class)
    public void testEliminarUniversidad() throws InstanceNotFoundException{
        
        Universidad u=new Universidad();
        universidadService.eliminarUniversidad(u);
        
        try{
            
            Universidad u2=universidadService.buscarUniversidad(u.getNombre());
            
        }catch(InstanceNotFoundException ex){
            
            
            
        } 
        
       
       
    }
    
    
}
