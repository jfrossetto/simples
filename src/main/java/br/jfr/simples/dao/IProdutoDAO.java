package br.jfr.simples.dao;

import java.util.Calendar;
import java.util.List;

//import br.jfr.simples.model.Pedido;
import br.jfr.simples.model.Produto;

public interface IProdutoDAO extends IGenericDAO<Produto, Long> { 

	public List<Produto> findAll();
	public String findDescOra( String codora ) ;
	public List<Produto> findByFiltro(String codigo , String filtro , Calendar dataini , Calendar datafim) ;
	public List<Object[]> findContrato(String codora) ;	
	public List<Produto> findByCat(String cat) ;
	public List<Produto> findProdDistr( String customer_number ) ;
	
	public List<Object> findProdValidade( String codora ) ;  
	public List<Produto> findProdsVen() ;
	public List<Produto> findProdsCort() ;	
}
