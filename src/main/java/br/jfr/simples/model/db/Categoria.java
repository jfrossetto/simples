package br.jfr.simples.model.db ;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="categoria",
		indexes= @Index(name="categoria_n1", columnList = "descricao", unique=false) )
public class Categoria extends Entidade implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="categoria_generator", sequenceName="categoria_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="categoria_generator")	
	private Long id;

	@Column(name= "descricao" , length=64, nullable=false )
	private String descricao;
	
	@Column(name= "desc_reduzida" , length=32, nullable=false )
	private String desc_reduzida;

    @OneToMany( mappedBy="categoria", fetch=FetchType.LAZY, orphanRemoval=false)
	private List<Produto> produtoLista;
	
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
	
}