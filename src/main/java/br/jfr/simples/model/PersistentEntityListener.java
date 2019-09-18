package br.jfr.simples.model;

import java.util.Calendar;

import javax.enterprise.inject.spi.BeanManager;
import org.omnifaces.util.BeansLocal;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

//import br.hh.base.bean.UsuarioLogado;

public class PersistentEntityListener {
	
	@Inject
    private BeanManager beanManager;
	
    /**
     * Listerner de pre-persistencia do dados
     * 
     * @param entity a entidade a ser afetada pelo evento
     */
    @PrePersist
    public void prePersist(PersistentEntity entity) {
   		entity.setCreationDate( Calendar.getInstance() );
   		entity.setCreatedBy(this.getAuthenticated() );
        entity.setLastUpdateDate( Calendar.getInstance() );
       	entity.setLastUpdatedBy(this.getAuthenticated() );
    }
    
    /**
     * Listerner de pre-atualizacao do dados
     * 
     * @param entity a entidade a ser afetada pelo evento
     */
    @PreUpdate
    public void preUpdate(PersistentEntity entity) {
        entity.setLastUpdateDate( Calendar.getInstance() );
       	entity.setLastUpdatedBy(this.getAuthenticated() );
    }
    
    /**
     * @return o usuario autenticado
     */
    private Long getAuthenticated() {
    	try {
    		//UsuarioLogado usarioLogado = BeansLocal.getInstance(this.beanManager, UsuarioLogado.class);
    		//if( usarioLogado == null || usarioLogado.getMaut() == null ) {
    			return 0L;
    		//}
    		//return usarioLogado.getMaut().getId() ;
    	}
    	catch (Exception e) {
    		return -1L;
    	}
    }
    
}
