package br.jfr.simples.filtro;

import java.io.Serializable;
import java.util.Calendar;

import br.jfr.simples.model.db.IEntidade;
import br.jfr.simples.model.db.Produto;
import br.jfr.simples.util.DataUtils;

public class FiltroProduto extends FiltroPadrao<Produto> implements IFiltroPadrao<Produto>, Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar dataini;
	private Calendar datafim;
	
	public FiltroProduto() {
	}
	
	@Override
	public String getQueryString() {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select p from Produto p where 1 = 1 ");

		if( filtro != null && !filtro.isEmpty() ) {
			sql.append(" and ( p.descricao like '%"+ filtro.toUpperCase().trim() + "%' ")
				.append( "    or p.categoria.descricao like '%"+ filtro.toUpperCase().trim() + "%' ) ");
		}
		
		if( dataini != null ) {
			sql.append(" and ( p.datainicio >= '" + DataUtils.formataDataAMD(dataini) + "' ) ");
		}
		if( datafim != null ) {
			sql.append(" and ( p.datafim < '" + DataUtils.formataDataAMD(datafim) + "' ) ");
		}
		
		sql.append(" order by p.id desc ");						
		
		return sql.toString();
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


}
