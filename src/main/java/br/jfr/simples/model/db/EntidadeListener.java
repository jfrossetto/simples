package br.jfr.simples.model.db;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Objects;

import javax.enterprise.inject.spi.BeanManager;
import org.omnifaces.util.BeansLocal;

import br.jfr.simples.bean.UsuarioLogado;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntidadeListener {
	
	@Inject
    private BeanManager beanManager;
	
    @PrePersist
    public void prePersist(Entidade entity) {
   		entity.setCriadoEm( Calendar.getInstance() );
   		entity.setCriadoPor( getAuthenticated() );
   		entity.setRegAtivo(true);
        entity.setAlteradoEm( Calendar.getInstance() );
       	entity.setAlteradoPor( getAuthenticated() );
    }
    
    @PreUpdate
    public void preUpdate(Entidade entity) {
        entity.setAlteradoEm( Calendar.getInstance() );
       	entity.setAlteradoPor( getAuthenticated() );
    }
    
    private Usuario getAuthenticated() {
    	try {
    		UsuarioLogado usarioLogado = BeansLocal.getInstance(this.beanManager, UsuarioLogado.class);
    		if( Objects.nonNull(usarioLogado) ||  Objects.nonNull( usarioLogado.getUsuario() ) ) {
    			return usarioLogado.getUsuario();
    		}
    		return null;
    	}
    	catch (Exception e) {
    		return null;
    	}
    }
    
}
