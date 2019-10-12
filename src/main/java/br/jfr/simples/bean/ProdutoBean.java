
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

import br.jfr.simples.model.Categoria;
import br.jfr.simples.model.Produto;
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
	
	private Part imgPsp;
	
	private List<Produto> produtos ;
	
	private Calendar dataini;
	private Calendar datafim;

	private String filtro ; 
	private String codigo ;
	
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
	public void buscaTabela() {
		produtos = produtoServico.buscaPorFiltro(codigo, filtro, dataini, datafim);
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
	
	public void selecionaObj(SelectEvent event) {
		produto = (Produto) event.getObject();
		modoEdicao();
		logger.info( " teste pegaOjetoCampo2: " + Utils.pegaObjetoCampo2(this, "produto.categoria.descricao") );
	}
	
    public void showUpload(Long id) {
        produto = produtoServico.findById(id); 
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
    	setMsgModalAviso("Produto: "+produto.getDescricao()+" Atualizado! ");
    	updateComponent("form_modalAviso");
    	abrirModal("modalAviso");
    	super.atualizar(ev);
    }
    
    @Override
    public void excluir(ActionEvent ev) {
    	setMsgModalAviso("Produto: "+produto.getDescricao()+" Excluido! ");
    	updateComponent("form_modalAviso");
    	abrirModal("modalAviso");
    	super.excluir(ev);
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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Calendar getDataini() {
		return dataini;
	}

	public void setDataini(Calendar dataini) {
		this.dataini = dataini;
	}

	public Calendar getDatafim() {
		return datafim;
	}

	public void setDatafim(Calendar datafim) {
		this.datafim = datafim;
	}

	//public UploadedFile getImgPsp() {
	//	return imgPsp;
	//}

	//public void setImgPsp(UploadedFile imgPsp) {
	//this.imgPsp = imgPsp;
	//}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Part getImgPsp() {
		return imgPsp;
	}

	public void setImgPsp(Part imgPsp) {
		this.imgPsp = imgPsp;
	}
	
	private String msgModalAviso;

	public String getMsgModalAviso() {
		return msgModalAviso;
	}

	public void setMsgModalAviso(String msgModalAviso) {
		this.msgModalAviso = msgModalAviso;
	}
	
}
