package br.jfr.simples.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import br.jfr.simples.model.Permissao;
import br.jfr.simples.model.Usuario;
import br.jfr.simples.service.UsuarioServico;

@Named
@ViewScoped
public class LoginBean extends GenericoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioServico usuarioServico;
	
	@Inject 
	private UsuarioLogado usuarioLogado ;
	
	@Inject 
	private Logger logger;
	
	private Usuario usuario;
	private List<Permissao> permissoes ;
	
    private String email ;
    private String senha ;
    private String senha_md5 ;

	public String getUserNameLogado() {
		return usuarioLogado.getUsuario().getNome();
	}
	
	public String efetuaLogin() {
		
		if( this.email == null || email.equals("") ) {
			this.addError(true, "informe o usuario");
			return null;
		}
		if( this.senha == null || senha.equals("") ) {
			this.addError(true, "informe a senha");
			return null;
		}
		
		logger.info("Fazendo login do usuario >>> " + this.email ) ;
		
        senha_md5 = usuarioServico.retornaMD5(senha); 
        logger.info("senha md5: " + senha_md5);
        
		usuario = usuarioServico.buscaPorEmail(email); 
		
		if( usuario == null ) {
			this.addError(true, "Usuario Não Localizado");
			return null;
		}
		
		if( Objects.nonNull(senha_md5) && !usuario.getSenha().equals(senha_md5) ) {
			this.addError(true, "Senha Inválida");
			return null;
		}
		
		usuarioLogado.setUsuario(usuario);
		
		permissoes = usuarioServico.retornaPermissoesUsuario(usuario);
		usuarioLogado.setPermissoes(permissoes);
		
		if( this.usuarioLogado.getLast_url() == null || this.usuarioLogado.getLast_url().equals("") ) {
			return "home.xhtml" ;
		}
		
		return this.usuarioLogado.getLast_url()+"?faces-redirect=true" ; 
		
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String doLogout() {
		usuarioLogado.setUsuario(null);
		return "login.xhtml?faces-redirect=true" ;
	}
	
}
