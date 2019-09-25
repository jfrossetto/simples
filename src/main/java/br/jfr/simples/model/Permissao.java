package br.jfr.simples.model;

import java.io.Serializable;
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

@Entity
@Table(name="permissao", schema = "simples",
       indexes= @Index(name="permissao_n1", columnList = "descricao") )
public class Permissao extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="permissao_generator", sequenceName="permissao_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="permissao_generator")	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name= "descricao" , length=64, nullable=false )
	private String descricao;

    @OneToMany( mappedBy="permissao", fetch=FetchType.LAZY, orphanRemoval=false)
    private List<GrupoPerm> permissoesGrupo;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<GrupoPerm> getPermissoesGrupo() {
		return permissoesGrupo;
	}

	public void setPermissoesGrupo(List<GrupoPerm> permissoesGrupo) {
		this.permissoesGrupo = permissoesGrupo;
	}
	
}
