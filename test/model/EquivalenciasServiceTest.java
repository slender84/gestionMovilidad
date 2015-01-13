
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
    public void testBuscarEquivalenciaEnContratoEliminado() throws InstanceNotFoundException{
        
        Contrato c=new Contrato();
        Equivalencia e=new Equivalencia();
        c.getEquivalencias().add(e);
        equivalenciaService.crearContrato(c);
        equivalenciaService.eliminarContrato(c);
        Contrato aux=equivalenciaService.buscarContrato(c.getIdContrato());
        Iterator i=aux.getEquivalencias().iterator();
        
        
    }
    @Test
    public void eliminarEquivalenciaCompartida()throws InstanceNotFoundException{
        
        Contrato c1=new Contrato();
        Contrato c2=new Contrato();
        Equivalencia e=new Equivalencia();
        equivalenciaService.crearEquivalencia(e);
        System.out.println("equivalencia id es "+e.getIdequivalencia());
        c1.getEquivalencias().add(e);
        c2.getEquivalencias().add(e);
        equivalenciaService.crearContrato(c1);
        equivalenciaService.crearContrato(c2);
        System.out.println("c1 id es "+c1.getIdContrato());
        System.out.println("c2 id es "+c2.getIdContrato());

       Contrato aux=equivalenciaService.buscarContrato(c1.getIdContrato());
        System.out.println("contrato aux es "+aux.getIdContrato());
       Iterator i=aux.getEquivalencias().iterator();
       Equivalencia aux2=(Equivalencia)i.next();
        System.out.println("equivalenciaAux id "+aux2.getIdequivalencia());
       equivalenciaService.eliminarEquivalencia(e);
       equivalenciaService.eliminarContrato(aux);
       System.out.println("equivalenciaAux id "+aux2.getIdequivalencia());
       
       
       
       
       
       Contrato auxc2=equivalenciaService.buscarContrato(c2.getIdContrato());
        i=auxc2.getEquivalencias().iterator();
       Equivalencia eaux=(Equivalencia)i.next();
       System.out.println("equivalenciaAux2 id "+eaux.getIdequivalencia());
       
        
    }
    
    
   
    
    
    
    
    
    
    
    
    
}
