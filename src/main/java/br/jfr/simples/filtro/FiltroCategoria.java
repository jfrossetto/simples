package br.jfr.simples.filtro;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.jfr.simples.model.db.Categoria;

public class FiltroCategoria extends FiltroPadrao<Categoria> implements IFiltroPadrao<Categoria>, Serializable {

	private static final long serialVersionUID = 1L;
	
	public FiltroCategoria() {
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select c from Categoria c where 1 = 1 ");
		
		if( filtro != null && !filtro.isEmpty() ) {
			sql.append(" and ( c.descricao like '%"+ filtro.toUpperCase().trim() + "%' ) ");
		}
		sql.append(" order by c.id desc ");						
		
		return sql.toString();
	}

}
