package br.jfr.simples.model ;

import java.io.Serializable;
import java.math.BigDecimal;


import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DEM_ITEM database table.
 * 
 */
@Entity
@Table(name="produto", schema = "simples")
public class Produto extends Entidade implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="produto_generator", sequenceName="produto_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="produto_generator")	
	private Long id;

	@Column(name="codproduto")
	private String codProduto;
	
	private String descricao;
	
	private String obs_validade ;
	private String obs_acesso ;
	private String obs_complemento ;
	private String obs_info ;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datainicio ;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datafim ;
	
	private String tipo ;
	private String cats ;  
	
	private String imagem ;
    
	private BigDecimal preco_old ;
	
	private BigDecimal preco ;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getPreco_old() {
		return preco_old;
	}

	public void setPreco_old(BigDecimal preco_old) {
		this.preco_old = preco_old;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getObs_validade() {
		return obs_validade;
	}

	public void setObs_validade(String obs_validade) {
		this.obs_validade = obs_validade;
	}

	public String getObs_acesso() {
		return obs_acesso;
	}

	public void setObs_acesso(String obs_acesso) {
		this.obs_acesso = obs_acesso;
	}

	public String getObs_info() {
		return obs_info;
	}

	public void setObs_info(String obs_info) {
		this.obs_info = obs_info;
	}

	public String getCats() {
		return cats;
	}

	public void setCats(String cats) {
		this.cats = cats;
	}

	public String getObs_complemento() {
		return obs_complemento;
	}

	public void setObs_complemento(String obs_complemento) {
		this.obs_complemento = obs_complemento;
	}
	
}