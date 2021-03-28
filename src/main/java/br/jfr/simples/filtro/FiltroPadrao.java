package br.jfr.simples.filtro;

import java.io.Serializable;

import br.jfr.simples.model.db.IEntidade;

public abstract class FiltroPadrao<T extends IEntidade> implements IFiltroPadrao<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected String filtro ;
	
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
