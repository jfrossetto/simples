
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
import br.jfr.simples.filtro.FiltroCategoria;
import br.jfr.simples.filtro.FiltroPadrao;
import br.jfr.simples.filtro.FiltroProduto;
import br.jfr.simples.model.bean.ModalAvisoConf;
import br.jfr.simples.model.db.Categoria;
import br.jfr.simples.model.db.Produto;
import br.jfr.simples.service.CategoriaServico;
import br.jfr.simples.service.ProdutoServico;
import br.jfr.simples.service.ServicoGenerico;
import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.util.Utils;

@Named
@ViewScoped
public class ProdutoBean extends CrudBean<Produto, ProdutoServico> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Produto produto ;
	private FiltroProduto produtoFiltro = new FiltroProduto();
	private FiltroCategoria categoriaFiltro = new FiltroCategoria();
	
	private Part imgPsp;
	
//	private List<Categoria> categorias ;
	
    @Inject
    private ProdutoServico produtoServico;
    @Inject 
    private CategoriaServico categoriaServico;
    
	@PostConstruct
	@Override
    public void init() {
		super.init();
    	logger.info(" ... init produtoBean ...");
    	servico.initServico();
    }
    
	@Override
	public void modoListaTabela() {
		super.modoListaTabela();
		logger.info(" ... modoListaTabela() ...");
    }
	
    @Override
	public void buscaTabelaPrincipal() {
		listaTabela = new ListaTabela<Produto>(produtoServico)
							.setFiltro(produtoFiltro);
		return ;	
	}
    
    @Override
    public boolean podeIncluir() {
    	return usuarioLogado.temPermissao("R_PRODUTO_NEW");
    }

    /*
    @Override
    public void modoNovoCadastro() {
    	super.modoNovoCadastro();
    }
    
    public void modoEdicao() {
        this.viewState = ViewState.EDITING;
        updatePanelsConteudo();
    }
    */
	
    @Override
	public void selecionaObjPrincipal(SelectEvent event) {
		if( objLista instanceof Produto ) {
			produto = (Produto) objLista;
			logger.info( " teste pegaOjetoCampo2: " + Utils.pegaObjetoCampo2(this, "produto.categoria.descricao") );
			modoEdicao();
		}
	}
    
    @Override
	public void selecionaObjModal(SelectEvent event) {
		if( objListaModal instanceof Categoria ) {
			produto.setCategoria( (Categoria) objListaModal );
			updatePanelsConteudo();
			fecharModal(classeModal);
		} 
	}
	
    public void showUpload(Long id) {
        //produto = produtoServico.buscaPorId(id, false); 
        this.updateAndOpenDialog("imgProdutoDialog", "dialogImgProduto");
    }
    
    public void doUpload() {
    	try {
    		Path fileToDeletePath = Paths.get("/u/imghh/psp/psp_" + produto.getId().toString() + "_.png");
    		Files.delete(fileToDeletePath);
    	}
    	catch (IOException ex) {
       		System.out.println( ex.getMessage() ) ;
       		ex.printStackTrace();
       	}
        try {    		
     		imgPsp.write("/u/imghh/psp/psp_" + produto.getId().toString() + "_.png" );
            this.addInfo(true, "Imagem Carregada");
    	} 
        catch (IOException ex) {
    		System.out.println( ex.getMessage() ) ;
    		this.addError( true, "erro ao salvar arquivo" , ex.getMessage()  );
    		ex.printStackTrace();
    	}
        this.closeDialog("dialogImgProduto");
    }

    /*
    public void salvar(ActionEvent ev) {
        try {
            produtoServico.salvar(produto);
            addInfo(true, "Produto Incluido "+produto.getId());
            modoEdicao();
        } catch (InternalServiceError ex) {
            addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            addError(true, "erro java ", ex.getMessage());
        }
    }
    */

    @Override
    public void atualizar(ActionEvent ev) {
    	super.atualizar(ev);
    }
    
    @Override
    public void modalAvisoConfBtn2() {
    	logger.info(" ... modalAvisoConfBtn2() ... ");
    	if(  pegaModalAvisoConf() != null && pegaModalAvisoConf().getTitulo().equals("Confirma Exclusão") ) {
    		logger.info(" ... excluindo sqn ... ");
    		//super.excluir(null);
        	abrirModalAvisoConf( new ModalAvisoConf()
        								.setTitulo("Excluido")
        								.setMsgCorpo("Produto: "+produto.getDescricao()+" Excluido! ")
        					   );
    		
    	}
    }
    
    @Override
    public void excluir(ActionEvent ev) {
    	abrirModalAvisoConf( new ModalAvisoConf()
                .setTitulo("Confirma Exclusão")
                .setMsgCorpo("Produto: "+produto.getDescricao() )
                .setSimNao()
			);
    }

    
	public void checkProd(javax.faces.event.AjaxBehaviorEvent event) {
	}
	
	// getters & setters 
	public ProdutoServico getProdutoServico() {
		return produtoServico;
	}
	
	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}

	@Override
    public String getTituloView() {
    	return "Produto";
    }
	
	@Override
    public String getDetalheTituloView() {
    	if( viewState.equals(ViewState.EDITING) && produto != null ) {
    		return produto.getDescricao();
    	}
    	return "";
    }

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	//public UploadedFile getImgPsp() {
	//	return imgPsp;
	//}

	//public void setImgPsp(UploadedFile imgPsp) {
	//this.imgPsp = imgPsp;
	//}


	public FiltroProduto getProdutoFiltro() {
		return produtoFiltro;
	}

	public void setProdutoFiltro(FiltroProduto produtoFiltro) {
		this.produtoFiltro = produtoFiltro;
	}

	public FiltroCategoria getCategoriaFiltro() {
		return categoriaFiltro;
	}

	public void setCategoriaFiltro(FiltroCategoria categoriaFiltro) {
		this.categoriaFiltro = categoriaFiltro;
	}

	public Part getImgPsp() {
		return imgPsp;
	}

	public void setImgPsp(Part imgPsp) {
		this.imgPsp = imgPsp;
	}
	
/*
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
*/
}
