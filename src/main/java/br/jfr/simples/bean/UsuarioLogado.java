package br.jfr.simples.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.jfr.simples.model.Permissao;
import br.jfr.simples.model.Usuario;

//import br.hh.base.model.MAutentica;
//import br.hh.base.model.MAutoriza;

@Named
@SessionScoped
public class UsuarioLogado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private List<Permissao> permissoes ;
	
	private String last_url;

    @PostConstruct
    void init() {
    	usuario = null ;
    	permissoes = new ArrayList<>();
    }

    public boolean isLogged() {
    	return Objects.nonNull(usuario);
    }

	public String getLast_url() {
		return last_url;
	}

	public void setLast_url(String last_url) {
		this.last_url = last_url;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	public boolean temPermissao(String perm) {
		if( permissoes == null) {
			return false;
		}
		return permissoes.stream().allMatch( p -> p.getDescricao().equals(perm) );
	}
	
	public boolean temUrl(String url) {
		/*
		if( roles == null) {
			return false;
		}
		for (MAutoriza r : roles) {
			if( r.getAuthorityurl().equals(url) ) {
				return true;
			}
		}
		return false ;
		*/
		return true;
	}
    
}
