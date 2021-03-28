package br.jfr.simples.model.db;

import java.util.Calendar;

public interface IEntidade {
	public Long getId();
	public Boolean getRegAtivo();
	public Calendar getCriadoEm();
	public Usuario getCriadoPor();
	public Calendar getAlteradoEm();
	public Usuario getAlteradoPor();
}
