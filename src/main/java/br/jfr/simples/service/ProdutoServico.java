package br.jfr.simples.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;

import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.filtro.FiltroPadrao;
import br.jfr.simples.filtro.FiltroProduto;
import br.jfr.simples.filtro.IFiltroPadrao;
import br.jfr.simples.model.db.Categoria;
import br.jfr.simples.model.db.Produto;
import br.jfr.simples.model.db.Usuario;

@RequestScoped
public class ProdutoServico extends ServicoGenerico<Produto, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public List<Produto> buscaAutoComplete(String filtro) {
		
		Map<String,Object> mapaParam = new HashMap<>() ;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select p from Produto c where 1 = 1 ");
		
		if( filtro != null ) {
			sql.append(" and ( p.descricao like :filtro ) ") ; 
	    	mapaParam.put("filtro", "%" + filtro.trim() + "%" );
		}
		sql.append(" order by p.id desc ");						
		
		return carregaRegistros(sql.toString(), mapaParam);
		
	}
	
	public List<Produto> buscaPorCat(String cat) {
		
		Map<String,Object> mapaParam = new HashMap<>() ;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select p from Produto p where 1 = 1 ");
		
		if( cat != null && !cat.isEmpty() ) {
			sql.append("    and p.cats = :pcat ");
			mapaParam.put("cat",cat);
		}
		sql.append(" order by preco " );
		
		return carregaRegistros(sql.toString(), mapaParam);
		
	}
	
    public void valoresIniciais(Produto produto) {
    	produto.setDatainicio( Calendar.getInstance() );
    };

    @Transactional
    public void salvar(Produto produto) {
    	super.salvar(produto);
    }
    
    @Transactional
    public void atualizar(Produto produto) {
    	super.atualizar(produto);
    }

}
