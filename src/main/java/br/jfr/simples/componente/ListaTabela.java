package br.jfr.simples.componente;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.jfr.simples.model.bean.FiltroPadrao;
import br.jfr.simples.model.bean.IFiltroPadrao;
import br.jfr.simples.model.db.IEntidade;
import br.jfr.simples.service.ServicoGenerico;

public class ListaTabela<T extends IEntidade> extends LazyDataModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected ServicoGenerico servico ;
	protected IFiltroPadrao filtro;

	public ListaTabela(ServicoGenerico servico) {
		super();
		this.servico = servico;
	}

	@Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List tabela ;
		this.setRowCount(10);
		tabela = servico.carregaPagina(filtro, first, pageSize);
		return tabela;
	 }
	
    @Override
    public Object getRowKey(T object) {
        return object.getId();
    }
    
    @Override
    public T getRowData(String rowKey) {

        final Long key = Long.parseLong(rowKey);

        for (T t : this.getModelSource()) {
            if (t.getId().equals(key)) {
                return t;
            }
        }

        return null;
    }

    public List<T> getModelSource() {
        return (List<T>) this.getWrappedData();
    }
	
	public IFiltroPadrao getFiltro() {
		return filtro;
	}

	public ListaTabela setFiltro(IFiltroPadrao filtro) {
		this.filtro = filtro;
		return this;
	}

	public ServicoGenerico getServico() {
		return servico;
	}

	public ListaTabela setServico(ServicoGenerico servico) {
		this.servico = servico;
		return this;
	}

	
}
