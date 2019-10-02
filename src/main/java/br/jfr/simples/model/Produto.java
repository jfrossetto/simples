package br.jfr.simples.model ;

import java.io.Serializable;
import java.math.BigDecimal;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the DEM_ITEM database table.
 * 
 */
@Entity
@Table(name="produto",
		indexes= @Index(name="produto_n1", columnList = "descricao", unique=false) )
public class Produto extends Entidade implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="produto_generator", sequenceName="produto_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="produto_generator")	
	private Long id;

	@NotNull
	@Column(name= "descricao" , length=64, nullable=false )
	private String descricao;
	
	@Column(name= "desc_reduzida" , length=32, nullable=false )
	private String desc_reduzida;
	
	@Column(name= "complemento" , length=255, nullable=false )
	private String complemento ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "datainicio")
	private Calendar datainicio ;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "datafim")
	private Calendar datafim ;
	
	@NotNull
	@Column(name= "tipo", length=3 , nullable=false)
	private String tipo ; // 1.produto venda 2.insumo/mat prim 3.intermediario 4.servico .... (usar tablov (tipo_tablov , opcao , descr));
	
	@NotNull
	@Column(name= "categoria", nullable=false)	
	private String categoria ;
	
	@Column(name= "url_imagem" , length=512)
	private String url_imagem ;
	
    @Column(name="preco_de",precision=9,scale=2)
	private BigDecimal preco_de ;
	
    @NotNull
    @Column(name="preco",precision=9,scale=2,nullable=false )
	private BigDecimal preco ;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDesc_reduzida() {
		return desc_reduzida;
	}

	public void setDesc_reduzida(String desc_reduzida) {
		this.desc_reduzida = desc_reduzida;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Calendar getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Calendar datainicio) {
		this.datainicio = datainicio;
	}

	public Calendar getDatafim() {
		return datafim;
	}

	public void setDatafim(Calendar datafim) {
		this.datafim = datafim;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getUrl_imagem() {
		return url_imagem;
	}

	public void setUrl_imagem(String url_imagem) {
		this.url_imagem = url_imagem;
	}

	public BigDecimal getPreco_de() {
		return preco_de;
	}

	public void setPreco_de(BigDecimal preco_de) {
		this.preco_de = preco_de;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	
}