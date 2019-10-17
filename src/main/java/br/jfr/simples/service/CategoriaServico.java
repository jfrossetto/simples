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
import br.jfr.simples.model.bean.FiltroPadrao;
import br.jfr.simples.model.bean.IFiltroPadrao;
import br.jfr.simples.model.db.Categoria;
import br.jfr.simples.model.db.Usuario;

@RequestScoped
public class CategoriaServico extends ServicoGenerico<Categoria, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<Categoria> buscaAutoComplete(String filtro) {
		
		Map<String,Object> mapaParam = new HashMap<>() ;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c from Categoria c where 1 = 1 ");
		
		if( filtro != null ) {
			sql.append(" and ( c.descricao like :filtro ) ") ; 
	    	mapaParam.put("filtro", "%" + filtro.trim() + "%" );
		}
		sql.append(" order by c.id desc ");						
		
		return carregaRegistros(sql.toString(), mapaParam);
		
	}
	
	public List<Categoria> carregaPagina(IFiltroPadrao filtro, int pagina , int tamanhopagina) {
		
		Map<String,Object> mapaParam = new HashMap<>() ;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c from Categoria c where 1 = 1 ");
		
		if( filtro != null && filtro.getFiltro() != null && ! filtro.getFiltro().isEmpty() ) {
			sql.append(" and ( c.descricao like :filtro ) ") ; 
	    	mapaParam.put("filtro", "%" + filtro.getFiltro().toUpperCase().trim() + "%" );
		}
		sql.append(" order by c.id desc ");						
		
		return carregaPagina(sql.toString(), mapaParam, pagina, tamanhopagina);
		
	}
    
    public void valoresIniciais(Categoria categoria) {
    }


}
