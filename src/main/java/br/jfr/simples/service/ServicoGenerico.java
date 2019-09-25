package br.jfr.simples.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.jfr.simples.model.IEntidade;

public class ServicoGenerico<T extends IEntidade, ID extends Serializable> extends ServicoUtils implements IServicoGenerico<T, ID>, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	private final Class<T> classe;
	//private final IEntidade entidade;
	
	@SuppressWarnings("unchecked")
	public ServicoGenerico() {
		this.classe = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		//this.entidade = (IEntidade) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
	public T salvar(T entidade) {
		return this.getEntityManager().merge(entidade);
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

}
