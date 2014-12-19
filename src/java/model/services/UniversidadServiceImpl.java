package model.services;


import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import exceptions.PaisException;
import exceptions.UniversidadException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.dao.UniversidadDao;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("universidadService")
@Transactional
public class UniversidadServiceImpl implements UniversidadService,Serializable{
    
    @Autowired
    private UniversidadDao universidadDao;

    public UniversidadDao getCarreraDao() {
        return universidadDao;
    }

    public void setCarreraDao(UniversidadDao carreraDao) {
        this.universidadDao = carreraDao;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Pais> listaPaises(){
        
        return universidadDao.listaPaises();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad> listaUniversidades(){
        
        return universidadDao.listaUniversidades();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pais findPais(String pais) throws PaisException{
        Pais p=universidadDao.findPais(pais);
        if(p==null){
            throw new PaisException();
        }
        return p;
    }
    
    
    @Override
    public void insertarPais(String pais){
        
        
        Pais p=new Pais(pais);
        universidadDao.insertarPais(p);
        
            
        
    }
    @Override
    public void deletePais(Pais p){
        
        universidadDao.deletePais(p);
    }
    
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad> listar(){
        
        return(universidadDao.listarUniversidades());
    }
    
    @Override
    public void delete(Universidad u){
        
        universidadDao.delete(u);
        
    }
    
    
        
    
    @Override
    public void insertarUniversidad(Universidad u){
        
     universidadDao.insertarCarrera(u);
        
        
    }
    @Override
    public void actualizar(Universidad u){
        
        universidadDao.actualizar(u);
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad>listarPorUniversidad(String universidad){
        
        return universidadDao.listarPorUniversidad(universidad);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad>listarPorPais(String pais){
        
        return universidadDao.listarPorPais(pais);
        
    }
    @Override
    @Transactional(readOnly = true)
    public List<String>listarPorPaisStr(String pais){
        
       
        
        return universidadDao.listarPorPaisStr(pais);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String>listarPorUniversidadStr(String universidad){
        
        return universidadDao.listarPorUniversidadStr(universidad);
        
        
    }
    @Override
   public void crearCursoAcademico(Cursoacademico cursoAcademico){
       
       
       universidadDao.crearCursoAcademico(cursoAcademico);
       
   }
   @Override
   public void eliminarCursoAcademico(String cursoAcademico){
       
       Cursoacademico c=new Cursoacademico(cursoAcademico);
       universidadDao.eliminarCursoAcademico(c);
       
       
   }
    
   @Override
   @Transactional(readOnly = true)
   public List<Cursoacademico> listaCursosAcademicos(){
       
       return universidadDao.listaCursosAcademicos();
   }
   
   @Override
   public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin){
    Calendar cal1=Calendar.getInstance();
    Calendar cal2=Calendar.getInstance();
    cal1.setTime(fechaInicio);
    cal2.setTime(fechaFin);
    Cursoacademico ca=new Cursoacademico();
                    
                    if(cal1.get(2)>8){
                       
                        ca.setCursoAcademico(cal1.get(1)+"/"+(cal1.get(1)+1));
                    }else{
                        
                        ca.setCursoAcademico(cal1.get(1)-1+"/"+(cal1.get(1)));
                    }
                    
                    for(Cursoacademico c:listaCursosAcademicos()){
                        if (c.getCursoAcademico().equals(ca.getCursoAcademico())){
                            return ca;
                        }
                        
                    }
                    
                    crearCursoAcademico(ca);
                    return ca;
    
    
}
   
   
   @Override
   @Transactional(readOnly = true)
   public Universidad findUniversidad(String universidad) throws UniversidadException{
       
       Universidad u=universidadDao.findUniversidad(universidad);
       if(u==null){
           throw new UniversidadException();
       }
       return u;
   }
    
}

