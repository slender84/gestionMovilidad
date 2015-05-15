
package model.dao;


import entities.Configuracion;
import entities.Contrato;
import entities.Movilidad;
import java.io.Serializable;


import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

@Repository("contratoDao")
public class ContratoDaoImpl extends GenericDaoHibernate<Contrato, Integer> implements ContratoDao,Serializable{
    
    @Override
    public List<Contrato> listarContratos(Movilidad m){
        
        return getSession().createQuery("select c from Contrato c where c.movilidad=:movilidad order by c.fecha desc").setParameter("movilidad", m).list();
    
}
    
    
    @Override
    public List<Contrato> listarContratosPendientes(){
        
        return getSession().createCriteria(Contrato.class)
                .add(Restrictions.like("estado","pendiente")).list();
               
    }
    
    
    @Override
    public List<Contrato> listarTodosContratos(){
        
        return getSession().createQuery("select c from Contrato c order by c.fecha desc").list();
        
    }
    
    @Override
     public List<Contrato> listarContratosPorFiltro(Map<String,String> listaFiltros){
         
         if(listaFiltros.containsKey("curso"))
            
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", listaFiltros.get("curso"));
        if(listaFiltros.containsKey("estado")){
            
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", listaFiltros.get("estado"));
        }
        if(listaFiltros.containsKey("pais")){
           
            if(listaFiltros.containsKey("universidad")==false){
                
                return getSession().createQuery("select c from Contrato c where c.movilidad.universidad.pais.nombre=:pais order by c.fecha desc").setParameter("pais", listaFiltros.get("pais")).list();
            }else{
                
                getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", listaFiltros.get("universidad"));
                
                
            }
                
            
        }
        
        return listarTodosContratos();
        
    }
         
