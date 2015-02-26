
package model.dao;

import exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;


public class GenericDaoHibernate<T,PK extends Serializable> implements GenericDao<T,PK>{
    
    private SessionFactory sessionFactory;
    private Class<T> entityClass;

    
    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

    
    public GenericDaoHibernate() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

    @Override
    public void insertar(T entity){
        
        getSession().save(entity);
    }
    
    @Override
    public void editar(T entity){
        
        getSession().update(entity);
        
    }
    
    
    @Override
    public boolean existe(PK id) {
		return getSession().createCriteria(entityClass).add(
				Restrictions.idEq(id)).setProjection(Projections.id())
				.uniqueResult() != null;
	}
    @Override
    @SuppressWarnings("unchecked")
	public T buscar(PK id) throws InstanceNotFoundException {
		T entity = (T) getSession().get(entityClass, id);
		if (entity == null) {
			throw new InstanceNotFoundException();
		}
		return entity;
	}
        
        @Override
        public List<T> listar(){
            
            return getSession().createCriteria(entityClass).list();
            
        }
        
        @Override
        public void eliminar(T entity){
            
            getSession().delete(entity);
        }

        
        
        
        
        
        
    
}
