/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Universidad;
import exceptions.InstanceNotFoundException;
import static junit.framework.Assert.assertEquals;
import static model.UniversidadServiceTest.SPRING_CONFIG_FILE;
import model.services.UniversidadService;
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
    private final long NON_EXISTENT_PAIS_ID = -1;
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
    public void testCrearUniversidadYBuscarUniversidad()
        throws InstanceNotFoundException {

        Universidad universidad=new Universidad("uni1", "1");
        universidadService.insertarUniversidad(universidad);
        Universidad universidad2 = universidadService.findUniversidad("1");

        assertEquals(universidad, universidad2);

    }
    
    
    @Test(expected = InstanceNotFoundException.class)
    public void testBuscarUniversidadNoExistente() throws InstanceNotFoundException {

        universidadService.findUniversidad(NON_EXISTENT_UNI);

    }
    
    
    
    
}
