package model.services;


import entities.Cronica;
import entities.Cursoacademico;
import entities.Pais;
import entities.Universidad;
import entities.Usuario;
import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.dao.CronicaDao;
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
    
    @Autowired
    private CronicaDao cronicaDao;

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

    public CronicaDao getCronicaDao() {
        return cronicaDao;
    }

    public void setCronicaDao(CronicaDao cronicaDao) {
        this.cronicaDao = cronicaDao;
    }
    
    
    

    public UniversidadDao getUniversidadDao() {
        return universidadDao;
    }

    public void setUniversidadDao(UniversidadDao universidadDao) {
        this.universidadDao = universidadDao;
    }

    
    @Override
    @Transactional(readOnly = true)
    public List<Pais> listarPaises(){
        
        return paisDao.listar();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad> listarUniversidades(){
        
        return universidadDao.listar();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pais buscarPais(String pais) throws InstanceNotFoundException{
        Pais p=paisDao.buscar(pais);
        return p;
    }
    
    
    @Override
    public void insertarPais(Pais pais){
        
        
        
        paisDao.insertar(pais);
        
            
        
    }
    @Override
    public void eliminarPais(Pais p){
        
        paisDao.eliminar(p);
    }
    
    
    
   
    
    @Override
    public void eliminarUniversidad(Universidad u)throws InstanceNotFoundException{
        //u=universidadDao.find(u.getNombre());
         if(universidadDao.existe(u.getNombre())==false)
                throw new InstanceNotFoundException();
        universidadDao.eliminar(u);
        
    }
    
    
        
    
    @Override
    public void insertarUniversidad(Universidad u){
        
     universidadDao.insertar(u);
        
        
    }
    @Override
    public void actualizarUniversidad(Universidad u) throws InstanceNotFoundException{
        //u=universidadDao.find(u.getNombre());
        if(universidadDao.existe(u.getNombre())==false)
                throw new InstanceNotFoundException();
        universidadDao.editar(u);
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Universidad>listarPorPais(String pais){
        
        return universidadDao.listarPorPais(pais);
        
    }
    
    
    @Override
   public void crearCursoAcademico(Cursoacademico cursoAcademico){
       
       
       cursoacademicoDao.insertar(cursoAcademico);
       
   }
   @Override
   public void eliminarCursoAcademico(Cursoacademico cursoAcademico){
       
       
       cursoacademicoDao.eliminar(cursoAcademico);
       
       
   }
    
   @Override
   @Transactional(readOnly = true)
   public List<Cursoacademico> listarCursosAcademicos(){
       
       return cursoacademicoDao.listar();
   }
   
   @Override
   public Cursoacademico buscarCursoAcademico(Date fechaInicio,Date fechaFin){
    Calendar cal1=Calendar.getInstance();
    Calendar cal2=Calendar.getInstance();
    cal1.setTime(fechaInicio);
    cal2.setTime(fechaFin);
    Cursoacademico ca=new Cursoacademico();
                    
                    if(cal1.get(1)==cal2.get(1)){
                        if(cal1.get(2)>=8){
                        System.out.println(cal1.get(1)+"/"+cal1.get(2));
                        ca.setCursoAcademico(cal1.get(1)+"/"+(cal1.get(1)+1));
                     
                    }else{
                            ca.setCursoAcademico((cal1.get(1)-1)+"/"+(cal1.get(1)));
    
                            } 
                        
                    }else{
                        
                        ca.setCursoAcademico(cal1.get(1)+"/"+(cal1.get(1)+1));
                        
                    }
                    
                    for(Cursoacademico c:listarCursosAcademicos()){
                        if (c.getCursoAcademico().equals(ca.getCursoAcademico())){
                            return ca;
                        } 
                        
                    }
                    
                    crearCursoAcademico(ca);
                    return ca;
    
    
   }
   
   
   @Override
   @Transactional(readOnly = true)
   public Universidad buscarUniversidad(String universidad) throws InstanceNotFoundException{
       
       Universidad u=universidadDao.buscar(universidad);
       
       return u;
   }
   
   
   
   @Override
   public List<Cronica> listarCronicasPorUniversidad(String universidad)throws InstanceNotFoundException{
       
      if(universidadDao.existe(universidad)==false){
          throw new InstanceNotFoundException();
      }
       
       return cronicaDao.listarCronicasPorUniversidad(universidad);
       
       
       
   }
   
   
   @Override
   public void enviarCronica(Cronica c){
       
       
       
       cronicaDao.insertar(c);
       
   }
   
   @Override
   @Transactional(readOnly = true)
   public List<Cronica> listarMisCronicas(Usuario u){
       
       
       return cronicaDao.listarMisCronicas(u);
       
   }
   
   @Override
   public void editarCronica(Cronica c)throws InstanceNotFoundException{
       
        if(cronicaDao.existe(c.getIdcronica())==false){
            throw new InstanceNotFoundException();
        }
        
        cronicaDao.editar(c);
       
       
   }
   
   @Override
   public void eliminarCronica(Cronica c) throws InstanceNotFoundException{
       
       if(cronicaDao.existe(c.getIdcronica())==false){
           throw new InstanceNotFoundException();
       }
       cronicaDao.eliminar(c);
       
   }
   
   @Override
   @Transactional(readOnly = true)
       public List<Cronica> listaCronicas(){
           
           return cronicaDao.listar();
           
       }

       @Override
       @Transactional(readOnly = true)
       public List<Cronica> listarCronicasPublicas(String universidad) throws InstanceNotFoundException{
           
           if(universidadDao.existe(universidad)==false){
          throw new InstanceNotFoundException();
      }
       
           return cronicaDao.listarCronicasPublicas(universidad);
           
       }
       
  
    
}

