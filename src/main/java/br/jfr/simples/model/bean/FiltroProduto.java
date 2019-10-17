package br.jfr.simples.model.bean;

import java.io.Serializable;
import java.util.Calendar;

import br.jfr.simples.model.db.IEntidade;
import br.jfr.simples.model.db.Produto;

public class FiltroProduto extends FiltroPadrao<Produto> implements IFiltroPadrao<Produto>, Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar dataini;
	private Calendar datafim;
	
	public FiltroProduto() {
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


	@Override
	public String getQueryString() {
		return null;
	}

}
