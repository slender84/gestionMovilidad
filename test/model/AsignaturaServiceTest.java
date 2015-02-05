
package model;

import entities.Asignatura;
import entities.AsignaturaId;
import entities.Pais;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import static junit.framework.Assert.assertEquals;
import static model.UniversidadServiceTest.SPRING_CONFIG_FILE;
import model.services.AsignaturaService;
import model.services.UniversidadService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AsignaturaServiceTest {
    public static final String SPRING_CONFIG_FILE =
        "classpath:/applicationContext.xml";
   private final long NON_EXISTENT_UNI_ID = -1;
    private final String NON_EXISTENT_PAIS = "NonExistentPais";
    private final long NON_EXISTENT_PAIS_ID2 = -2;
    
    private final String NON_EXISTENT_UNI="NonExistentUni";
    
    
   @Autowired 
   UniversidadService universidadService;
   
   @Autowired 
   AsignaturaService asignaturaService;

    public AsignaturaService getAsignaturaService() {
        return asignaturaService;
    }

    public void setAsignaturaService(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }
   
   
   

    public UniversidadService getUniversidadService() {
        return universidadService;
    }

    public void setUniversidadService(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }
   
   @Test(expected = InstanceNotFoundException.class)
    public void testCrearAsignaturaConUniversidad()throws InstanceNotFoundException{
        
        
        
        Universidad u=universidadService.buscarUniversidad(NON_EXISTENT_UNI);
        Asignatura a=new Asignatura(new AsignaturaId("cod1","uni1"),u);
        universidadService.insertarUniversidad(u);
        asignaturaService.crearAsignatura(a);
        asignaturaService.eliminarAsignatura(a);
        Asignatura b=u.getAsignaturas().iterator().next();
        assertEquals(b, a);
        
        
    }
    
    
   /*  @Test(expected = InstanceNotFoundException.class)
    public void testBuscarPaisNoExistente() throws InstanceNotFoundException {

        universidadService.buscarPais(NON_EXISTENT_PAIS);

    }
    
   */
   
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
        
        
        @Test
        public void crearYEditarAsignaturaTest() throws InstanceNotFoundException{
           
        Universidad uni1=new Universidad("abc", "cba"); 
        AsignaturaId asId=new AsignaturaId("asf","abc");
        Asignatura a=new Asignatura(asId,uni1);
        uni1.getAsignaturas().add(a);
        universidadService.insertarUniversidad(uni1);

        asignaturaService.crearAsignatura(a);
        a.setCreditos(new Float(12));
        asignaturaService.actualizarAsignatura(a);
        Universidad uni2=universidadService.buscarUniversidad("abc");
        //Assert.assertTrue(uni2.getAsignaturas().size()==1);

            Asignatura a2=uni2.getAsignaturas().iterator().next();
        Assert.assertTrue(a2.getCreditos().equals(a.getCreditos()));
        
        
    }
        
        
       
       
    }
    
    

