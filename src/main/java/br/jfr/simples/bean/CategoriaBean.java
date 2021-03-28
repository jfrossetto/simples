
package br.jfr.simples.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import org.primefaces.event.SelectEvent;

import br.jfr.simples.componente.ListaTabela;
import br.jfr.simples.filtro.FiltroCategoria;
import br.jfr.simples.model.bean.ModalAvisoConf;
import br.jfr.simples.model.db.Categoria;
import br.jfr.simples.service.CategoriaServico;

@Named
@ViewScoped
public class CategoriaBean extends CrudBean<Categoria, CategoriaServico> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Categoria categoria ;
	
    @Inject 
    private CategoriaServico categoriaServico;
    
    private FiltroCategoria categoriaFiltro = new FiltroCategoria();
    
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
	public void buscaTabelaPrincipal() {
		listaTabela = new ListaTabela<Categoria>(categoriaServico)
								.setFiltro(categoriaFiltro);
		return ;	
	}
    
    @Override
    public boolean podeIncluir() {
    	return usuarioLogado.temPermissao("R_PRODUTO_NEW");
    }

    @Override
	public void selecionaObjPrincipal(SelectEvent event) {
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
    		super.excluir(null);
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

	public FiltroCategoria getCategoriaFiltro() {
		return categoriaFiltro;
	}

	public void setCategoriaFiltro(FiltroCategoria categoriaFiltro) {
		this.categoriaFiltro = categoriaFiltro;
	}

	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}
	
}
