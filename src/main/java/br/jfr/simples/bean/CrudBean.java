package br.jfr.simples.bean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jfr.simples.bean.GenericoBean.ViewState;
import br.jfr.simples.model.IEntidade;
import br.jfr.simples.model.Produto;
import br.jfr.simples.service.IServicoGenerico;
import br.jfr.simples.service.ServicoGenerico;
import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.util.Utils;

public abstract class CrudBean<T extends IEntidade, S extends ServicoGenerico> extends GenericoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UsuarioLogado usuarioLogado;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected ServicoGenerico servico ;

	protected Class classeObjPrincipal ;
	protected Field fieldObjPrincipal ; 
	
	@PostConstruct
	public void init() {
		logger.info(" ... init crudBean ... ") ;
		inicializaServico();
		validaObjPrincipal();
    	modoListaTabela();
	}
	
	public void modoListaTabela() {
		logger.info(" ... modoListaTabela() ...");
        this.viewState = ViewState.LISTING;
		buscaTabela();
		executeJS("PF('tabela').unselectAllRows();");
		updatePanelsConteudo();
    }
	
	public void buscaTabela() {
	}
	
    public String getDefaultMessagesComponentId() {
        return "formCadastro:messages"; 
    }
    
    public boolean podeIncluir() {
    	return true;
    }
    
	@SuppressWarnings("rawtypes")
	public void inicializaServico() {
		logger.info("... inicializaServico ...");
		logger.info( "classe: " + this.getClass().getName() );
		logger.info( "super classe: " + this.getClass().getSuperclass().getName() );
		ParameterizedType parameterizedType = Utils.pegaParameterizedType(this.getClass());
		if( parameterizedType == null ) {
			throw new InternalServiceError("nao e uma classe generica");
		}
		Class classeServico = (Class<T>) parameterizedType.getActualTypeArguments()[1];
		logger.info( "classe do servico: " + classeServico.getName() );
		// procura por membros dssa classe
		Field fieldServico = Utils.pegaCampoClasse(this.getClass() , classeServico ); 
		if( fieldServico == null ) {
			throw new InternalServiceError(" servico não declarado no bean ");
		}
		String getServico = Utils.nomeMetodoGetSet("get",fieldServico.getName()); 
		logger.info(" campo do servico: " + fieldServico.getName() + " get: " + getServico);
		fieldServico.setAccessible(true);
		try {
			Method m = this.getClass().getDeclaredMethod(getServico);
			servico = (ServicoGenerico) m.invoke( this );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar o servico ");
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void validaObjPrincipal() {
		logger.info("... validaObjPrincipal ...");
		logger.info( "classe: " + this.getClass().getName() );
		logger.info( "super classe: " + this.getClass().getSuperclass().getName() );
		ParameterizedType parameterizedType = Utils.pegaParameterizedType(this.getClass());
		if( parameterizedType == null ) {
			throw new InternalServiceError("nao e uma classe generica");
		}
		classeObjPrincipal = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		logger.info( "classe obj principal : " + classeObjPrincipal.getName() );
		fieldObjPrincipal = Utils.pegaCampoClasse(this.getClass() , classeObjPrincipal ); 
		if( fieldObjPrincipal == null ) {
			throw new InternalServiceError(" obj principal não declarado no bean ");
		}
		String objPrincipalGet = Utils.nomeMetodoGetSet("get",fieldObjPrincipal.getName()); 
		logger.info(" campo do obj principal : " + fieldObjPrincipal.getName() + " get: " + objPrincipalGet);
	}
	
	public Object getObjPrincipal() {
		String objPrincipalGet = Utils.nomeMetodoGetSet("get",fieldObjPrincipal.getName()); 
		fieldObjPrincipal.setAccessible(true);
		try {
			Method m = this.getClass().getDeclaredMethod(objPrincipalGet);
			return m.invoke( this );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar obj principal ");
		}
	}
	
	public void setObjPrincipal(Object value) {
		String objPrincipalSet = Utils.nomeMetodoGetSet("set",fieldObjPrincipal.getName()); 
		fieldObjPrincipal.setAccessible(true);
		try {
			Method m = this.getClass().getDeclaredMethod(objPrincipalSet,classeObjPrincipal);
			m.invoke( this , value );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InternalServiceError(" erro ao acessar obj principal " + e.getMessage() );
		}
	}
	
	public void novoObjPrincipal() {
		try {
			setObjPrincipal( classeObjPrincipal.newInstance() );
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InternalServiceError(" erro ao inicar obj principal " +e.getMessage());
		}
	}
	
	public void configuraValoresIniciais() {
		servico.valoresIniciais( ((T)getObjPrincipal()) );
	}
	
    public void modoNovoCadastro(ActionEvent ev) {
        this.viewState = ViewState.ADDING;
        novoObjPrincipal() ;
        configuraValoresIniciais();
        updatePanelsConteudo();
    }
    
    public void modoEdicao() {
        this.viewState = ViewState.EDITING;
        updatePanelsConteudo();
    }
    
    @SuppressWarnings("unchecked")
	public void salvar(ActionEvent ev) {
		logger.info(" ... atualizar ...");    	
        try {
            servico.salvar( (T) getObjPrincipal() );
            addInfo(true, "Registro Incluido Com Sucesso"+ ((T) getObjPrincipal()).getId() );
            modoEdicao();
        } catch (InternalServiceError ex) {
            addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            addError(true, "erro java ", ex.getMessage());
        }
    }
    
    public void atualizar(ActionEvent ev) {
		logger.info(" ... atualizar ...");
        try {
            servico.atualizar((T) getObjPrincipal());
            addInfo(true,"Registro Atualizado Com Sucesso");
            logger.info("registro atualizado: " + classeObjPrincipal.getName() + " id: " + ((T) getObjPrincipal()).getId() );
        } catch (InternalServiceError ex) {
            addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            addError(true, "erro java ", ex.getMessage());
        }
    }

    public void excluir(ActionEvent ev) {
		logger.info(" ... excluir ...");
        try {
            servico.excluir( (T) getObjPrincipal() );
            addInfo(true,"Registro Excluido Com Sucesso");
            modoListaTabela();
        } catch (InternalServiceError ex) {
            addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            addError(true, "erro java ", ex.getMessage());
        }
    }
    
	public List carregaAutoComplete(String query) {
		logger.info(" carregaAutoComplete - query: "+query);
        AutoComplete autoComplete = (AutoComplete) UIViewRoot.getCurrentComponent(facesContext.getCurrentInstance());
        String nomeServico = (String) autoComplete.getAttributes().get("servicoautocomplete");
        logger.info(" carregaAutoComplete - servico: "+nomeServico);
        ServicoGenerico servicoAutoComplete = (ServicoGenerico) Utils.pegaObjetoCampo(this,nomeServico);
		//List categoriaLista = categoriaServico.buscaPorFiltro(query);
		List lista = servicoAutoComplete.buscaAutoComplete(query);
		return lista;
	}
	

	// getters & setters
	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	
}
