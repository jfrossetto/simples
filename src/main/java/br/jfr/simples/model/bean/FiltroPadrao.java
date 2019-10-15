package br.jfr.simples.model.bean;

import java.io.Serializable;

import br.jfr.simples.model.db.IEntidade;

public class FiltroPadrao<T extends IEntidade> implements IFiltroPadrao<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String filtro ;
	
	public FiltroPadrao() {
	}
	
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	@Override
	public String getQueryString() {
		return null;
	}

}
