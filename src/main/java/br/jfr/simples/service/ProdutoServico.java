package br.jfr.simples.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.jfr.simples.util.InternalServiceError;
import br.jfr.simples.model.Produto;
import br.jfr.simples.model.Usuario;

@RequestScoped
public class ProdutoServico extends ServicoGenerico<Produto, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Produto> findAll() {
		EntityManager em = this.getEntityManager();
		
		TypedQuery<Produto> query = em
				.createQuery(
						"select p from Produto p"
						+ " order by id desc " 
						,Produto.class);

		return query.getResultList();
	}
	
	public List<Produto> findByFiltro(String codigo, String filtro, Calendar dataini, Calendar datafim) {
		
		EntityManager em = this.getEntityManager();
		String sql ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		sql = "select p from Produto p where 1 = 1 ";
		
		if( ! (codigo == null || codigo.equals("") ) ) {
			sql += " and p.id = :pidprod " ; 
		}
		
		if( dataini != null ) {
			sql = sql + " and p.datainicio >= :pdtini " ;
		}
		if( datafim != null ) {
			sql = sql + " and p.datafim < :pdtfim " ;			
		}
		if( !(filtro == null || filtro.equals("")) ) {
			sql += " and ( p.descricao like :pfiltro " 
	    		+ "    or p.cats like :pfiltro " 
		    	+ "    or p.codProduto like :pfiltro ) " ;
		}
		
		sql += " order by p.id desc " ;						
		
		TypedQuery<Produto> query = em
				.createQuery( sql , 
								Produto.class ) ;
		
		if( sql.contains(":pidprod") ) {
			query.setParameter("pidprod", new Long(codigo) );
		}
		if( sql.contains(":pdtini") ) {
			String sdatai = sdf.format(dataini.getTime()) ;
			query.setParameter("pdtini", dataini , TemporalType.TIMESTAMP );
		}
		if( sql.contains(":pdtfim") ) {
			String sdataf = sdf.format(datafim.getTime()) ;
			query.setParameter("pdtfim", datafim , TemporalType.TIMESTAMP  );
		}
		if( sql.contains(":pfiltro") ) {
			query.setParameter("pfiltro", "%"+filtro+"%");
		}
		
		try {
			return query.getResultList();
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
	}

	
	public List<Produto> findByCat(String cat) {
		
		EntityManager em = this.getEntityManager();
		String sql ;
		
		sql = "select p from Produto p where 1 = 1 "
	    		+ "    and p.cats = :pcat "
		    	+ "    order by preco " ;
		
		TypedQuery<Produto> query = em
				.createQuery( sql , 
								Produto.class ) ;
		
		query.setParameter("pcat", cat);
		
		try {
			return query.getResultList();
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
	}
	
	
    public List<Produto> listProdutos() {
        return this.buscaTodos();
    }
    
    public Produto findById(Long id) {
        return this.buscaPorId(id, false);
    }


    @Transactional
    public Produto salvar(Produto produto) {
    	return super.salvar(produto);
    }

    @Transactional
    public Produto atualizaProduto(Produto produto) {
    	return super.salvar(produto);
    }

}
