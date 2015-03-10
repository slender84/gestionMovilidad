
package model.utils;



import entities.Configuracion;
import entities.Estado;
import entities.EstadoMovilidad;
import java.io.Serializable;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("utilidadService")
public class UtilidadServiceImpl implements UtilidadService,Serializable{

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    
    
    
    @Override
    public void crearEstado(Estado e){
        sessionFactory.getCurrentSession().save(e);
    }
    @Override
    public List<Estado> listaEstados(){
        return sessionFactory.getCurrentSession().createQuery("select e from Estado e").list();
    }
    @Override
    public void eliminaEstado(Estado e){
        sessionFactory.getCurrentSession().delete(e);
                
    }
    
    @Override
    public void crearEstadoMovilidad(EstadoMovilidad e){
        
        sessionFactory.getCurrentSession().save(e);
        
    }
    
    @Override
    public List<EstadoMovilidad> listaEstadosMovilidad(){
         return sessionFactory.getCurrentSession().createQuery("select e from EstadoMovilidad e").list();
        
        
    }
    
    
    @Override
    public void eliminaEstadoMovilidad(EstadoMovilidad e){
        sessionFactory.getCurrentSession().delete(e);
    }
    
    
    
    @Override
    public Configuracion getCorreoConf(){
        
        
        List<Configuracion> l= sessionFactory.getCurrentSession().createQuery("select c from Configuracion c").list();
        if(l.isEmpty())
            return new Configuracion();
        return l.get(0);
    }
    
    @Override
    public void setCorreoConf(Configuracion correoConf){
        
        sessionFactory.getCurrentSession().saveOrUpdate(correoConf);
    }
    
    
    
}