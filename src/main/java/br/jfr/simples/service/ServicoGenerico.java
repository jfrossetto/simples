package br.jfr.simples.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jfr.simples.model.IEntidade;
import br.jfr.simples.util.InternalServiceError;

public abstract class ServicoGenerico<T extends IEntidade, ID extends Serializable> extends ServicoUtils implements IServicoGenerico<T, ID>, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Class<T> classe;
	
	public void initServico() {
		logger.info("... initServico() ");
	}
	
	@SuppressWarnings("unchecked")
	public ServicoGenerico() {
		logger.info( this.getClass().getName() );
		logger.info( this.getClass().getSuperclass().getName() );
		ParameterizedType parameterizedType = pegaParameterizedType(this.getClass());
		if( parameterizedType == null ) {
			throw new InternalServiceError("nao e uma classe generica");
		}
		this.classe = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		logger.info( classe.getName() );
	}
	
	protected EntityManager getEntityManager() {
		if (this.entityManager == null) {
			logger.error("EntityManager não inicializado !");
			throw new IllegalStateException("EntityManager não inicializado !");
		}
		return this.entityManager;
	}
	
    protected Session getSession() {
        return (Session) this.getEntityManager().getDelegate();
    }
	
	protected Criteria createCriteria() {
        return this.getSession().createCriteria(this.getClasse());
    }

	public Class<T> getClasse() {
		return classe;
	}

	public List<T> buscaTodos() {
    	EntityManager em = this.getEntityManager();
    	CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery( this.getClasse() );
    	query.select(query.from(this.getClasse() ));
    	return em.createQuery(query).getResultList();
	}

	public T buscaPorId(ID id, boolean lock) {
    	EntityManager em = this.getEntityManager();
    	final T entity ;
        if (lock) {
        	entity = em.find(this.getClasse(), id, LockModeType.OPTIMISTIC );
        } else {
        	entity = em.find(this.getClasse(), id );
        }
		return entity;
	}

	public Long count() {
		EntityManager em = this.getEntityManager() ;
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(this.getClasse()) ));
        return em.createQuery(query).getSingleResult();
	}

	@Transactional
	public void salvar(T entidade) {
		this.getEntityManager().persist(entidade);
	}
	
	@Transactional
	public void atualizar(T entidade) {
		this.getEntityManager().merge(entidade);
	}
	
	@Transactional
	public void excluir(T entity) {
        final T persistentEntity = this.getEntityManager().getReference(
                this.getClasse(), entity.getId());
        this.getEntityManager().remove(persistentEntity);
	}

	@SuppressWarnings("unchecked")
	public List<T> carregaRegistros(String sql) {
		EntityManager em = this.getEntityManager();
		Query query = em.createQuery(sql);
		try {
			return (List<T>) query.getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null ;
		}
	}

	public T carregaEntidade(String sql, Map<String, Object> mapaParam) {

		EntityManager em = this.getEntityManager();

		Query query = em.createQuery(sql);
		
		mapaParam.forEach( (k,v) -> query.setParameter(k, v) );
		
		try {
			return (T) query.getSingleResult() ;
		} catch (javax.persistence.NoResultException e) {
			return null ;
		}
		
	}

	public List<T> carregaRegistros(String sql, Map<String, Object> mapaParam) {
		EntityManager em = this.getEntityManager();
		Query query = em.createQuery(sql); 
		mapaParam.forEach( (k,v) -> {
			if( v instanceof Calendar ) {
				query.setParameter(k, (Calendar) v, TemporalType.TIMESTAMP);
			} else {
				query.setParameter(k, v);
			}
		} );
		try {
			return (List<T>) query.getResultList();
		} catch (javax.persistence.NoResultException e) {
			return null ;
		}
	}

}
