
package model;

import entities.Contrato;
import entities.Equivalencia;
import exceptions.InstanceNotFoundException;
import java.util.Iterator;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static model.EquivalenciasServiceTest.SPRING_CONFIG_FILE;
import model.services.EquivalenciaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@ContextConfiguration(locations = { SPRING_CONFIG_FILE })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EquivalenciasServiceTest {
    public static final String SPRING_CONFIG_FILE =
        "classpath:/applicationContext.xml";
    
    private final Integer contrato_id=1;
    private final Integer contrato_non_existent_id=-1;
    
    
    @Autowired
    private EquivalenciaService equivalenciaService;

    public EquivalenciaService getEquivalenciaService() {
        return equivalenciaService;
    }

    public void setEquivalenciaService(EquivalenciaService equivalenciaService) {
        this.equivalenciaService = equivalenciaService;
    }
    
    
    
    
    @Test
    public void testCrearYBuscarContrato()throws InstanceNotFoundException{
        
        Contrato c=new Contrato();
        equivalenciaService.crearContrato(c);
        Contrato c2=equivalenciaService.buscarContrato(c.getIdContrato());
        
        assertEquals(c, c2);
        
        
    }
    
    @Test(expected = InstanceNotFoundException.class)
    public void testBuscarContratoNoExistente() throws InstanceNotFoundException{
        
        equivalenciaService.buscarContrato(contrato_non_existent_id);
        
    }
    
    @Test
    public void testCrearEquivalenciaEnContratoExistente()throws InstanceNotFoundException{
        
        Contrato c=new Contrato();
        Equivalencia e=new Equivalencia();
        equivalenciaService.crearEquivalencia(e);
        c.getEquivalencias().add(e);
        equivalenciaService.crearContrato(c);
        
        Contrato c2=equivalenciaService.buscarContrato(c.getIdContrato());
        
        Assert.assertTrue(c2.getEquivalencias().size()==1);
        
        
    }
    
    @Test(expected = InstanceNotFoundException.class)
    public void testCrearYEliminarContrato() throws InstanceNotFoundException{
        
        Contrato c=new Contrato();
        Equivalencia e=new Equivalencia();
        c.getEquivalencias().add(e);
        e.getContratos().add(c);
        equivalenciaService.crearContrato(c);
        equivalenciaService.eliminarContrato(c);
        Contrato aux=equivalenciaService.buscarContrato(c.getIdContrato());
        //Iterator i=aux.getEquivalencias().iterator();
        
        
    }
    @Test(expected = RuntimeException.class)
    public void eliminarEquivalenciaCompartida(){
        
        Contrato c1=new Contrato();
        Contrato c2=new Contrato();
        Equivalencia e=new Equivalencia();
        equivalenciaService.crearEquivalencia(e);
        
        c1.getEquivalencias().add(e);
        c2.getEquivalencias().add(e);
        e.getContratos().add(c1);
        e.getContratos().add(c2);
        equivalenciaService.crearContrato(c1);
        equivalenciaService.crearContrato(c2);
        equivalenciaService.eliminarEquivalencia(e);
        e.setContratos(null);
        equivalenciaService.actualizarEquivalencia(e);
        
        
        assertEquals(c1.getEquivalencias().size(), c2.getEquivalencias().size());
       
        
    }
    
    
   
    
    
    
    
    
    
    
    
    
}
