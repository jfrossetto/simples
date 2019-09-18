package br.jfr.simples.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.jfr.simples.model.Produto;

public class ProdutoDAO extends GenericDAO<Produto, Long> implements IProdutoDAO {
	
	public List<Produto> findAll() {
		EntityManager em = this.getEntityManager();
		
		TypedQuery<Produto> query = em
				.createQuery(
						"select p from Produto p"
						+ " order by id desc " 
						,Produto.class);

		return query.getResultList();
	}

	
	public String findDescOra(String codora) {
		EntityManager em = this.getEntityManager();
		Query q = em
				  .createNativeQuery(
						  "   select  descricao  " 
					    + "     from  bilhetes "
					    + "    where  codigo = :pcodora " 
                                  ) ;
		
		q.setParameter("pcodora", codora);
		
		String result ;
		try {
			result = (String) q.getSingleResult() ;
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
		return result ; 
		
	}

	
	public List<Object[]> findContrato(String codora) {
		
		EntityManager em = this.getEntityManager();
		
		String sql = "	select virtual_header_id , nr_contrato , codbilhete , b.descricao , b.repeticoes+1 acessos , oe_pkg.get_price(nr_contrato,codbilhete) preco " + 
				"	from b2b_virtual_header a , bilhetes b " + 
				"	where a.cod_parceiro in ( 40777 , 40799 ) " + 
				"	and b.codigo = a.codbilhete " + 
				"	and codbilhete = :pcodora  " ; 

		Query q = em.createNativeQuery(sql) ;
		
		q.setParameter("pcodora", codora);
		
		List<Object[]> result ;
		
		try {
			result = q.getResultList() ;
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
		return result ; 
		
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
	
	public List<Produto> findProdsVen() {
		EntityManager em = this.getEntityManager();
		String sql ;
		
		sql = "select p from Produto p where  "
	    		+ "     p.cats = :pcat "
	    		+ " and p.preco != 0 "
				+ " and NOT EXISTS (select pc from ProdutoCortesia pc where pc.prodven = p) "
		    	+ "    order by id desc " ;
		
		TypedQuery<Produto> query = em
				.createQuery( sql , 
								Produto.class ) ;
		
		query.setParameter("pcat", "#kon");
		
		try {
			return query.getResultList();
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
	}
			
	public List<Produto> findProdsCort() {
		EntityManager em = this.getEntityManager();
		String sql ;
		
		sql = "select p from Produto p where  "
	    		+ "    p.cats = :pcat "
				+ "    and p.preco = 0 "
		    	+ "    order by id desc " ;
		
		TypedQuery<Produto> query = em
				.createQuery( sql , 
								Produto.class ) ;
		
		query.setParameter("pcat", "#kon");
		
		try {
			return query.getResultList();
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
	}
	
	public List<Produto> findProdDistr( String customer_number ) {  
		
		EntityManager em = this.getEntityManager();
		String sql ;
		
		sql = "	   select  p.* " + 
			"	     from      " + 
			"	           nw_produto p " + 
			"	    where  p.codproduto in (                              " + 
			"	                             select codbilhete            " + 
			"	                               from b2b_virtual_header h  " + 
			"	                              where h.parceiro_prod =:pcoddistr " + 
			"	                                and (select count(*) from b2b_virtual_detail d where d.virtual_header_id = h.virtual_header_id and status = 'I') > 0 " + 
			"	                                and oe_pkg.get_price(h.nr_contrato,h.codbilhete) != 0 " + 
			"	           ) " + 
			"	      and  cats = '#kon' " + 
			"	      and  datafim > trunc(sysdate) " ; 
		
		Query query = em
				.createNativeQuery( sql , 
								Produto.class ) ;
		
		query.setParameter("pcoddistr", customer_number);
		
		try {
			return query.getResultList();
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
	}
	
	
	public List<Object> findProdValidade( String codora ) {
		EntityManager em = this.getEntityManager();
		
		String sql = "	select data " +   //  ''''||to_char(data,'mm-dd-yyyy')||''''
				"    from validadebilhetes " + 
				"   where " + 
				"	      codbilhete = :pcodora  " ; 

		Query q = em.createNativeQuery(sql) ;
		
		q.setParameter("pcodora", codora);
		
		List<Object> result ;
		
		try {
			result = q.getResultList() ;
		} catch ( javax.persistence.NoResultException e ) {
			return null;
		}
		
		return result ; 
		
	}


}
