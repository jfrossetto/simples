package br.jfr.simples.model.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.jfr.simples.converter.BooleanToStringConverter;

@Entity
@Table(name="grupo", //schema = "simples",
       indexes= @Index(name="grupo_n1", columnList = "descricao", unique=false) )
public class Grupo extends Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="grupo_generator", sequenceName="grupo_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="grupo_generator")	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name= "descricao" , length=64, nullable=false )
	private String descricao;

    @Column(name="admin")
    @Convert(converter=BooleanToStringConverter.class)
    private Boolean admin;
	
    @OneToMany( mappedBy="grupo", fetch=FetchType.LAZY, orphanRemoval=false)
    private List<Usuario> usuarioLista;

    @OneToMany( mappedBy="grupo", fetch=FetchType.LAZY, orphanRemoval=false)
    private List<GrupoPerm> permissoesGrupo;
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public List<Usuario> getUsuarioLista() {
		return usuarioLista;
	}

	public void setUsuarioLista(List<Usuario> usuarioLista) {
		this.usuarioLista = usuarioLista;
	}

	public List<GrupoPerm> getPermissoesGrupo() {
		return permissoesGrupo;
	}

	public void setPermissoesGrupo(List<GrupoPerm> permissoesGrupo) {
		this.permissoesGrupo = permissoesGrupo;
	}

}
