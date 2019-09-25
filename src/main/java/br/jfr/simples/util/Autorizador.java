package br.jfr.simples.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

//import br.jfr.simples.bean.UsuarioLogado;


public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

//	@Inject 
//	private UsuarioLogado usuarioLogado ;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();
		
		FacesContext ctx = context.getCurrentInstance();
		ExternalContext external = ctx.getExternalContext();
		
		if( external.isResponseCommitted() ) {
			return ;
		}
		
		String nomePagina = context.getViewRoot().getViewId();

		System.out.println(nomePagina);
        /*
		if ("/login.xhtml".equals(nomePagina) || "/home.xhtml".equals(nomePagina) ) {
			return;
		}
		
		if( usuarioLogado == null || ! usuarioLogado.isLogged() ) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/login?faces-redirect=true");
			context.renderResponse();
		}
		
		// verifica se possui acesso a url 
		if( ! usuarioLogado.hasUrl(nomePagina) ) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/home?faces-redirect=true");
			context.renderResponse();
		}
		*/
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}