package br.jfr.simples.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.jfr.simples.model.Permissao;
import br.jfr.simples.model.Usuario;

@RequestScoped
public class UsuarioServico extends ServicoGenerico<Usuario, Long> {
	
	public Usuario buscaPorEmail(String email) {
		
		EntityManager em = this.getEntityManager();
		
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
