

package model.services;


import entities.Asignatura;
import entities.AsignaturaId;
import entities.ComentarioAsignatura;
import entities.Idioma;
import entities.MiembroGrupoAsignaturaB;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.List;
import model.dao.AsignaturaDao;
import model.dao.IdiomaDao;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 @Transactional
 @Service("asignaturaService")
public class AsignaturaServiceImpl implements AsignaturaService,Serializable{
    
   
    @Autowired
    private AsignaturaDao asignaturaDao;
    @Autowired
    private IdiomaDao idiomaDao;

    public AsignaturaDao getAsignaturaDao() {
        return asignaturaDao;
    }
    public void setAsignaturaDao(AsignaturaDao asignaturaDao) {
        this.asignaturaDao = asignaturaDao;
    }

    public IdiomaDao getIdiomaDao() {
        return idiomaDao;
    }

    public void setIdiomaDao(IdiomaDao idiomaDao) {
        this.idiomaDao = idiomaDao;
    }
    
    
    
    
    @Override
    public void crearAsignatura(Asignatura a){
        
        asignaturaDao.insertar(a);
    }
    
    @Override
    public List<Asignatura> listarAsignaturas(){
        return asignaturaDao.listar();
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listarAsignaturasPorUniversidad(String codUniversidad){
        
        return asignaturaDao.listarAsignaturasPorUniversidad(codUniversidad);
                
                
    }
    @Override
    public void eliminarAsignatura(Asignatura a) throws InstanceNotFoundException{
        if(asignaturaDao.existe(a.getId())==false)
                throw new InstanceNotFoundException();
        
        asignaturaDao.eliminar(a);
        
    }
    
    
    @Override
    public void actualizarAsignatura(Asignatura a)throws InstanceNotFoundException{
        if(asignaturaDao.existe(a.getId())==false)
                throw new InstanceNotFoundException();
        asignaturaDao.editar(a);
    }
    
    @Override
   public void crearIdioma(Idioma i){
       
       idiomaDao.insertar(i);
       
       
   }
   @Override
    public void eliminarIdioma(Idioma i){
        
        idiomaDao.eliminar(i);
        
    }
    
    @Override
    public List<Idioma>listarIdiomas(){
        
    return idiomaDao.listar();
        
        
    }
    @Override
    public Asignatura buscarAsignatura(AsignaturaId id)throws InstanceNotFoundException{
        
        if(asignaturaDao.existe(id)==false)
            throw new InstanceNotFoundException();
        Asignatura a= asignaturaDao.buscar(id);
        Hibernate.initialize(a.getComentarioAsignaturas());
        return a;
    }
    
    @Override
    public void insertarComentario(ComentarioAsignatura c){
        
        asignaturaDao.insertarComentario(c);
        
    }
    @Override
    public void crearMiembro(MiembroGrupoAsignaturaB m){
        
        asignaturaDao.crearMiembro(m);
        
    }
    
}
