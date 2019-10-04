package br.jfr.simples.bean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.UploadedFile;

import br.jfr.simples.bean.GenericoBean;
import br.jfr.simples.model.Produto;
import br.jfr.simples.service.ProdutoServico;
import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.bean.UsuarioLogado;

@Named
@ViewScoped
public class ProdutoBean extends CrudBean<Produto, ProdutoServico> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long produtoId;
	
	private Produto produto ;
	
	private Part imgPsp;
	
	private List<Produto> produtos ;
	
	private Calendar dataini;
	private Calendar datafim;

	private String filtro ; 
	private String codigo ;
	
    @Inject
	private UsuarioLogado usarioLogado ;
	
    @Inject
    private ProdutoServico produtoServico;
    
    @Inject
    private FacesContext fc;
    
    public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
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

	@PostConstruct
	@Override
    public void init() {
		super.init();
    	logger.info(" ... init produtoBean ...");
    	servico.initServico();
    	//this.produto = new Produto();
    }
    
	public void initializeListing() {
        this.viewState = ViewState.LISTING;
		this.produtos = produtoServico.buscaTodos();
    }

    public void initializeForm(long produtoId) {

        if (produtoId == 0) {
            this.viewState = ViewState.ADDING;
            this.produto = new Produto();
        } else {
            this.viewState = ViewState.EDITING;
            this.produto = this.produtoServico.findById(produtoId);
    		System.out.println( produto.getDescricao() );
        }
    }
    
	public void checkProd(javax.faces.event.AjaxBehaviorEvent event) {
	}
    
    public String changeToAdd() {
        this.viewState = ViewState.ADDING;
        return "formProduto.xhtml?faces-redirect=true";
    }

    public String changeToListing() {
        return "listProduto.xhtml?faces-redirect=true";
    }

    public String changeToEdit(long produtoId) {
        this.viewState = ViewState.EDITING;
        return "formProduto.xhtml?faces-redirect=true&produtoId=" + produtoId;
    }
    
	public void doSearch() {
		produtos = produtoServico.buscaPorFiltro(codigo, filtro, dataini, datafim);
		return ;	
	}
	
    public String doCancel() {
        return "listProduto.xhtml?faces-redirect=true";
    }
    
	
    public void changeToDelete(long periodoReposicaoId) {
    	/*
        this.periodoReposicao = this.periodoReposicaoService.findPeriodoReposicaoById(periodoReposicaoId);
        this.updateAndOpenDialog("deletePeriodoReposicaoDialog", "dialogDeletePeriodoReposicao");
        */
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

    public void doSave() {
    	
        try {
            this.produtoServico.salvar(produto);
            this.addInfo(true, "Produto Incluido "+produto.getId());
            this.viewState = ViewState.EDITING;
            updateComponent("panel_crud");
        } catch (InternalServiceError ex) {
            this.addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            this.addError(true, "erro java ", ex.getMessage());
        }

    }

    public void doUpdate() {
    	
        try {
            this.produtoServico.atualizaProduto(produto);
            this.addInfo(true, "Produto Atualizado !");
            this.logger.info("produto atualizado: " + produto.getDescricao() );
        } catch (InternalServiceError ex) {
            this.addError(true, ex.getMessage(), ex.getParameters());
        } catch (Exception ex) {
            this.logger.error(ex.getMessage(), ex);
            this.addError(true, "erro java ", ex.getMessage());
        }
        
    }

    public void doDelete() {
    	
        
    }

	public ProdutoServico getProdutoServico() {
		return produtoServico;
	}

    

}
