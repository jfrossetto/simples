package br.jfr.simples.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import br.jfr.simples.model.Permissao;
import br.jfr.simples.model.Usuario;

@ApplicationScoped
public class UsuarioServico extends ServicoGenerico<Usuario, Long> implements Serializable {

	@Inject 
	private Logger logger;

	private static final long serialVersionUID = 1L;

	public Usuario buscaPorEmail(String email) {
		
		logger.info(" buscaPorEmail " );
		
		String sql = "select u from Usuario u " +
						 " where email = :email ";
		
		Map<String,Object> mapaParam = new HashMap<>() ;
		mapaParam.put("email", email);
		return carregaEntidade(sql, mapaParam);
		
	}
	
	public List<Permissao> retornaPermissoesUsuario(Usuario usuario) {
		List<Permissao> permissoes = new ArrayList<>();
		if( !usuario.getGrupo().isAdmin() ) {
			usuario.getGrupo().getPermissoesGrupo().forEach( pg -> { 
				permissoes.add( pg.getPermissao() ) ; 
				}); 
		} 
		return permissoes;
	}
	

}
