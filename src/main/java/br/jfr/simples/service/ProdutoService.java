package br.jfr.simples.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.jfr.simples.dao.IProdutoDAO;
//import br.hh.siteAdmin.util.InternalServiceError;
import br.jfr.simples.model.Produto;

@RequestScoped
public class ProdutoService implements Serializable {
		
	private static final long serialVersionUID = 1L;
		
	@Inject
	private IProdutoDAO produtoDAO ;

    public List<Produto> listProdutos() {
        return this.produtoDAO.findAll();
    	//return this.produtoDAO.listAll();
    }
    
    public Produto findById(Long id) {
        return this.produtoDAO.findById(id, false);
    }
    
    public String findDescOra(String codora) {
    	return this.produtoDAO.findDescOra(codora) ;
    }
    
	public List<Produto> findByFiltro(String codigo, String filtro, Calendar dataini, Calendar datafim) {
		return this.produtoDAO.findByFiltro(codigo, filtro, dataini, datafim);
	}

	public List<Object[]> findContrato(String codora) {
		return this.produtoDAO.findContrato(codora);
	}
	
	public List<Object> findProdValidade( String codora ) {
		return this.produtoDAO.findProdValidade(codora);
	}
	
	public List<Produto> findByCat(String cat) {
		return this.produtoDAO.findByCat(cat);
	}
	
	public List<Produto> findProdDistr( String customer_number ) {
		return this.produtoDAO.findProdDistr(customer_number);
	}

    @Transactional
    public Produto salvar(Produto produto) {
    	
    	/*
        if (produto.getDescricao() == null ) {
            throw new InternalServiceError("descricao obrigat�ria");
        }
    	*/
    	
    	return this.produtoDAO.save(produto);
    }

    @Transactional
    public Produto atualizaProduto(Produto produto) {
    	
    	/*
        final PeriodoReposicao found = this.periodoReposicaoDAO.
                findByDataInicioFim( periodoReposicao.getDatainicio() ,
                periodoReposicao.getDatafim() ) ;

        if (found != null && !found.equals( periodoReposicao ) ) {
            throw new InternalServiceError("periodo reposicao alterado ja cadastrado");
        }
        */
    	
//        if (produto.getDescricao() == null ) {
//            throw new InternalServiceError("alt descricao obrigat�ria");
//        }
    	
        return this.produtoDAO.save(produto);

    }

}
