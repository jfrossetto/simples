package br.jfr.simples.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.jfr.simples.model.IEntidade;

public interface IServicoGenerico<T extends IEntidade, ID extends Serializable> {

    public List<T> buscaTodos();
    public T buscaPorId(ID id, boolean lock);
    public Long count();
    public T salvar(T entity);
    public void excluir(T entity);
    public List<T> carregaRegistros(String sql);
    public T carregaEntidade(String sql,Map<String,Object> mapaParam);
	
}
