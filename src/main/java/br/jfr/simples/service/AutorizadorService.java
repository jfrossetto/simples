package br.jfr.simples.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import br.jfr.simples.bean.UsuarioLogado;

@RequestScoped
public class AutorizadorService implements Serializable {
		
	private static final long serialVersionUID = 1L;
		
	@Inject
	private UsuarioLogado usuarioLogado ;
	
	public boolean temPermissao(String perm) {
		return true;
	}
    
}
