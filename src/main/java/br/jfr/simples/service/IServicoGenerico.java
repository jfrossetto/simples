package br.jfr.simples.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.jfr.simples.filtro.FiltroPadrao;
import br.jfr.simples.filtro.IFiltroPadrao;
import br.jfr.simples.model.db.IEntidade;

public interface IServicoGenerico<T extends IEntidade, ID extends Serializable> {

	public void initServico();
    public List<T> buscaTodos();
    public List<T> buscaAutoComplete(String filtro);
    public T buscaPorId(ID id, boolean lock);
    public Long count();
    public void valoresIniciais(T entity);
    public void salvar(T entity);
    public void atualizar(T entity);
    public void excluir(T entity);
    public List<T> carregaRegistros(String sql);
    public List<T> carregaRegistros(String sql,Map<String,Object> mapaParam);

    public List<T> carregaPagina(IFiltroPadrao filtro, int pagina , int tamanhopagina) ;    
    public List<T> carregaPagina(String sql, Map<String, Object> mapaParam, int pagina , int tamanhopagina) ;
    public List<T> carregaPagina(String sql, int pagina , int tamanhopagina) ;
    
    public T carregaEntidade(String sql,Map<String,Object> mapaParam);

    
}