     @Override    
     public List<Contrato> listaLazyContrato(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf){
                
         String login=null;
         String universidad=null;
         String curso=null;
         int filtros=0;
         
         String queryCurso=null;
         String queryLogin=null;
         String queryUniversidad=null;
         
         
         
         
         String orden=null;
        List<Contrato> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            case "estado":campo="estado";
                break;
            case "fecha":campo="fecha";
            break;
            
            case "movilidad.universidad.nombre":campo="movilidad.universidad";
                break;
            case "movilidad.cursoacademico.cursoAcademico":campo="movilidad.cursoacademico";
                break;
            case "movilidad.universidad.pais.nombre":campo="movilidad.universidad.pais.nombre";
                break;
            case "movilidad.usuario.login":campo="movilidad.usuario.login";
                break;
            case "idContrato":campo="idContrato";
                break;
            
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        
        
        
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
            filtros++;
             queryCurso="(select m from Movilidad m where m.cursoacademico.cursoAcademico like :curso)";
            curso="%"+filters.get("movilidad.cursoacademico.cursoAcademico").toString()+"%";
             
            //getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        
         if(filters.containsKey("movilidad.usuario.login")){
             filtros++;
              login="%"+filters.get("movilidad.usuario.login").toString()+"%";
              queryLogin="(select m from Movilidad m where m.usuario.login like :login)";
             //l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like :login)").setParameter("login", "%" + login + "%").list();//funciona bien en casa, poniendo "p" trae pedro.olarte
             //return l;
         }
        
        if(filters.containsKey("movilidad.universidad.nombre")){
            filtros++;
         universidad="%"+filters.get("movilidad.universidad.nombre").toString()+"%";
         queryUniversidad="(select m from Movilidad m where m.universidad.nombre like :universidad)";
        //getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         //l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.universidad like CONCAT('%', :uni, '%'))").setParameter("uni", filters.get("movilidad.universidad.nombre")).list();
                
                 
                  }
        
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from Contrato c ").setFirstResult(first).setMaxResults(pageSize).list();
            return l;
        }
        
        
        else{
            
            
            if(filtros==3){
                
                
               l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryLogin+" and c.movilidad in"+queryUniversidad+" order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("curso", curso).setParameter("login", login).setParameter("universidad", universidad).list();
               return l; 
                
                
            }
            
            if(filtros==2){
                
                if(curso==null){
                    
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryLogin+" and c.movilidad in"+queryUniversidad+" order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("login", login).setParameter("universidad", universidad).list();
                    return l; 
                    
                }
                
                if(login==null){
                    
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryUniversidad+" order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("curso", curso).setParameter("universidad", universidad).list();
                    return l; 
                }
                
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryLogin+" order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).setParameter("curso", curso).setParameter("login", login).list();
                    return l; 
                
            }
            
            if(filtros==1){
            
            
            
            if(curso!=null){
              
               l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryCurso+" order by c."+campo+"  "+orden).setParameter("curso", curso).setFirstResult(first).setMaxResults(pageSize).list();
               return l;
            }else if(login!=null){
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryLogin+" order by c."+campo+"  "+orden).setParameter("login", login).setFirstResult(first).setMaxResults(pageSize).list();
               return l;
            }else{
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryUniversidad+" order by c."+campo+"  "+orden).setParameter("universidad", universidad).setFirstResult(first).setMaxResults(pageSize).list();
               return l;
            }
                 
                
        }
            
             l=getSession().createQuery("select c from Contrato c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
        
        return l;
            
            
        }
        
     }
       
        
    
        
        
        
          
         
     
    
     
     
     
     @Override
     public int countContrato(Map<String,Object> filters){
         
          List<Contrato> l=null; 
          String login=null;
         String universidad=null;
         String curso=null;
         int filtros=0;
         
         String queryCurso=null;
         String queryLogin=null;
         String queryUniversidad=null;
         
         if(filters.containsKey("estado")){
            
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
         
         if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
            filtros++;
             queryCurso="(select m from Movilidad m where m.cursoacademico.cursoAcademico like :curso)";
            curso="%"+filters.get("movilidad.cursoacademico.cursoAcademico").toString()+"%";
             
            //getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        
         if(filters.containsKey("movilidad.usuario.login")){
             filtros++;
              login="%"+filters.get("movilidad.usuario.login").toString()+"%";
              queryLogin="(select m from Movilidad m where m.usuario.login like :login)";
             //l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like :login)").setParameter("login", "%" + login + "%").list();//funciona bien en casa, poniendo "p" trae pedro.olarte
             //return l;
         }
        
        if(filters.containsKey("movilidad.universidad.nombre")){
            filtros++;
         universidad="%"+filters.get("movilidad.universidad.nombre").toString()+"%";
         queryUniversidad="(select m from Movilidad m where m.universidad.nombre like :universidad)";
        //getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         //l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.universidad like CONCAT('%', :uni, '%'))").setParameter("uni", filters.get("movilidad.universidad.nombre")).list();
           
                  }
        
            
            if(filtros==3){
                
                
               l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryLogin+" and c.movilidad in"+queryUniversidad).setParameter("curso", curso).setParameter("login", login).setParameter("universidad", universidad).list();
               return l.size(); 
                
                
            }
            
            if(filtros==2){
                
                if(curso==null){
                    
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryLogin+" and c.movilidad in"+queryUniversidad).setParameter("login", login).setParameter("universidad", universidad).list();
                    return l.size(); 
                    
                }
                
                if(login==null){
                    
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryUniversidad).setParameter("curso", curso).setParameter("universidad", universidad).list();
                    return l.size(); 
                }
                
                    l=getSession().createQuery("select c from Contrato c where c.movilidad in"+queryCurso+ " and c.movilidad in"+queryLogin).setParameter("curso", curso).setParameter("login", login).list();
                    return l.size(); 
                
            }
            
             if(filtros==1){
            
            if(curso!=null){
              
               l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryCurso).setParameter("curso", curso).list();
               return l.size();
            }else if(login!=null){
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryLogin).setParameter("login", login).list();
               return l.size();
            }else{
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in "+queryUniversidad).setParameter("universidad", universidad).list();
               return l.size();
            }
                 
                
        }
        
        
        
        l=getSession().createQuery("select c from Contrato c").list();
        
        return l.size();
        }
         
        
        
         
     
     
     @Override    
     public List<Contrato> listaLazyContratoPrueba(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf){
                
                String mensaje="";
         
         String orden=null;
        List<Contrato> l=null;
        
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            case "estado":campo="estado";
                break;
            case "fecha":campo="fecha";
            break;
            
            case "movilidad.universidad.nombre":campo="movilidad.universidad";
                break;
            case "movilidad.cursoacademico.cursoAcademico":campo="movilidad.cursoacademico";
                break;
            case "movilidad.universidad.pais.nombre":campo="movilidad.universidad.pais.nombre";
                break;
            case "movilidad.usuario.login":campo="movilidad.usuario.login";
                break;
            case "idContrato":campo="idContrato";
                break;
            
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
            
        if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        
        if(filters.containsKey("movilidad.usuario.login")){
            
            mensaje="first es "+first+"\n pagesize es "+pageSize+"\n"+mensaje+"hay movilidad.usuario.login \n";
        }
            
            //getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
           /* try{
                    
                    email.enviarEmailYo(correoConf, "filters",filters.get("movilidad.usuario.login").toString());
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
        }else{
            
             try{
                    
                    email.enviarEmailYo(correoConf, "filters","no");
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
        }*/
        
        //if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            //getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        //}
        
       // if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            //getSession().enableFilter("paisContrato").setParameter("paisContratoParam", filters.get("movilidad.universidad.pais.nombre"));
        
        //}
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        //getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.universidad like CONCAT('%', :uni, '%'))").setParameter("uni", filters.get("movilidad.universidad.nombre")).list();
                return l;
                 
                  }
        
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from Contrato c ").setFirstResult(first).setMaxResults(pageSize).list();
            return l;
        }
        
        
        else{
            
             if(filters.containsKey("movilidad.usuario.login")){
                mensaje=mensaje+"en movilidad.usuario.login hay: "+filters.get("movilidad.usuario.login")+"\n";
                 //String lo="u3";
                 //String query="'%"+filters.get("movilidad.usuario.login")+"%'";
                 //l=getSession().createQuery("select c from Contrato c where c.movilidad.usuario.login like '%u%' order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
                 l=getSession().createQuery("select c from Contrato c").list();
                 mensaje=mensaje+"tamaño lista tras movilidad.usuario.login a pelo es "+l.size()+"\n";
                 //l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like 'u2')").list();//funciona bien en casa
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like 'paulo.bgarcia')").list();
                 mensaje=mensaje+"busco paulo.bgarcia: "+l.size();
                 //System.out.println("lo que trae al buscar 'u3' es "+l.get(0).getFecha());
                 String nombre=filters.get("movilidad.usuario.login").toString();
                 l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like :login)").setParameter("login", "%" + nombre + "%").list();//funciona bien en casa, poniendo "p" trae pedro.olarte
                 mensaje=mensaje+"\n añadiendo like login como parametro "+l.size();
                 
                 System.out.println("el mensaje total es: "+mensaje);
                 try{
                    
                   // email.enviarEmailYo(correoConf, "mensaje total",mensaje);
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
                 
             }else{
                 
                 l=getSession().createQuery("select c from Contrato c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
             }
               return l;
        }
        
       
        }
    
        
        
        
          
         
     
    
     
     
     
     @Override
     public int countContratoPrueba(Map<String,Object> filters){
         String mensaje="";
         List<Contrato> l=null;
         
         /*if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        */
        if(filters.containsKey("movilidad.usuario.login")){
          
          String nombre=filters.get("movilidad.usuario.login").toString();
          l=getSession().createQuery("select c from Contrato c where c.movilidad in(select m from Movilidad m where m.usuario.login like :login)").setParameter("login", "%" + nombre + "%").list();//funciona bien en casa, poniendo "p" trae pedro.olarte

            //getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
            
        }
        
        
        else{
            
            l=getSession().createQuery("select c from Contrato c").list();
            
        }
        /*
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            getSession().enableFilter("paisContrato").setParameter("paisContratoParam", filters.get("movilidad.universidad.pais.nombre"));
        
        }
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         
                  }
        
        
        */
        //List<Contrato> lista=getSession().createQuery("select c from Contrato c").list();
        
        if(filters.containsKey("movilidad.usuario.login")){
         mensaje=" hay "+l.size();
         System.out.println("count en dao :"+mensaje);
         
        }
        return l.size();
         //return 10;
                 
         
     }
     
     
     @Override    
     public List<Contrato> listaLazyContratoFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf){
     String orden=null;
        List<Contrato> l=null;
        String mensaje=null;
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            case "estado":campo="estado";
                break;
            case "fecha":campo="fecha";
            break;
            
            case "movilidad.universidad.nombre":campo="movilidad.universidad";
                break;
            case "movilidad.cursoacademico.cursoAcademico":campo="movilidad.cursoacademico";
                break;
            case "movilidad.universidad.pais.nombre":campo="movilidad.universidad.pais.nombre";
                break;
            case "movilidad.usuario.login":campo="movilidad.usuario.login";
                break;
            case "idContrato":campo="idContrato";
                break;
            
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
        
        
            
            if(sortField==null){
            
             l=getSession().createQuery("select c from Contrato c ").setFirstResult(first).setMaxResults(pageSize).list();
            return l;
        }
        
        
        else{
            
             if(filters.containsKey("movilidad.usuario.login")){
               
                getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
                mensaje=mensaje+"en movilidad.usuario.login hay: "+filters.get("movilidad.usuario.login")+"\n";
             }
  
                
                 l=getSession().createQuery("select c from Contrato c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
                 mensaje=mensaje+"tamaño lista tras movilidad.usuario.login con filter letra p es "+l.size()+"\n"; //la primera vez no hace el filter
                 
                 if(filters.containsKey("movilidad.usuario.login")){
                     System.out.println("el mensaje total es: "+mensaje);
                    try{
                    
                    //email.enviarEmailYo(correoConf, "mensaje total",mensaje);
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
             }
            }
               return l;
        }
        
        
     
     

     
     
     @Override
     public int countContratoFilter(Map<String,Object> filters){
         String mensaje=null;
         
         if(filters.containsKey("movilidad.usuario.login")){
                getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
               
             }
         
         List<Contrato> l=getSession().createQuery("select c from Contrato c").list();
         //System.out.println("el tamaño de count filter en dao es "+l.size());
         return l.size();
     }
     
     
     @Override    
     public List<Contrato> listaLazyContratoFilter2(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,Configuracion correoConf){
     String orden=null;
        List<Contrato> l=null;
        String mensaje=null;
        
        String campo=null;
        
        
        if(sortField!=null){
          
        switch(sortField){
            
            case "estado":campo="estado";
                break;
            case "fecha":campo="fecha";
            break;
            
            case "movilidad.universidad.nombre":campo="movilidad.universidad";
                break;
            case "movilidad.cursoacademico.cursoAcademico":campo="movilidad.cursoacademico";
                break;
            case "movilidad.universidad.pais.nombre":campo="movilidad.universidad.pais.nombre";
                break;
            case "movilidad.usuario.login":campo="movilidad.usuario.login";
                break;
            case "idContrato":campo="idContrato";
                break;
            
            
        }
         
            
            if(sortOrder.toString().equalsIgnoreCase("ascending")){
                orden="asc";    
            }else{
                orden="desc";
                
            }
        }
        
        
        if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        
        
         try{
                    
                    //email.enviarEmailYo(correoConf, "en lazy filter 2","despues de estado");
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
        
        if(filters.containsKey("movilidad.usuario.login")){
            
            getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
        }
        
        
         try{
                    
                   // email.enviarEmailYo(correoConf, "lazy filter2 después de usuario.login","despues de usuario.login");
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
        
        
        
        
        
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            getSession().enableFilter("paisContrato").setParameter("paisContratoParam", filters.get("movilidad.universidad.pais.nombre"));
        
        }
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         
                  }
        
        
               
        
        if(sortField==null){
            
             l=getSession().createQuery("select c from Contrato c ").setFirstResult(first).setMaxResults(pageSize).list();
            
        }else{
            
             l=getSession().createQuery("select c from Contrato c order by c."+campo+"  "+orden).setFirstResult(first).setMaxResults(pageSize).list();
             mensaje=mensaje+"tamano  "+l.size();
             System.out.println(mensaje);
               try{
                    
                    //email.enviarEmailYo(correoConf, "mensaje total",mensaje);
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }    
             
             
             
             
        }
        
        return l;
        
    }
        
        
        
            
            
                  
            
        
        
     
     

     
     
     @Override
     public int countContratoFilter2(Map<String,Object> filters){
         String mensaje=null;
         
         if(filters.containsKey("estado")){
                 
            getSession().enableFilter("estadoContrato").setParameter("estadoContratoParam", filters.get("estado"));
        
        }
        
        if(filters.containsKey("movilidad.usuario.login")){
            
            getSession().enableFilter("usuarioContrato").setParameter("usuarioContratoParam", filters.get("movilidad.usuario.login"));
        }
        
        if(filters.containsKey("movilidad.cursoacademico.cursoAcademico")){
             
            getSession().enableFilter("cursoAcademicoContrato").setParameter("cursoAcademicoContratoParam", filters.get("movilidad.cursoacademico.cursoAcademico"));
        }
        
        if(filters.containsKey("movilidad.universidad.pais.nombre")){
            
            getSession().enableFilter("paisContrato").setParameter("paisContratoParam", filters.get("movilidad.universidad.pais.nombre"));
        
        }
        if(filters.containsKey("movilidad.universidad.nombre")){
            
        getSession().enableFilter("universidadContrato").setParameter("universidadContratoParam", filters.get("movilidad.universidad.nombre"));
         
                  }
        
        
        
        List<Contrato> lista=getSession().createQuery("select c from Contrato c").list();
        return lista.size();
     }
     
     
         
     }
    
    
    
    
    
