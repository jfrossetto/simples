package br.jfr.simples.filtro;


import br.jfr.simples.model.db.IEntidade;

public interface IFiltroPadrao<T extends IEntidade> {
	
	public String getFiltro();
	public String getQueryString();
	
}
