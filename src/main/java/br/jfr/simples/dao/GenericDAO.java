package br.jfr.simples.dao;

import java.io.Serializable;
import java.util.List;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.jfr.simples.model.IPersistentEntity;

public abstract class GenericDAO<T extends IPersistentEntity, ID extends Serializable> 
      implements IGenericDAO<T, ID>, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> persistentClass;

	/**
	 * Inicia o repositorio identificando qual e a classe de nossa entidade, seu
	 * tipo {@link Class<?>}
	 */
	@SuppressWarnings({"unchecked", "unsafe"})
	public GenericDAO() {
		this.persistentClass = (Class< T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * @return nosso entityManager, inicializado e configurado
	 */
	protected EntityManager getEntityManager() {
		if (this.entityManager == null) {
			throw new IllegalStateException("The entityManager is not initialized");
		}
		return this.entityManager;
	}
    /**
     * @return a {@link Criteria} do hibernate setada para a classe do
     * repositorio
     */
	
	
	protected Criteria createCriteria() {
        return this.getSession().createCriteria(this.getPersistentClass());
    }

    protected Session getSession() {
        return (Session) this.getEntityManager().getDelegate();
    }
	
	
    /**
     * @return a classe de nossa entidade persistente
     */
    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }
	
    /**
     * Lista todos os itens, sem filtro
     *
     * @return todos os itens daquela entidade
     */
    
    public List<T> listAll() {
    	EntityManager em = this.getEntityManager();
    	CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(this.getPersistentClass() );
    	query.select(query.from(this.getPersistentClass() ));
    	return em.createQuery(query).getResultList();
    }
    
    /**
     * Tras apenas um objeto, filtrado pelo seu ID
     *
     * @param id o id que se deseja buscar
     * @param lock se devemos usar ou nao o lock
     * 
     * @return o objeto pesquisado
     */
    
    public T findById(ID id, boolean lock) {
    	EntityManager em = this.getEntityManager();
    	final T entity ;
        if (lock) {
        	entity = em.find(this.getPersistentClass(), id, LockModeType.OPTIMISTIC );
        } else {
        	entity = em.find(this.getPersistentClass(), id );
        }
		return entity;
    	
    }
    
    /**
     * Conta todos os registros da tabela
     * 
     * @return o numero de registros (rows) da tabela
     */
    
    public Long count() {
		EntityManager em = this.getEntityManager() ;
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(this.getPersistentClass())));
        return em.createQuery(query).getSingleResult();		
    }

    /**
     * Salva um entidade no banco caso ela nao exista ou atualiza ela caso o 
     * objeto passado como parametro ja exista 
     *
     * @param entity a entidade a ser salva (incluida ou atualizada)
     * 
     * @return a entidade manipulada. Se ela acaba de ser  incluida entao o seu
     *         o seu ID sera setado no objeto de retorno
     */
    
    public T save(T entity) {
        return this.getEntityManager().merge(entity);
    }

    /**
     * Deleta uma entidade pelo seu objeto persistente
     *
     * @param entity a entidade a ser deletada
     */
    
    public void delete(T entity) {
        final T persistentEntity = this.getEntityManager().getReference(
                this.getPersistentClass(), entity.getId());
        this.getEntityManager().remove(persistentEntity);
    }

}
