package model.services;


import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.dao.CursoAcademicoDao;
import model.dao.PaisDao;
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
    
    @Autowired
    private CursoAcademicoDao cursoacademicoDao;
    
    @Autowired
    private PaisDao paisDao;

    public CursoAcademicoDao getCursoacademicoDao() {
        return cursoacademicoDao;
    }

    public void setCursoacademicoDao(CursoAcademicoDao cursoacademicoDao) {
        this.cursoacademicoDao = cursoacademicoDao;
    }

    public PaisDao getPaisDao() {
        return paisDao;
    }

    public void setPaisDao(PaisDao paisDao) {
        this.paisDao = paisDao;
    }
    
    
    

    public UniversidadDao getUniversidadDao() {
        return universidadDao;
    }

    public void setUniversidadDao(UniversidadDao universidadDao) {
        this.universidadDao = universidadDao;
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<Pais> listaPaises(){
        
        return paisDao.list();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad> listaUniversidades(){
        
        return universidadDao.list();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pais findPais(String pais) throws InstanceNotFoundException{
        Pais p=paisDao.find(pais);
        return p;
    }
    
    
    @Override
    public void insertarPais(String pais){
        
        
        Pais p=new Pais(pais);
        paisDao.insert(p);
        
            
        
    }
    @Override
    public void deletePais(Pais p){
        
        paisDao.delete(p);
    }
    
    
    
   
    
    @Override
    public void delete(Universidad u)throws InstanceNotFoundException{
        //u=universidadDao.find(u.getNombre());
         if(universidadDao.exists(u.getNombre())==false)
                throw new InstanceNotFoundException();
        universidadDao.delete(u);
        
    }
    
    
        
    
    @Override
    public void insertarUniversidad(Universidad u){
        
     universidadDao.insert(u);
        
        
    }
    @Override
    public void actualizar(Universidad u) throws InstanceNotFoundException{
        //u=universidadDao.find(u.getNombre());
        if(universidadDao.exists(u.getNombre())==false)
                throw new InstanceNotFoundException();
        universidadDao.edit(u);
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad>listarPorPais(String pais){
        
        return universidadDao.listarPorPais(pais);
        
    }
    
    
    @Override
   public void crearCursoAcademico(Cursoacademico cursoAcademico){
       
       
       cursoacademicoDao.insert(cursoAcademico);
       
   }
   @Override
   public void eliminarCursoAcademico(String cursoAcademico){
       
       Cursoacademico c=new Cursoacademico(cursoAcademico);
       cursoacademicoDao.delete(c);
       
       
   }
    
   @Override
   @Transactional(readOnly = true)
   public List<Cursoacademico> listaCursosAcademicos(){
       
       return cursoacademicoDao.list();
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
   public Universidad findUniversidad(String universidad) throws InstanceNotFoundException{
       
       Universidad u=universidadDao.find(universidad);
       
       return u;
   }
    
}

