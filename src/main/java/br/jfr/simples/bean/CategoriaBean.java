
package br.jfr.simples.bean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.Part;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.jfr.simples.componente.ListaTabela;
import br.jfr.simples.model.bean.FiltroPadrao;
import br.jfr.simples.model.bean.ModalAvisoConf;
import br.jfr.simples.model.db.Categoria;
import br.jfr.simples.model.db.Produto;
import br.jfr.simples.service.CategoriaServico;
import br.jfr.simples.service.ProdutoServico;
import br.jfr.simples.service.ServicoGenerico;
import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.util.Utils;
import javassist.expr.Instanceof;

@Named
@ViewScoped
public class CategoriaBean extends CrudBean<Categoria, CategoriaServico> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Categoria categoria ;
	
    @Inject 
    private CategoriaServico categoriaServico;
    
    private FiltroPadrao categoriaFiltro = new FiltroPadrao();
    
	@PostConstruct
	@Override
    public void init() {
		super.init();
    	logger.info(" ... init categoriaBean ...");
    	servico.initServico();
    }
    
	@Override
	public void modoListaTabela() {
		super.modoListaTabela();
		logger.info(" ... modoListaTabela() ...");
    }
	
    @Override
	public void buscaTabela() {
		listaTabela = new ListaTabela<Categoria>(categoriaServico)
								.setFiltro(categoriaFiltro);
		return ;	
	}
    
    @Override
    public boolean podeIncluir() {
    	return usuarioLogado.temPermissao("R_PRODUTO_NEW");
    }

    @Override
	public void selecionaObj(SelectEvent event) {
		if( objLista instanceof Categoria ) {
			categoria = (Categoria) objLista;
			modoEdicao();
		}
	}
    
    @Override
    public void modalAvisoConfBtn2() {
    	logger.info(" ... modalAvisoConfBtn2() ... ");
    	if(  pegaModalAvisoConf() != null && pegaModalAvisoConf().getTitulo().equals("Confirma Exclusão") ) {
    		logger.info(" ... excluindo sqn ... ");
    		//super.excluir(null);
    	}
    }
    
    @Override
    public void excluir(ActionEvent ev) {
    	abrirModalAvisoConf( new ModalAvisoConf()
                .setTitulo("Confirma Exclusão")
                .setMsgCorpo("Categoria: "+categoria.getDescricao() )
                .setSimNao()
			);
    }
    

	@Override
    public String getTituloView() {
    	return "Categoria";
    }
	
	@Override
    public String getDetalheTituloView() {
    	if( viewState.equals(ViewState.EDITING) && categoria != null ) {
    		return categoria.getDescricao();
    	}
    	return "";
    }
	
	// getters & setters 
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getFiltro() {
		return categoriaFiltro.getFiltro();
	}

	public void setFiltro(String filtro) {
		categoriaFiltro.setFiltro(filtro);
	}

	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}
	
}
