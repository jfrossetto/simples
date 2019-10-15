package br.jfr.simples.model.bean;


import br.jfr.simples.model.db.IEntidade;

public interface IFiltroPadrao<T extends IEntidade> {
	
	public String getFiltro();
	public String getQueryString();
	
}
