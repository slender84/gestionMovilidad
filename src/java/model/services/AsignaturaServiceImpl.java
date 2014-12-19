

package model.services;


import entities.Asignatura;
import java.io.Serializable;
import java.util.List;
import model.dao.AsignaturaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 @Transactional
 @Service("asignaturaService")
public class AsignaturaServiceImpl implements AsignaturaService,Serializable{
    
   
    @Autowired
    private AsignaturaDao asignaturaDao;

    public AsignaturaDao getAsignaturaDao() {
        return asignaturaDao;
    }

    public void setAsignaturaDao(AsignaturaDao asignaturaDao) {
        this.asignaturaDao = asignaturaDao;
    }
    
    @Override
    public void crearAsignatura(Asignatura a){
        
        asignaturaDao.crearAsignatura(a);
    }
    
    @Override
    public List<Asignatura> listarAsignaturas(){
        return asignaturaDao.listarAsignaturas();
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad){
        
        return asignaturaDao.listarAsignaturasPorUniversidad(codUniversidad);
                
                
    }
    @Override
    public void eliminaAsignatura(Asignatura a){
        
        asignaturaDao.eliminaAsignatura(a);
        
    }
    
    
    @Override
    public void actualizarAsignatura(Asignatura a){
        
        asignaturaDao.actualizarAsignatura(a);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listarPorCriterio(){
        
        return asignaturaDao.listarPorCriterio();
    }
    
    
}
