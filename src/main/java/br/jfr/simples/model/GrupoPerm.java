package br.jfr.simples.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="grupoperm", schema = "simples",
       indexes= @Index(name="grupoperm_n1", columnList = "grupo") )
public class GrupoPerm extends Entidade implements Serializable {

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

	@ManyToOne
	private Grupo grupo;
	@ManyToOne
	private Permissao permissao;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

}
