package br.jfr.simples.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="usuario", schema = "simples",
       indexes= @Index(name="usuario_n1", columnList = "email") )
public class Usuario extends Entidade implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, updatable = false)
	@SequenceGenerator(name="usuario_generator", sequenceName="usuario_s" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_generator")	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name= "email" , length=64, nullable=false )
	private String email;
	
	@Column( length=64, nullable=false )
	private String nome;

	@Column( length=64, nullable=false )
	private String senha;
	
	@Column( length=64 )
	private String ultSenha;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultLogin ;

	@ManyToOne
	private Grupo grupo;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUltSenha() {
		return ultSenha;
	}

	public void setUltSenha(String ultSenha) {
		this.ultSenha = ultSenha;
	}

	public Calendar getUltLogin() {
		return ultLogin;
	}

	public void setUltLogin(Calendar ultLogin) {
		this.ultLogin = ultLogin;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	

}
