package br.jfr.simples.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.jfr.simples.model.Permissao;
import br.jfr.simples.model.Usuario;
import br.jfr.simples.service.UsuarioServico;

@Named
@ViewScoped
public class LoginBean extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioServico usuarioServico;
	
	@Inject 
	private UsuarioLogado usuarioLogado ;
	
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
		
		logger.info("Fazendo login do usuario " + this.email ) ;
		
        senha_md5 = usuarioServico.retornaMD5(senha); 

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
	
	public String doLogout() {
		usuarioLogado.setUsuario(null);
		return "login.xhtml?faces-redirect=true" ;
	}
	
}
