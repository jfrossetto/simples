package br.jfr.simples.bean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jfr.simples.model.IEntidade;
import br.jfr.simples.service.IServicoGenerico;
import br.jfr.simples.service.ServicoGenerico;
import br.jfr.simples.util.InternalServiceError;

public abstract class CrudBean<T extends IEntidade, S extends ServicoGenerico> extends GenericoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected ServicoGenerico servico ;
	
	@PostConstruct
	public void init() {
		logger.info(" ... init crudBean ... ") ;
		inicializaServico();
	}
	
	@SuppressWarnings("rawtypes")
	public void inicializaServico() {
		logger.info("... inicializaServico ...");
		logger.info( "classe: " + this.getClass().getName() );
		logger.info( "super classe: " + this.getClass().getSuperclass().getName() );
		ParameterizedType parameterizedType = pegaParameterizedType(this.getClass());
		if( parameterizedType == null ) {
			throw new InternalServiceError("nao e uma classe generica");
		}
		Class classeServico = (Class<T>) parameterizedType.getActualTypeArguments()[1];
		logger.info( "classe do servico: " + classeServico.getName() );
		// procura por membros dssa classe
		Field fieldServico = pegaCampoServico(this.getClass() , classeServico );
		if( fieldServico == null ) {
			throw new InternalServiceError(" servico não declarado no bean ");
		}
		logger.info(" campo do servico: " + fieldServico.getName() );
		fieldServico.setAccessible(true);
		try {
			Method m = this.getClass().getDeclaredMethod("getProdutoServico");
			servico = (ServicoGenerico) m.invoke( this );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar o servico ");
		}
		
	}
	
	public ParameterizedType pegaParameterizedType(Class classe) {
		if( classe == null ) {
			return null ;
		} else if( classe.getGenericSuperclass() instanceof ParameterizedType ) {
			return (ParameterizedType) classe.getGenericSuperclass()  ;
		} else {
			return pegaParameterizedType(classe.getSuperclass());
		}
	}
	
	public Field pegaCampoServico(Class bean , Class classeServico ) {
		logger.info("... pegaCampoServico ...");
		for( Field f : bean.getDeclaredFields() ) {
			logger.info(" campo: " + f.getType().getName() );
			if( f.getType().getName().equals(classeServico.getName()) ) {
				return f;
			}
		}
		return null ;	
	}

}
